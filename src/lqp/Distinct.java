package lqp;

import java.util.List;

import classes.ColumnName;
import storageManager.Block;
import storageManager.Config;
import storageManager.FieldType;
import storageManager.Relation;
import storageManager.StorageManager;
import storageManager.Tuple;

public class Distinct extends Table {
    public Table table;
    public List<ColumnName> columnNames;

    private boolean isEqual(Tuple tuple1, Tuple tuple2) {
        for(ColumnName columnName : columnNames) {
            if(tuple1.getField(columnName.attribute).type == FieldType.INT) {
                if(tuple1.getField(columnName.attribute).integer != tuple2.getField(columnName.attribute).integer)
                    return false;
            }
            else if(!tuple1.getField(columnName.attribute).str.equals(tuple2.getField(columnName.attribute).str))
                return false;
        }
        return true;
    }

    @Override
    public boolean execute() {
        table.execute();
        tableName = table.tableName;
        numberOfTuples = 0;
        Relation relation = StorageManager.schemaManager.getRelation(tableName);
        if(columnNames == null)
            assert(false);
        int totalDiskBlocks = relation.getNumOfBlocks();
        if(totalDiskBlocks == 0)
            return true;
        int maxMemoryBlocks = Config.NUM_OF_BLOCKS_IN_MEMORY-1;
        int validDiskBlock = 0;
        Block tempBlock = StorageManager.memory.getBlock(Config.NUM_OF_BLOCKS_IN_MEMORY-1);
        tempBlock.clear();
        Tuple tempTuple = null;
        for(int dBlockIndex = 0; dBlockIndex < totalDiskBlocks; dBlockIndex += maxMemoryBlocks) {
            int validMemoryBlock = 0;
            int activeMemBlocks = Math.min(maxMemoryBlocks, totalDiskBlocks-dBlockIndex);
            relation.getBlocks(dBlockIndex, 0, activeMemBlocks);
            for(int mBlockIndex = 0; mBlockIndex < activeMemBlocks; mBlockIndex++) {
                Block block = StorageManager.memory.getBlock(mBlockIndex);
                for(Tuple tuple : block.getTuples()) {
                    if(tempTuple == null) {
                        tempTuple = tuple;
                        continue;
                    }
                    if(!isEqual(tempTuple, tuple)) {
                        tempBlock.appendTuple(tempTuple);
                        numberOfTuples++;
                        tempTuple = tuple;
                        if(tempBlock.isFull()) {
                            StorageManager.memory.setBlock(validMemoryBlock, tempBlock);
                            validMemoryBlock++;
                            tempBlock.clear();
                        }
                    }
                }
            }
            if(dBlockIndex + maxMemoryBlocks >= totalDiskBlocks) {
                tempBlock.appendTuple(tempTuple);
                numberOfTuples++;
                StorageManager.memory.setBlock(validMemoryBlock, tempBlock);
                validMemoryBlock++;
            }
            if(validMemoryBlock != 0)
                relation.setBlocks(validDiskBlock, 0, validMemoryBlock);
            validDiskBlock += validMemoryBlock;
        }
        if(validDiskBlock < totalDiskBlocks)
            relation.deleteBlocks(validDiskBlock);
        return true;
    }
}
