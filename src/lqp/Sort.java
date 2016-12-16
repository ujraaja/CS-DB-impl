package lqp;

import java.util.List;

import classes.ColumnName;
import storageManager.Block;
import storageManager.Config;
import storageManager.FieldType;
import storageManager.Relation;
import storageManager.StorageManager;
import storageManager.Tuple;

public class Sort extends Table {
    public Table table;
    public List<ColumnName> columnNames;

    private boolean isGreater(Tuple tuple1, Tuple tuple2) {
        for(ColumnName columnName : columnNames) {
            if(tuple1.getField(columnName.attribute).type == FieldType.INT) {
                if(tuple1.getField(columnName.attribute).integer != tuple2.getField(columnName.attribute).integer)
                    return tuple1.getField(columnName.attribute).integer > tuple2.getField(columnName.attribute).integer;
            } else if(!tuple1.getField(columnName.attribute).str.equals(tuple2.getField(columnName.attribute).str))
                return tuple1.getField(columnName.attribute).str.compareTo(tuple2.getField(columnName.attribute).str) > 0;
        }
        return false;
    }
    
    private Tuple getPrevTuple(int blockIndex, int tupleIndex) {
        if(tupleIndex != 0)
            return StorageManager.memory.getBlock(blockIndex).getTuple(tupleIndex-1);
        if(blockIndex == 0)
            return null;
        Block block = StorageManager.memory.getBlock(blockIndex-1);
        return block.getTuple(block.getNumTuples()-1);
    }
    
    private void setPrevTuple(int blockIndex, int tupleIndex, Tuple tuple) {
        if(tupleIndex != 0)
            StorageManager.memory.getBlock(blockIndex).setTuple(tupleIndex-1, tuple);
        else if(blockIndex != 0) {
            Block block = StorageManager.memory.getBlock(blockIndex-1);
            block.setTuple(block.getNumTuples()-1, tuple);
        }
    }

    @Override
    public boolean execute() {
        table.execute();
        Relation relation = StorageManager.schemaManager.getRelation(table.tableName);
        if(columnNames == null)
            assert(false);
        tableName = table.tableName;
        numberOfTuples = table.numberOfTuples;
        attrValues = table.attrValues;
        int totalDiskBlocks = relation.getNumOfBlocks();
        if(totalDiskBlocks == 0)
            return true;
        int tuplesPerBlock = relation.getSchema().getTuplesPerBlock();
        int maxMemoryBlocks = Config.NUM_OF_BLOCKS_IN_MEMORY;
        /* sorting phase #1 */
        for(int dBlockIndex = 0; dBlockIndex < totalDiskBlocks; dBlockIndex += maxMemoryBlocks) {
            int activeMemBlocks = Math.min(maxMemoryBlocks, totalDiskBlocks-dBlockIndex);
            relation.getBlocks(dBlockIndex, 0, activeMemBlocks);
            Block block;
            for(int z = 0; z < activeMemBlocks * tuplesPerBlock; z++) {
                for(int i = 0; i < activeMemBlocks; i++) {
                    block = StorageManager.memory.getBlock(i);
                    for(int j = 0; j < block.getNumTuples(); j++) {
                        Tuple t1 = getPrevTuple(i, j), t2 = block.getTuple(j);
                        if(t1 == null) continue;
                        if(isGreater(t1, t2)) {
                            setPrevTuple(i, j, t2);
                            StorageManager.memory.getBlock(i).setTuple(j, t1);
                        }
                    }
                }
            }
            relation.setBlocks(dBlockIndex, 0, activeMemBlocks);
        }
        /* sorting phase #2 */
        int numPartitions = (totalDiskBlocks + maxMemoryBlocks-1)/maxMemoryBlocks;
        int currentBlock[] = new int[numPartitions];
        int currentTuple[] = new int[numPartitions];
        for(int i = 0; i < numPartitions; i++) {
            currentBlock[i] = i * maxMemoryBlocks;
            currentTuple[i] = 0;
            relation.getBlock(currentBlock[i], i);
        }
        int currBlock = numPartitions;
        Block block = StorageManager.memory.getBlock(currBlock);
        block.clear();
        boolean flag = true;
        tableName = table.tableName + ":sorted";
        Relation newRelation = StorageManager.schemaManager.createRelation(tableName, relation.getSchema());
        int newRelationActiveBlock = 0;
        while(flag) {
            flag = false;
            Tuple minTuple = null;
            int minBlock = 0;
            for(int i = 0; i < numPartitions; i++) {
                if(currentBlock[i] == -1) continue;
                flag = true;
                if(minTuple == null) {
                    minTuple = StorageManager.memory.getBlock(i).getTuple(currentTuple[i]);
                    minBlock = i;
                    continue;
                }
                Tuple tuple = StorageManager.memory.getBlock(i).getTuple(currentTuple[i]);
                if(isGreater(minTuple, tuple)) {
                    minTuple = tuple;
                    minBlock = i;
                }
            }
            if(!flag) break;
            currentTuple[minBlock]++;
            if(currentTuple[minBlock] == StorageManager.memory.getBlock(minBlock).getNumTuples()) {
                currentTuple[minBlock] = 0;
                currentBlock[minBlock]++;
                if(currentBlock[minBlock]%maxMemoryBlocks == 0 || currentBlock[minBlock] == totalDiskBlocks) {
                    currentBlock[minBlock] = -1;
                } else {
                    relation.getBlock(currentBlock[minBlock], minBlock);
                }
            }
            block.appendTuple(minTuple);
            if(block.isFull()) {
                currBlock++;
                if(currBlock == Config.NUM_OF_BLOCKS_IN_MEMORY) {
                    newRelation.setBlocks(newRelationActiveBlock, numPartitions, currBlock-numPartitions);
                    newRelationActiveBlock += currBlock-numPartitions;
                    currBlock = numPartitions;
                }
                block = StorageManager.memory.getBlock(currBlock);
                block.clear();
            }
        }
        if(block.getNumTuples() != 0)
            currBlock++;
        if(currBlock != numPartitions)
            newRelation.setBlocks(newRelationActiveBlock, numPartitions, currBlock-numPartitions);
        return true;
    }
}
