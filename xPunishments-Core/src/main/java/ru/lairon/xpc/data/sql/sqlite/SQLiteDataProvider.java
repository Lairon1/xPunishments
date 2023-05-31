package ru.lairon.xpc.data.sql.sqlite;

import lombok.Cleanup;
import ru.lairon.xpc.data.sql.AbstractSQLDataProvider;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.lairon.xpc.model.Punishment;
import ru.lairon.xpc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    public void save(@NonNull User user) {
        String query = """
                INSERT INTO `%s` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT(`%s`) DO UPDATE SET
                `%s` = excluded.%s,
                `%s` = excluded.%s,
                `%s` = excluded.%s,
                `%s` = excluded.%s,
                `%s` = excluded.%s,      
                `%s` = excluded.%s,
                `%s` = excluded.%s,
                `%s` = excluded.%s,
                `%s` = excluded.%s,
                `%s` = excluded.%s;
                """.formatted(
                Names.TABLE, Names.UUID,
                Names.MUTE_OPERATOR_UUID, Names.MUTE_OPERATOR_UUID,
                Names.MUTE_OPERATOR_NAME, Names.MUTE_OPERATOR_NAME,
                Names.MUTE_REASON, Names.MUTE_REASON,
                Names.MUTE_EXPRESS, Names.MUTE_EXPRESS,
                Names.MUTE_ISSUED, Names.MUTE_ISSUED,

                Names.BAN_OPERATOR_UUID, Names.BAN_OPERATOR_UUID,
                Names.BAN_OPERATOR_NAME, Names.BAN_OPERATOR_NAME,
                Names.BAN_REASON, Names.BAN_REASON,
                Names.BAN_EXPRESS, Names.BAN_EXPRESS,
                Names.BAN_ISSUED, Names.BAN_ISSUED);
        @Cleanup
        PreparedStatement statement = getConnection().prepareStatement(query);

        statement.setString(1, user.getUUID().toString());
        statement.setString(2, user.getName());

        if (user.getMute() != null) {
            Punishment mute = user.getMute();
            statement.setString(3, mute.getOperator().getUUID().toString());
            statement.setString(4, mute.getOperator().getName());
            statement.setString(5, mute.getReason());
            statement.setLong(6, mute.getDuration());
            statement.setLong(7, mute.getIssued());
        } else {
            statement.setString(3, null);
            statement.setString(4, null);
            statement.setString(5, null);
            statement.setLong(6, 0);
            statement.setLong(7, 0);
        }

        if (user.getBan() != null) {
            Punishment ban = user.getBan();
            statement.setString(8, ban.getOperator().getUUID().toString());
            statement.setString(9, ban.getOperator().getName());
            statement.setString(10, ban.getReason());
            statement.setLong(11, ban.getDuration());
            statement.setLong(12, ban.getIssued());
        } else {
            statement.setString(8, null);
            statement.setString(9, null);
            statement.setString(10, null);
            statement.setLong(11, 0);
            statement.setLong(12, 0);
        }
        statement.execute();

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
