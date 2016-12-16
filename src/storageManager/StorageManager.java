package storageManager;

public class StorageManager {
    
    public static SchemaManager schemaManager;
    public static MainMemory memory; 
    public static Disk disk; 
    
    public StorageManager() {
        memory = new MainMemory();
        disk = new Disk();
        schemaManager = new SchemaManager(memory, disk);

        disk.resetDiskIOs();
        disk.resetDiskTimer();
    }
}
