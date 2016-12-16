package classes;

import java.util.ArrayList;
import java.util.List;

import lqp.Table;
import lqp.TableManager;
import storageManager.FieldType;
import storageManager.Schema;
import storageManager.StorageManager;

public class CreateTableStatement extends Statement {
    public String tableName;
    public List<AttributeType> attrTypeList;
    
    public CreateTableStatement() {
        attrTypeList = new ArrayList<>();
    }

    @Override
    public boolean execute() {
        ArrayList<String> fieldNames = new ArrayList<>();
        ArrayList<FieldType> fieldTypes = new ArrayList<>();
        for(AttributeType attributeType : attrTypeList) {
            fieldNames.add(attributeType.attribute);
            fieldTypes.add(attributeType.type);
        }
        
        Schema schema = new Schema(fieldNames, fieldTypes);

        if(StorageManager.schemaManager.createRelation(tableName, schema) == null)
            return false;
        Table table = new Table(tableName);
        TableManager.addTable(tableName, table);
        return true;
    }
}
