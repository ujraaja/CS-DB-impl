package lqp;

import java.util.ArrayList;
import java.util.List;

import classes.BooleanFactor;
import classes.BooleanTerm;
import classes.ColumnName;
import classes.Expression;
import classes.Literal;
import classes.SearchCondition;
import classes.Term;
import storageManager.Block;
import storageManager.Config;
import storageManager.Field;
import storageManager.FieldType;
import storageManager.Relation;
import storageManager.Schema;
import storageManager.StorageManager;
import storageManager.Tuple;

public class Join extends Table {
    public List<Table> tables;
    public SearchCondition searchCondition;
    

    private boolean checkCondition(SearchCondition condition, Tuple tuple) {
        if(condition == null)
            return true;
        if(condition.searchCondition == null)
            return checkCondition(condition.booleanTerm, tuple);
        return checkCondition(condition.booleanTerm, tuple) || checkCondition(condition.searchCondition, tuple);
    }
    
    private boolean checkCondition(BooleanTerm booleanTerm, Tuple tuple) {
        if(booleanTerm.booleanTerm == null)
            return checkCondition(booleanTerm.booleanFactor, tuple);
        return checkCondition(booleanTerm.booleanFactor, tuple) && checkCondition(booleanTerm.booleanTerm, tuple);
    }
    
    private boolean checkCondition(BooleanFactor booleanFactor, Tuple tuple) {
        Object v1 = evaluate(booleanFactor.expression1, tuple);
        Object v2 = evaluate(booleanFactor.expression2, tuple);
        if(v1 == null || v2 == null)
            return true;
        if(v1 instanceof java.lang.Integer && v2 instanceof java.lang.Integer) {
            switch (booleanFactor.operator) {
                case '<':
                    return (int) v1 < (int) v2;
                case '>':
                    return (int) v1 > (int) v2;
                case '=':
                    return (int) v1 == (int) v2;
            }
        } else {
            assert(booleanFactor.operator == '=');
            return v1.toString().equals(v2.toString());
        }
        return false;
    }
    
    private Object evaluate(Expression expression, Tuple tuple) {
        Object v1 = evaluate(expression.term1, tuple);
        if(expression.operator == ' ')
            return v1;
        Object v2 = evaluate(expression.term2, tuple);
        if(v1 == null || v2 == null)
            return null;
        switch (expression.operator) {
            case '+':
                return (int) v1 + (int) v2;
            case '-':
                return (int) v1 - (int) v2;
            case '*':
                return (int) v1 * (int) v2;
        }
        return null;
    }
    
    private Object evaluate(Term term, Tuple tuple) {
        if(term instanceof ColumnName) {
            String attribute;
            if(tuple.getSchema().getFieldNames().contains(term.toString()))
                attribute = term.toString();
            else
                attribute = ((ColumnName) term).attribute;
            if(!tuple.getSchema().fieldNameExists(attribute))
                return null;
            Field field = tuple.getField(attribute);
            if(field.type == FieldType.INT)
                return field.integer;
            return field.str;
        }
        if(term instanceof classes.Integer)
            return ((classes.Integer) term).value;
        if(term instanceof Literal)
            return ((Literal) term).value;
        return null;
    }
    
    private void deleteTable(Table table) {
        if(table.tableName.contains(":")) {
            if(StorageManager.schemaManager.relationExists(table.tableName)) {
                TableManager.removeTable(table.tableName);
                StorageManager.schemaManager.deleteRelation(table.tableName);
            }
        }
    }

