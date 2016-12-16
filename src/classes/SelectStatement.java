package classes;

import java.util.ArrayList;
import java.util.List;

import lqp.Distinct;
import lqp.Join;
import lqp.Project;
import lqp.Select;
import lqp.Sort;
import lqp.Table;
import lqp.TableManager;
import storageManager.Block;
import storageManager.Config;
import storageManager.Field;
import storageManager.FieldType;
import storageManager.Relation;
import storageManager.StorageManager;
import storageManager.Tuple;

public class SelectStatement extends Statement {
    public List<ColumnName> selectList;
    public List<String> tableList;

    public boolean isDistinct = false;
    private boolean isProjectionNeeded = true;
    public SearchCondition searchCondition = null;

    public ColumnName columnName = null;

    public List<Tuple> output;
    
    private void cleanupColumnNames() {
        if(selectList == null) {
            isProjectionNeeded = false;
            selectList = new ArrayList<ColumnName>();
            for(String table : tableList) {
                for(String attr : StorageManager.schemaManager.getSchema(table).getFieldNames()) {
                    ColumnName columnName = new ColumnName();
                    columnName.attribute = attr;
                    columnName.tableName = table;
                    selectList.add(columnName);
                }
            }
        }
        if(tableList.size() > 1) {
            for(int i = 0; i < selectList.size(); i++) {
                ColumnName columnName = selectList.get(i);
                columnName.attribute = columnName.tableName + '.' + columnName.attribute;
                columnName.tableName = "";
                selectList.set(i, columnName);
            }
            if(columnName != null) {
                columnName.attribute = columnName.tableName + '.' + columnName.attribute;
                columnName.tableName = "";
            }
        }
    }

    private Table populateLQP() {
        Table table;
        Join join = new Join();
        join.tables = new ArrayList<>();
        for(String name : tableList)
            join.tables.add(TableManager.getTable(name));
        table = join;
        if(searchCondition != null) {
            Select select = new Select();
            select.table = table;
            select.searchCondition = searchCondition;
            join.searchCondition = searchCondition;
            table = select;
        }
        if(isProjectionNeeded) {
            Project project = new Project();
            project.columnNames = selectList;
            project.table = table;
            table = project;
        }
        if(isDistinct) {
            Sort sort = new Sort();
            sort.table = table;
            sort.columnNames = selectList;
            table = sort;

            Distinct distinct = new Distinct();
            distinct.table = table;
            distinct.columnNames = selectList;
            table = distinct;
        }
        if(columnName != null) {
            Sort sort = new Sort();
            sort.table = table;
            sort.columnNames = new ArrayList<>();
            sort.columnNames.add(columnName);
            table = sort;
        }
        return table;
    }
    
    private void print(Table table, boolean isPrint) {
        Relation relation = StorageManager.schemaManager.getRelation(table.tableName);
        int totalDiskBlocks = relation.getNumOfBlocks();
        int maxMemoryBlocks = Config.NUM_OF_BLOCKS_IN_MEMORY;
        if(isPrint) {
            System.out.println("-----------------------------------------------------------------------------------------");
            for(String field : relation.getSchema().getFieldNames())
                System.out.print(field + "\t");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------");
        } else {
            output = new ArrayList<>();
        }
        for(int dBlockIndex = 0; dBlockIndex < totalDiskBlocks; dBlockIndex += maxMemoryBlocks) {
            int activeMemBlocks = Math.min(maxMemoryBlocks, totalDiskBlocks-dBlockIndex);
            relation.getBlocks(dBlockIndex, 0, activeMemBlocks);
            for(int mBlockIndex = 0; mBlockIndex < activeMemBlocks; mBlockIndex++) {
                Block block = StorageManager.memory.getBlock(mBlockIndex);
                for(Tuple tuple : block.getTuples()) {
                    if(isPrint) {
                        for(int fieldIndex = 0; fieldIndex < tuple.getNumOfFields(); fieldIndex++) {
                            Field field = tuple.getField(fieldIndex);
                            if(field.type == FieldType.INT)
                                System.out.print(field.integer + "\t");
                            else
                                System.out.print(field.str + "\t");
                        }
                        System.out.println();
                    } else {
                        output.add(tuple);
                    }
                }
            }
        }
        System.out.println("=========================================================================================");
    }
    
    private void deleteTables(Table table) {
        if(table.tableName.contains(":")) {
            if(StorageManager.schemaManager.relationExists(table.tableName)) {
                TableManager.removeTable(table.tableName);
                StorageManager.schemaManager.deleteRelation(table.tableName);
            }
            if(table instanceof Join)
                for(Table table1 : ((Join) table).tables)
                    deleteTables(table1);
            else if(table instanceof Distinct)
                deleteTables(((Distinct) table).table);
            else if(table instanceof Project)
                deleteTables(((Project) table).table);
            else if(table instanceof Select)
                deleteTables(((Select) table).table);
            else if(table instanceof Sort)
                deleteTables(((Sort) table).table);
        }
    }

    @Override
    public boolean execute() {
        cleanupColumnNames();
        Table table = populateLQP();
        table.execute();
        print(table, true);
        deleteTables(table);
        return true;
    }
    
    public boolean execute(InsertStatement insertStatement) {
        cleanupColumnNames();
        Table table = populateLQP();
        table.execute();
        print(table, false);
        deleteTables(table);
        return true;
    }
}
