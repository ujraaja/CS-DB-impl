package classes;

import storageManager.StorageManager;

public class DropTableStatement extends Statement {
    public String tableName;

    @Override
    public boolean execute() {
        return StorageManager.schemaManager.deleteRelation(tableName);
    }
}