    private int crossJoin(Table table1, Table table2) {
        Table table = new Table(table1.tableName + ":x:" + table2.tableName);
        table.numberOfTuples = table1.numberOfTuples * table2.numberOfTuples;
        tables.add(table);
        TableManager.addTable(table.tableName, table);

        Relation relation1 = StorageManager.schemaManager.getRelation(table1.tableName);
        Relation relation2 = StorageManager.schemaManager.getRelation(table2.tableName);

        Schema schema1 = relation1.getSchema();
        Schema schema2 = relation2.getSchema();

        ArrayList<String> fieldNames = new ArrayList<>();
        
        if(!(table1.tableName.contains(":")))
            for(int i = 0; i < schema1.getNumOfFields(); i++)
                fieldNames.add(table1.tableName + '.' + schema1.getFieldName(i));
        else
            fieldNames.addAll(schema1.getFieldNames());
        
        if(!(table2.tableName.contains(":")))
            for(int i = 0; i < schema2.getNumOfFields(); i++)
                fieldNames.add(table2.tableName + '.' + schema2.getFieldName(i));
        else
            fieldNames.addAll(schema2.getFieldNames());

        ArrayList<FieldType> fieldTypes = new ArrayList<>();
        fieldTypes.addAll(schema1.getFieldTypes());
        fieldTypes.addAll(schema2.getFieldTypes());
        
        Schema schema = new Schema(fieldNames, fieldTypes);
        Relation newRelation = StorageManager.schemaManager.createRelation(table.tableName, schema);

        int totalDiskBlocks1 = relation1.getNumOfBlocks();
        int totalDiskBlocks2 = relation2.getNumOfBlocks();

        int maxMemoryBlocks1 = Config.NUM_OF_BLOCKS_IN_MEMORY-2;
        if(totalDiskBlocks1 >= Config.NUM_OF_BLOCKS_IN_MEMORY-1)
            maxMemoryBlocks1 = Config.NUM_OF_BLOCKS_IN_MEMORY / 3;

        int validDiskBlock = 0;
        int maxMemoryBlocks2 = (Config.NUM_OF_BLOCKS_IN_MEMORY - maxMemoryBlocks1) / 2;
        int validMemoryBlock = maxMemoryBlocks1 + maxMemoryBlocks2;
        Block tempBlock = StorageManager.memory.getBlock(validMemoryBlock);
        tempBlock.clear();
        for(int d1BlockIndex = 0; d1BlockIndex < totalDiskBlocks1; d1BlockIndex += maxMemoryBlocks1) {
            int activeMemBlocks1 = Math.min(maxMemoryBlocks1, totalDiskBlocks1-d1BlockIndex);
            relation1.getBlocks(d1BlockIndex, 0, activeMemBlocks1);
            for(int d2BlockIndex = 0; d2BlockIndex < totalDiskBlocks2; d2BlockIndex += maxMemoryBlocks2) {
                int activeMemBlocks2 = Math.min(maxMemoryBlocks2, totalDiskBlocks2-d2BlockIndex);
                relation2.getBlocks(d2BlockIndex, maxMemoryBlocks1, activeMemBlocks2);
                for(int m1BlockIndex = 0; m1BlockIndex < activeMemBlocks1; m1BlockIndex++) {
                    Block m1Block = StorageManager.memory.getBlock(m1BlockIndex);
                    for(Tuple tuple1 : m1Block.getTuples()) {
                        for(int m2BlockIndex = 0; m2BlockIndex < activeMemBlocks2; m2BlockIndex++) {
                            Block m2Block = StorageManager.memory.getBlock(maxMemoryBlocks1 + m2BlockIndex);
                            for(Tuple tuple2 : m2Block.getTuples()) {
                                Tuple tuple = newRelation.createTuple();
                                for(int fieldIndex = 0; fieldIndex < schema1.getNumOfFields(); fieldIndex++) {
                                    if(schema1.getFieldType(fieldIndex) == FieldType.INT)
                                        tuple.setField(fieldIndex, tuple1.getField(fieldIndex).integer);
                                    else
                                        tuple.setField(fieldIndex, tuple1.getField(fieldIndex).str);
                                }
                                for(int fieldIndex = 0; fieldIndex < schema2.getNumOfFields(); fieldIndex++) {
                                    if(schema2.getFieldType(fieldIndex) == FieldType.INT)
                                        tuple.setField(tuple1.getNumOfFields()+fieldIndex, tuple2.getField(fieldIndex).integer);
                                    else
                                        tuple.setField(tuple1.getNumOfFields()+fieldIndex, tuple2.getField(fieldIndex).str);
                                }
                                if(checkCondition(searchCondition, tuple))
                                    tempBlock.appendTuple(tuple);
                                if(tempBlock.isFull()) {
                                    StorageManager.memory.setBlock(validMemoryBlock, tempBlock);
                                    validMemoryBlock++;
                                    if(validMemoryBlock == Config.NUM_OF_BLOCKS_IN_MEMORY) {
                                        newRelation.setBlocks(validDiskBlock, maxMemoryBlocks1 + maxMemoryBlocks2, validMemoryBlock - (maxMemoryBlocks1 + maxMemoryBlocks2));
                                        validDiskBlock += validMemoryBlock - (maxMemoryBlocks1 + maxMemoryBlocks2);
                                        validMemoryBlock = maxMemoryBlocks1 + maxMemoryBlocks2;
                                    }
                                    tempBlock = StorageManager.memory.getBlock(validMemoryBlock);
                                    tempBlock.clear();
                                }
                            }
                        }
                    }
                }
            }
        }
        if(!tempBlock.isEmpty()) {
            StorageManager.memory.setBlock(validMemoryBlock, tempBlock);
            validMemoryBlock++;
        }
        if(validMemoryBlock != maxMemoryBlocks1 + maxMemoryBlocks2)
            newRelation.setBlocks(validDiskBlock, maxMemoryBlocks1 + maxMemoryBlocks2, validMemoryBlock - (maxMemoryBlocks1 + maxMemoryBlocks2));
        return table.numberOfTuples;
    }
    
    @Override
    public boolean execute() {
        for(Table table : tables)
            table.execute();
        List<Integer> tableSizes = new ArrayList<>();
        for(int i = 0; i < tables.size(); i++)
            tableSizes.add(tables.get(i).numberOfTuples);
        while(tables.size() > 1) {
            int mn = Integer.MAX_VALUE, mni = -1;
            for(int i = 0; i < tableSizes.size(); i++)
                if(mn > tableSizes.get(i)) {
                    mn = tableSizes.get(i);
                    mni = i;
                }
            Table table1 = tables.get(mni);
            tables.remove(mni);
            tableSizes.remove(mni);
            mn = mni = Integer.MAX_VALUE;
            for(int i = 0; i < tableSizes.size(); i++)
                if(mn > tableSizes.get(i)) {
                    mn = tableSizes.get(i);
                    mni = i;
                }
            Table table2 = tables.get(mni);
            tables.remove(mni);
            tableSizes.remove(mni);
            tableSizes.add(crossJoin(table1, table2));
            deleteTable(table1);
            deleteTable(table2);
        }
        Table table = tables.get(0);
        tableName = table.tableName;
        numberOfTuples = table.numberOfTuples;
        return true;
    }
}