package lqp;

import java.util.ArrayList;
import java.util.List;

import classes.ColumnName;
import storageManager.Block;
import storageManager.Config;
import storageManager.FieldType;
import storageManager.Relation;
import storageManager.Schema;
import storageManager.StorageManager;
import storageManager.Tuple;

public class Project extends Table {
    public List<ColumnName> columnNames;
    public Table table;

    public boolean doFieldsMatch(ColumnName column, Relation relation, String field) {
        String name = "";
        if(column.tableName == null || column.tableName.isEmpty())
            name = column.attribute;
        else
            name = column.toString();
        if(field.equals(name) || (relation.getRelationName()+"."+field).equals(name) || field.equals(table.tableName + "." + name))
            return true;
        return false;
    }
    
    @Override
    public boolean execute() {
        table.execute();
        Relation relation = StorageManager.schemaManager.getRelation(table.tableName);
        tableName = table.tableName;
        numberOfTuples = table.numberOfTuples;
        attrValues = table.attrValues;
        if(columnNames == null)
            assert(false);
        List<String> fields = relation.getSchema().getFieldNames();
        boolean flag = true;
        for(String field : fields) {
            flag = false;
            for(ColumnName  column : columnNames) {
                if(doFieldsMatch(column, relation, field)) {
                    flag = true;
                    break;
                }
            }
            if(!flag)
                break;
        }
        if(flag)
            return true;
        int totalDiskBlocks = relation.getNumOfBlocks();
        if(totalDiskBlocks == 0)
            return true;
        
        Schema schema = StorageManager.schemaManager.getSchema(relation.getRelationName());
        ArrayList<String> field_names = new ArrayList<>();
        ArrayList<FieldType> field_types = new ArrayList<>();
        for(ColumnName column : columnNames) {
            field_names.add(column.attribute);
            for(String field : fields) {
                if(doFieldsMatch(column, relation, field)) {
                    field_types.add(schema.getFieldType(field));
                    break;
                }
            }
        }
        tableName = table.tableName + ":projected";
        TableManager.addTable(tableName, this);
        Relation newRelation = StorageManager.schemaManager.createRelation(tableName, new Schema(field_names, field_types));
        int maxMemoryBlocks = Config.NUM_OF_BLOCKS_IN_MEMORY-1;
        int validDiskBlock = 0;
        Block tempBlock = StorageManager.memory.getBlock(Config.NUM_OF_BLOCKS_IN_MEMORY-1);
        tempBlock.clear();
        for(int dBlockIndex = 0; dBlockIndex < totalDiskBlocks; dBlockIndex += maxMemoryBlocks) {
            int validMemoryBlock = 0;
            int activeMemBlocks = Math.min(maxMemoryBlocks, totalDiskBlocks-dBlockIndex);
            relation.getBlocks(dBlockIndex, 0, activeMemBlocks);
            for(int mBlockIndex = 0; mBlockIndex < activeMemBlocks; mBlockIndex++) {
                Block block = StorageManager.memory.getBlock(mBlockIndex);
                for(Tuple tuple : block.getTuples()) {
                    Tuple newTuple = newRelation.createTuple();
                    for(int colIndex = 0; colIndex < columnNames.size(); colIndex++) {
                        for(int fieldIndex = 0; fieldIndex < tuple.getNumOfFields(); fieldIndex++) {
                            if(doFieldsMatch(columnNames.get(colIndex), relation, schema.getFieldName(fieldIndex))) {
                                if(schema.getFieldType(fieldIndex) == FieldType.STR20)
                                    newTuple.setField(colIndex, tuple.getField(fieldIndex).str);
                                else
                                    newTuple.setField(colIndex, tuple.getField(fieldIndex).integer);
                            }
                        }
                    }
                    tempBlock.appendTuple(newTuple);
                    if(tempBlock.isFull()) {
                        StorageManager.memory.setBlock(validMemoryBlock, tempBlock);
                        validMemoryBlock++;
                        tempBlock.clear();
                    }
                }
            }
            if(dBlockIndex + maxMemoryBlocks >= totalDiskBlocks && !tempBlock.isEmpty()) {
                StorageManager.memory.setBlock(validMemoryBlock, tempBlock);
                validMemoryBlock++;
            }
            newRelation.setBlocks(validDiskBlock, 0, validMemoryBlock);
            validDiskBlock += validMemoryBlock;
        }
        return true;
    }
}
