package com.lairon.xpc.data.sql.sqlite;

import com.lairon.xpc.data.sql.AbstractSQLDataProvider;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataProvider extends AbstractSQLDataProvider {

    private final String filePath;
    private Connection connection;

    public SQLiteDataProvider(@NonNull String filePath) {
        this.filePath = filePath;
        createTables();
    }

    @Override
    @SneakyThrows
    protected Connection getConnection() {
        return connection == null || connection.isClosed() ? createNewConnection() : connection;
    }

    private Connection createNewConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        return connection;
    }
}
