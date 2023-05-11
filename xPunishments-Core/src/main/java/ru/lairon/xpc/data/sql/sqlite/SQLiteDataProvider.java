package ru.lairon.xpc.data.sql.sqlite;

import ru.lairon.xpc.data.sql.AbstractSQLDataProvider;
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
    @SneakyThrows
    private Connection createNewConnection() throws SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        return connection;
    }
}
