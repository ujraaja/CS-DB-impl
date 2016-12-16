package lqp;

import java.util.HashMap;

import classes.SearchCondition;
import classes.Utils;
import storageManager.Block;
import storageManager.Config;
import storageManager.Relation;
import storageManager.StorageManager;
import storageManager.Tuple;

public class Select extends Table {
    public Table table;
    public SearchCondition searchCondition;

    @Override
    public boolean execute() {
        table.execute();
        Relation relation = StorageManager.schemaManager.getRelation(table.tableName);
        int totalDiskBlocks = relation.getNumOfBlocks();
        if(searchCondition == null || totalDiskBlocks == 0) {
            tableName = table.tableName;
            numberOfTuples = table.numberOfTuples;
            attrValues = table.attrValues;
            return true;
        }
        tableName = table.tableName + ":selected";
        numberOfTuples = 0;
        attrValues = new HashMap<>();
        TableManager.addTable(tableName, this);
        Relation newRelation = StorageManager.schemaManager.createRelation(tableName, relation.getSchema());
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
                    if(Utils.checkCondition(searchCondition, tuple)) {
                        tempBlock.appendTuple(tuple);
                        numberOfTuples++;
                        if(tempBlock.isFull()) {
                            StorageManager.memory.setBlock(validMemoryBlock, tempBlock);
                            validMemoryBlock++;
                            tempBlock.clear();
                        }
                    }
                }
            }
            if(dBlockIndex + maxMemoryBlocks >= totalDiskBlocks && !tempBlock.isEmpty()) {
                StorageManager.memory.setBlock(validMemoryBlock, tempBlock);
                validMemoryBlock++;
            }
            if(validMemoryBlock != 0)
                newRelation.setBlocks(validDiskBlock, 0, validMemoryBlock);
            validDiskBlock += validMemoryBlock;
        }
        return true;
    }
}
