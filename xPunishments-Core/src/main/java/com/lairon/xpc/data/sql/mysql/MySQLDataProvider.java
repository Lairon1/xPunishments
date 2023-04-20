package com.lairon.xpc.data.sql.mysql;

import com.lairon.xpc.data.sql.AbstractSQLDataProvider;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataProvider extends AbstractSQLDataProvider {

    private final String
            address,
            database,
            username,
            password;
    private final int port;

    public MySQLDataProvider(@NonNull String address,
                             @NonNull String database,
                             @NonNull String username,
                             @NonNull String password,
                             @NonNull int port) {
        this.address = address;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
        createTables();
    }

    private Connection connection;


    @Override
    @SneakyThrows
    protected Connection getConnection() {
        return connection == null || connection.isClosed() ? createNewConnection() : connection;
    }

    private Connection createNewConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://%s:%d/%s?user=%s&password=%s"
                        .formatted(address, port, database, username, password));
        return connection;
    }
}
