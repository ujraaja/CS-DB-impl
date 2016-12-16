package classes;

import storageManager.Block;
import storageManager.Config;
import storageManager.Relation;
import storageManager.StorageManager;
import storageManager.Tuple;

public class DeleteStatement extends Statement {
    public String tableName;
    public SearchCondition searchCondition = null;

    @Override
    public boolean execute() {
        Relation relation = StorageManager.schemaManager.getRelation(tableName);
        int totalDiskBlocks = relation.getNumOfBlocks();
        if(totalDiskBlocks == 0)
            return true;
        if(searchCondition == null) {
            relation.deleteBlocks(0);
            return true;
        }
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
                    if(!Utils.checkCondition(searchCondition, tuple)) {
                        tempBlock.appendTuple(tuple);
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
                relation.setBlocks(validDiskBlock, 0, validMemoryBlock);
            validDiskBlock += validMemoryBlock;
        }
        if(validDiskBlock < totalDiskBlocks)
            relation.deleteBlocks(validDiskBlock);
        return true;
    }
}
