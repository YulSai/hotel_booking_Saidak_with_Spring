package com.company.hotel_booking.dao.connection;

import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Class with methods for connecting and interacting with the database
 */
@Log4j2
public class ProxyConnection implements Connection {
    private final Connection connection;
    private final DataSource dataSource;

    ProxyConnection(Connection connection, DataSource dataSource) {
        this.connection = connection;
        this.dataSource = dataSource;
    }

    @Override
    public void close() {
        log.debug("Connection 'close' used 'releaseConnection'");
        dataSource.getConnectionPool().releaseConnection(this);
    }

    void reallyClose() throws SQLException {
        log.debug("Connection was really close");
        connection.close();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.connection.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.connection.isWrapperFor(iface);
    }

    public Statement createStatement() throws SQLException {
        return this.connection.createStatement();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.connection.prepareStatement(sql);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return this.connection.prepareCall(sql);
    }

    public String nativeSQL(String sql) throws SQLException {
        return this.connection.nativeSQL(sql);
    }

    public boolean getAutoCommit() throws SQLException {
        return this.connection.getAutoCommit();
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.connection.setAutoCommit(autoCommit);
    }

    public void commit() throws SQLException {
        this.connection.commit();
    }

    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    public boolean isClosed() throws SQLException {
        return this.connection.isClosed();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return this.connection.getMetaData();
    }

    public boolean isReadOnly() throws SQLException {
        return this.connection.isReadOnly();
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        this.connection.setReadOnly(readOnly);
    }

    public String getCatalog() throws SQLException {
        return this.connection.getCatalog();
    }

    public void setCatalog(String catalog) throws SQLException {
        this.connection.setCatalog(catalog);
    }

    public int getTransactionIsolation() throws SQLException {
        return this.connection.getTransactionIsolation();
    }

    public void setTransactionIsolation(int level) throws SQLException {
        this.connection.setTransactionIsolation(level);
    }

    public SQLWarning getWarnings() throws SQLException {
        return this.connection.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        this.connection.clearWarnings();
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.connection.createStatement(resultSetType, resultSetConcurrency);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this.connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetType);
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return this.connection.getTypeMap();
    }

    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        this.connection.setTypeMap(map);
    }

    public int getHoldability() throws SQLException {
        return this.connection.getHoldability();
    }

    public void setHoldability(int holdability) throws SQLException {
        this.connection.setHoldability(holdability);
    }

    public Savepoint setSavepoint() throws SQLException {
        return this.connection.setSavepoint();
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        return this.connection.setSavepoint();
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        this.connection.rollback(savepoint);
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        this.connection.releaseSavepoint(savepoint);
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        return this.connection.createStatement();
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        return this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
        return this.connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return this.connection.prepareStatement(sql, autoGeneratedKeys);
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return this.connection.prepareStatement(sql, columnIndexes);
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return this.connection.prepareStatement(sql, columnNames);
    }

    public Clob createClob() throws SQLException {
        return this.connection.createClob();
    }

    public Blob createBlob() throws SQLException {
        return this.connection.createBlob();
    }

    public NClob createNClob() throws SQLException {
        return this.connection.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return this.connection.createSQLXML();
    }

    public boolean isValid(int timeout) throws SQLException {
        return this.connection.isValid(timeout);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        this.connection.setClientInfo(name, value);
    }

    public String getClientInfo(String name) throws SQLException {
        return this.connection.getClientInfo(name);
    }

    public Properties getClientInfo() throws SQLException {
        return this.connection.getClientInfo();
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        this.connection.setClientInfo(properties);
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return this.connection.createArrayOf(typeName, elements);
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return this.connection.createStruct(typeName, attributes);
    }

    public String getSchema() throws SQLException {
        return this.connection.getSchema();
    }

    public void setSchema(String schema) throws SQLException {
        this.connection.setSchema(schema);
    }

    public void abort(Executor executor) throws SQLException {
        this.connection.abort(executor);
    }

    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        this.connection.setNetworkTimeout(executor, milliseconds);
    }

    public int getNetworkTimeout() throws SQLException {
        return this.connection.getNetworkTimeout();
    }
}