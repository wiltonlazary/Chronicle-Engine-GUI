package net.openhft.chronicle.engine.gui;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rob Austin.
 */
public class KeyValueResultSetMetaData implements ResultSetMetaData {

    private final List<String> columns;

    public KeyValueResultSetMetaData(String... columns) {
        this.columns = Arrays.asList(columns);
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columns.size();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        return true;
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int isNullable(int column) throws SQLException {
        return 0;
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return columns.get(column - 1);
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return columns.get(column - 1);
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getScale(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getTableName(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        return "java.lang.String";
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("todo");
    }
}