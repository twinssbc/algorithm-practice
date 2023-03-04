import java.util.*;
import java.util.function.BiPredicate;

public class Database {
    private class Table {
        private final String name;
        private final Map<String, Integer> columns;
        private final List<Record> records;
        private int nextId = 0;

        public Table(String name, Map<String, Integer> columns) {
            this.name = name;
            this.columns = columns;
            this.records = new ArrayList<>();
        }

        public int generateId () {
            return this.nextId++;
        }

        public Map<String, Integer> getColumns() {
            return this.columns;
        }

        public List<Record> getRecords() {
            return this.records;
        }

        public void insertRecord(Record record) {
            this.records.add(record);
        }
    }

    private class Record {
        private final int id;
        private final List<Integer> data;

        public Record(int id, List<Integer> data) {
            this.id = id;
            this.data = data;
        }

        public int getId() {
            return id;
        }

        public List<Integer> getData() {
            return data;
        }
    }

    private class Condition {
        private final String columnName;
        private final String op;
        private final String value;

        public Condition(String columnName, String op, String value) {
            this.columnName = columnName;
            this.op = op;
            this.value = value;
        }

        public String getColumnName() {
            return this.columnName;
        }

        public String getOp() {
            return this.op;
        }

        public String getValue() {
            return this.value;
        }
    }

    private final HashMap<String, Table> tables;
    private static final String FROM_TOKEN = "FROM";
    private static final String WHERE_TOKEN = "WHERE";
    private static final String START_PARA_TOKEN = "(";
    private static final String END_PARA_TOKEN = ")";
    private static final String AND_TOKEN = "AND";
    private final Map<String, BiPredicate<Integer, Integer>> opMap;
    public Database () {
        tables = new HashMap<>();
        opMap = new HashMap<>();
        opMap.put("<=", (v1, v2) -> v1 <= v2);
        opMap.put("=", (v1, v2) -> v1.equals(v2));
        opMap.put(">=", (v1, v2) -> v1 >= v2);
    }

    public void create(String[] sql) {
        String tableName = sql[2];
        if(tables.containsKey(tableName)) {
            throw new RuntimeException("The table already exists: " + tableName);
        }

        int startParaTokenIndex = seekTokenIndex(sql, 3, START_PARA_TOKEN);
        if(startParaTokenIndex == -1) {
            throw new RuntimeException("Invalid sql: " + Arrays.toString(sql));
        }
        Map<String, Integer> columns = new HashMap<>();
        int columnCount = 0;
        for(int i = startParaTokenIndex + 1; i < sql.length -1; i++) {
            if(!sql[i].equals(",")) {
                columns.put(sql[i], columnCount++);
            }
        }
        Table table = new Table(tableName, columns);
        tables.put(tableName, table);
    }

    public int insert(String[] sql) {
        String tableName = sql[2];
        if(!tables.containsKey(tableName)) {
            throw new RuntimeException("The table doesn't exist: " + tableName);
        }

        int startParaTokenIndex = seekTokenIndex(sql, 3, START_PARA_TOKEN);
        if(startParaTokenIndex == -1) {
            throw new RuntimeException("Invalid sql: " + Arrays.toString(sql));
        }

        Table table = tables.get(tableName);
        int columnCount = table.getColumns().size();
        List<Integer> data = new ArrayList<>();
        for(int i = startParaTokenIndex + 1; i < sql.length - 1; i++) {
            if(!sql[i].equals(",")) {
                data.add(Integer.valueOf(sql[i]));
            }
        }
        if(data.size() != columnCount) {
            throw new RuntimeException("Invalid field count: " + Arrays.toString(sql));
        }

        int nextId = table.generateId();
        Record newRecord = new Record(nextId, data);
        table.insertRecord(newRecord);
        return nextId;
    }

    public Integer[] select(String[] sql) {
        int fromTokenIndex = seekTokenIndex(sql, 0, FROM_TOKEN);

        if(fromTokenIndex == -1 ) {
            throw new RuntimeException("Invalid sql: " + Arrays.toString(sql));
        }

        String tableName = sql[fromTokenIndex + 1];
        Table table = tables.get(tableName);
        if(table == null) {
            throw new RuntimeException("Table doesn't exist: " + tableName);
        }

        Map<String, Integer> tableColumns = table.getColumns();
        List<Condition> conditions = new ArrayList<>();
        int whereTokenIndex = seekTokenIndex(sql, fromTokenIndex + 1, WHERE_TOKEN);
        if(whereTokenIndex > 0) {
            int startParamToken = seekTokenIndex(sql, whereTokenIndex + 1, START_PARA_TOKEN );
            if(startParamToken == -1 ) {
                throw new RuntimeException("Invalid sql: " + Arrays.toString(sql));
            }
            int i = startParamToken + 1;
            while(i + 2 < sql.length -1) {
                Condition condition = new Condition(sql[i], sql[i+1], sql[i+2]);
                conditions.add(condition);
                if(sql[i+3].equals(AND_TOKEN)) {
                    i += 4;
                } else {
                    break;
                }
            }
        }

        //start query
        List<Integer> result = new ArrayList<>();
        for(Record record: table.getRecords()) {
            if(conditions.size() == 0) {
                result.add(record.getId());
            } else {
                List<Integer> data = record.getData();
                boolean matches = true;
                for(Condition condition: conditions) {
                    BiPredicate<Integer, Integer> predicate = opMap.get(condition.getOp());
                    Integer realValue = data.get(tableColumns.get(condition.getColumnName()));
                    Integer testValue = Integer.valueOf(condition.getValue());
                    if(!predicate.test(realValue, testValue)) {
                        matches = false;
                        break;
                    }
                }

                if(matches) {
                    result.add(record.getId());
                }
            }
        }
        return result.toArray(new Integer[0]);
    }

    private int seekTokenIndex(String[] tokens, int startIndex, String token) {
        while(startIndex < tokens.length) {
            if(tokens[startIndex].equals(token)) {
                return startIndex;
            }
            startIndex++;
        }
        return -1;
    }

    public static void main(String[] args) {
        Database db = new Database();
        db.create(new String[] {"CREATE", "TABLE", "table1", "(", "age", ",", "height", ")"});
        System.out.println(db.insert(new String[] {"INSERT", "INTO", "table1", "(", "20", "180", ")"}));
        System.out.println(db.insert(new String[] {"INSERT", "INTO", "table1", "(", "20", "188", ")"}));
        System.out.println(db.insert(new String[] {"INSERT", "INTO", "table1", "(", "30", "175", ")"}));
        System.out.println(db.insert(new String[] {"INSERT", "INTO", "table1", "(", "40", "160", ")"}));

        System.out.println(Arrays.toString(db.select(new String[]{"SELECT", "*", "FROM", "table1", "WHERE", "(", "age", "=", "20", "AND", "height", ">=", "181", ")"})));
        System.out.println(Arrays.toString(db.select(new String[]{"SELECT", "height", "FROM", "table1", "WHERE", "(", "age", "<=", "32", ")"})));
    }
}
