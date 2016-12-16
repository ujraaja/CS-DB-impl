package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lqp.Table;
import lqp.TableManager;
import storageManager.Block;
import storageManager.Config;
import storageManager.Field;
import storageManager.FieldType;
import storageManager.Relation;
import storageManager.StorageManager;
import storageManager.Tuple;

public class InsertStatement extends Statement {
    public String tableName;
    public List<String> attributeList;
    public List<Term> valueList;
    public SelectStatement selectStatement;
    
    public InsertStatement() {
        attributeList = new ArrayList<String>();
    }
    
    private List<Tuple> formTuples() {
        Relation relation = StorageManager.schemaManager.getRelation(tableName);
        List<Tuple> tuples = new ArrayList<>();
        Table table = TableManager.getTable(tableName);
        if(selectStatement == null) {
            Tuple tuple = relation.createTuple();
            for(int index = 0; index < attributeList.size(); index++) {
                Term term = valueList.get(index);
                if(term instanceof Literal)
                    tuple.setField(attributeList.get(index), ((Literal) term).value);
                else if(term instanceof Null)
                    tuple.setField(attributeList.get(index), null);
                else
                    tuple.setField(attributeList.get(index), ((Integer) term).value);
                if(table.attrValues.get(attributeList.get(index)) == null) {
                    Map<String, java.lang.Integer> mp = new HashMap<String, java.lang.Integer>();
                    table.attrValues.put(attributeList.get(index), mp);
                }
                java.lang.Integer it = table.attrValues.get(attributeList.get(index)).get(term.toString());
                if(it == null)
                    it = 1;
                else
                    it = it+1;
                table.attrValues.get(attributeList.get(index)).put(term.toString(), it);
            }
            tuples.add(tuple);
        } else {
            selectStatement.execute(this);
            List<Tuple> values = selectStatement.output;
            for(Tuple value : values) {
                Tuple tuple = relation.createTuple();
                for(String attribute : attributeList) {
                    String vl = "";
                    Field field = value.getField(attribute);
                    if(field.type == FieldType.INT) {
                        tuple.setField(attribute, field.integer);
                        vl += field.integer;
                    } else {
                        tuple.setField(attribute, field.str);
                        vl = field.str;
                    }
                    if(table.attrValues.get(attribute) == null) {
                        Map<String, java.lang.Integer> mp = new HashMap<String, java.lang.Integer>();
                        table.attrValues.put(attribute, mp);
                    }
                    java.lang.Integer it = table.attrValues.get(attribute).get(vl);
                    if(it == null)
                        it = 1;
                    else
                        it = it+1;
                    table.attrValues.get(attribute).put(vl, it);
                }
                tuples.add(tuple);
            }
        }
        return tuples;
    }

    @Override
    public boolean execute() {
        Relation relation = StorageManager.schemaManager.getRelation(tableName);
        int totalDiskBlocks = relation.getNumOfBlocks()-1;
        if(totalDiskBlocks == -1) {
            StorageManager.memory.getBlock(0).clear();
            totalDiskBlocks++;
        } else
            relation.getBlock(totalDiskBlocks, 0);
        Block block = StorageManager.memory.getBlock(0);
        if(block.getNumTuples() == relation.getSchema().getTuplesPerBlock()) {
            block.clear();
            totalDiskBlocks++;
        }
        int validMemBlock = 0;
        List<Tuple> tuples = formTuples();
        for(Tuple tuple : tuples) {
            block.appendTuple(tuple);
            if(block.getNumTuples() == relation.getSchema().getTuplesPerBlock()) {
                StorageManager.memory.setBlock(validMemBlock, block);
                validMemBlock++;
                if(validMemBlock == Config.NUM_OF_BLOCKS_IN_MEMORY) {
                    relation.setBlocks(totalDiskBlocks, 0, validMemBlock);
                    totalDiskBlocks += validMemBlock;
                    validMemBlock = 0;
                }
                block.clear();
            }
        }
        if(!block.isEmpty()) {
            StorageManager.memory.setBlock(validMemBlock, block);
            validMemBlock++;
        }
        if(validMemBlock != 0) {
            relation.setBlocks(totalDiskBlocks, 0, validMemBlock);
        }
        return true;
    }
}
