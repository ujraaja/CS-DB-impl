package lqp;

import java.util.HashMap;
import java.util.Map;

public class Table {
    public String tableName;
    public int numberOfTuples;
    public Map<String, Map<String, Integer>> attrValues;

    public Table() {
    }
    
    public Table(String tableName) {
        this.tableName = tableName;
        attrValues = new HashMap<>();
        numberOfTuples = 0;
    }
    
    // This function would only be called for the base relations, so nothing to be done on base relations.
    public boolean execute() {
        return true;
    }
}
