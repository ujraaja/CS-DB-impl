package classes;

public class ColumnName extends Term {
    public String tableName;
    public String attribute;
    
    @Override
    public String toString() {
        if(tableName == null)
            return attribute;
        return tableName + "." + attribute;
    }
}
