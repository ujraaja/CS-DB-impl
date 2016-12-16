package lqp;

import java.util.HashMap;
import java.util.Map;

public class TableManager {
    private static Map<String, Table> tables;
    
    public TableManager() {
        tables = new HashMap<>();
    }
    
    public static Table getTable(String name) {
        if(tables.containsKey(name))
            return tables.get(name);
        System.err.println("Table '" + name + "' not found!!!");
        return null;
    }

    public static void addTable(String name, Table table) {
        tables.put(name, table);
    }

    public static void removeTable(String name) {
        tables.remove(name);
    }
}
