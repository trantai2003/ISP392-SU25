/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ehospital.JDBC;

/**
 *
 * @author admin
 */
import ehospital.constant.ServerConnectionInfo;
import ehospital.anotation.Column;
import ehospital.anotation.FindBy;
import ehospital.anotation.Id;
import ehospital.anotation.Query;
import ehospital.anotation.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T, K> implements BaseDAO<T, K> {

    private final String tableName;
    private final Connection connection;
    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.tableName = "[" + getTableName(entityClass) + "]";
        this.connection = ServerConnectionInfo.CONNECTION;
    }

    private String getTableName(Class<T> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return entityClass.getAnnotation(Table.class).name();
        }
        throw new IllegalArgumentException("Entity " + entityClass.getSimpleName() + " must have @Table annotation");
    }

    private String getKeyFieldName() {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getAnnotation(Column.class).name();
            }
        }
        return null;
    }

    protected String getInsertQuery() {
        String setIdIndentity = "";
//        setIdIndentity = "SET IDENTITY_INSERT " + tableName + " OFF; ";
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder values = new StringBuilder(" VALUES (");

        // Cách viết gọn hơn:
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)
                    && (!field.isAnnotationPresent(Id.class) || !field.getAnnotation(Id.class).auto())) {
                sql.append(field.getAnnotation(Column.class).name()).append(", ");
                values.append("?, ");
            }
        }

        sql.setLength(sql.length() - 2);
        values.setLength(values.length() - 2);
        sql.append(")").append(values).append(")");

        return setIdIndentity + sql.toString();
    }

    protected String getUpdateQuery() {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        String location = " WHERE " + getKeyFieldName() + " = ?";
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
                sql.append(field.getAnnotation(Column.class).name()).append(" = ?, ");
            }
        }
        sql.setLength(sql.length() - 2);
        sql.append(location);
        return sql.toString();
    }

    protected void setInsertParams(PreparedStatement stmt, T t) throws SQLException {
        int index = 1;
        try {
            for (Field field : entityClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)
                        && (!field.isAnnotationPresent(Id.class) || !field.getAnnotation(Id.class).auto())) {
                    field.setAccessible(true);
                    stmt.setObject(index++, field.get(t));
                }
            }
        } catch (IllegalAccessException e) {
            throw new SQLException("Error setting insert parameters", e);
        }
    }

    protected void setUpdateParams(PreparedStatement stmt, T t) throws SQLException {
        int index = 1;
        try {
            Field idField = null;
            for (Field field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                }
                if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    stmt.setObject(index++, field.get(t));
                }
            }
            if (idField == null) {
                throw new SQLException("Error setting update parameters");
            }
            stmt.setObject(index, idField.get(t));
            System.out.println(index);
        } catch (IllegalAccessException e) {
            throw new SQLException("Error setting update parameters", e);
        }
    }

    @Override
    public boolean insert(T t) throws SQLException {
        String sql = getInsertQuery();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setInsertParams(stmt, t);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public K insertGetKey(T t) throws SQLException {
        String sql = getInsertQuery();
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setInsertParams(stmt, t);
            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    Object key = (K) rs.getObject(1);
                    // Chuyển đổi kiểu khóa chính dựa vào kiểu thực tế của nó
                    if (key instanceof BigDecimal) {
                        Integer key_int = ((BigDecimal) key).intValueExact();
                        return (K) key_int; // Nếu là Integer
                    } else if (key instanceof String) {
                        return (K) key; // Nếu là String
                    } else {
                        throw new SQLException("Không thể xử lý kiểu của khóa chính: " + key.getClass().getName());
                    }
                }
            }
            return null;
        }
    }

    @Override
    public List<T> getAll() throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        List<T> data = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(mapRow(rs));
            }
            return data;
        }
    }

    @Override
    public boolean update(T t) throws SQLException {
        String sql = getUpdateQuery();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setUpdateParams(stmt, t);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public T getById(K id) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + getKeyFieldName() + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(K id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + getKeyFieldName() + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<T> findByOr(Object... params) throws SQLException {
        String[] columnNames = null;
        // 🔥 Lấy phương thức nào gọi `findByOr()`
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName(); // Phương thức đang gọi `findByOr()`
        Method[] methods = this.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(FindBy.class) && methods[i].getName().equals(methodName)) {
                columnNames = methods[i].getAnnotation(FindBy.class).columns();
            }
        }
        if (columnNames == null) {
            throw new IllegalArgumentException("No method found with @FindBy annotation");
        }

        if (columnNames.length > params.length) {
            throw new IllegalArgumentException("Not enough params for this query");
        }

        if (columnNames.length < params.length) {
            throw new IllegalArgumentException("The number of parameters passed is more than necessary");
        }

        String sql = "SELECT * FROM " + tableName + " WHERE ";
        String operations = "";
        for (int i = 0; i < columnNames.length; i++) {
            operations += (columnNames[i] + " = ? or ");
        }
        operations += "1=0;";
        sql = sql + operations;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < columnNames.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();

            List<T> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        }
    }

    public List<T> findByAnd(Object... params) throws SQLException {
        String[] columnNames = null;
        // 🔥 Lấy phương thức nào gọi `findByOr()`
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName(); // Phương thức đang gọi `findByOr()`
        Method[] methods = this.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(FindBy.class) && methods[i].getName().equals(methodName)) {
                columnNames = methods[i].getAnnotation(FindBy.class).columns();
            }
        }
        if (columnNames == null) {
            throw new IllegalArgumentException("No method found with @FindBy annotation");
        }

        if (columnNames.length > params.length) {
            throw new IllegalArgumentException("Not enough params for this query");
        }

        if (columnNames.length < params.length) {
            throw new IllegalArgumentException("The number of parameters passed is more than necessary");
        }

        String sql = "SELECT * FROM " + tableName + " WHERE ";
        String operations = "";
        for (int i = 0; i < columnNames.length; i++) {
            operations += (columnNames[i] + " = ? and ");
        }
        operations += "1=1;";
        sql = sql + operations;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < columnNames.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();

            List<T> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        }
    }

    public List<T> executeQueryFind(Object... params) throws SQLException {
        String querySql = null;

        // 🔥 Lấy phương thức nào gọi `findByOr()`
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName(); // Phương thức đang gọi `executeQueryFind()`
        Method[] methods = this.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(Query.class) && methods[i].getName().equals(methodName)) {
                querySql = methods[i].getAnnotation(Query.class).sql();
            }
        }

        if (querySql == null) {
            throw new IllegalArgumentException("No method found with @Query annotation");
        }

        try (PreparedStatement stmt = connection.prepareStatement(querySql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            List<T> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        }
    }

    public boolean executeQueryUpdateOrCheck(Object... params) throws SQLException {
        String querySql = null;

        // 🔥 Lấy phương thức nào gọi `findByOr()`
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName(); // Phương thức đang gọi `executeQueryUpdateOrCheck()`
        Method[] methods = this.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(FindBy.class) && methods[i].getName().equals(methodName)) {
                querySql = methods[i].getAnnotation(Query.class).sql();
            }
        }

        if (querySql == null) {
            throw new IllegalArgumentException("No method found with @Query annotation");
        }

        try (PreparedStatement stmt = connection.prepareStatement(querySql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            boolean success = stmt.executeUpdate() > 0;
            return success;
        }
    }

    @Override
    public T mapRow(ResultSet rs) throws SQLException {
        try {
            T obj = entityClass.getDeclaredConstructor().newInstance();
            for (Field field : entityClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(field.getAnnotation(Column.class).name()));
                }
            }
            return obj;
        } catch (Exception e) {
            throw new SQLException("Error mapping row", e);
        }
    }
}
