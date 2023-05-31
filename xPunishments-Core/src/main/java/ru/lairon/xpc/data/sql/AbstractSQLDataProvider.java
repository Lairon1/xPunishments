package ru.lairon.xpc.data.sql;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.lairon.service.namedentity.impl.DefaultNamedEntity;
import ru.lairon.xpc.data.DataProvider;
import ru.lairon.xpc.model.Punishment;
import ru.lairon.xpc.model.PunishmentHistoryNode;
import ru.lairon.xpc.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractSQLDataProvider implements DataProvider {


    @Override
    @SneakyThrows
    public Optional<User> findByUUID(@NonNull UUID uuid) {
        @Cleanup
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM `%s` WHERE `%s` = ?;"
                .formatted(Names.TABLE, Names.UUID));
        statement.setString(1, uuid.toString());
        @Cleanup
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return Optional.empty();
        return Optional.of(parsePlayer(resultSet));
    }

    @Override
    @SneakyThrows
    public Optional<User> findByName(@NonNull String name) {
        @Cleanup
        PreparedStatement statement
                = getConnection().prepareStatement("SELECT * FROM `%s` WHERE `%s` = ?;"
                .formatted(Names.TABLE, Names.NAME));
        statement.setString(1, name);
        @Cleanup
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return Optional.empty();
        return Optional.of(parsePlayer(resultSet));
    }

    @Override
    @SneakyThrows
    public void save(@NonNull User user) {
        String query = """
                INSERT INTO `%s` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE 
                `%s` = VALUES (`%s`),
                `%s` = VALUES (`%s`),
                `%s` = VALUES (`%s`),
                `%s` = VALUES (`%s`),
                `%s` = VALUES (`%s`),      
                `%s` = VALUES (`%s`),
                `%s` = VALUES (`%s`),
                `%s` = VALUES (`%s`),
                `%s` = VALUES (`%s`),
                `%s` = VALUES (`%s`);
                """.formatted(
                Names.TABLE,
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

    protected abstract Connection getConnection();

    private User parsePlayer(@NonNull ResultSet resultSet) throws SQLException {
        User user = new User(
                UUID.fromString(resultSet.getString(Names.UUID)),
                resultSet.getString(Names.NAME)
        );

        String muteExecutorUUIDString = resultSet.getString(Names.MUTE_OPERATOR_UUID);
        if (muteExecutorUUIDString != null) {
            user.setMute(new Punishment(
                    new DefaultNamedEntity(
                            UUID.fromString(muteExecutorUUIDString),
                            resultSet.getString(Names.MUTE_OPERATOR_NAME)
                    ),
                    resultSet.getLong(Names.MUTE_EXPRESS),
                    resultSet.getLong(Names.MUTE_ISSUED)
            ));
        }

        String banExecutorUUIDString = resultSet.getString(Names.BAN_OPERATOR_UUID);
        if (banExecutorUUIDString != null) {
            user.setBan(new Punishment(
                    new DefaultNamedEntity(
                            UUID.fromString(banExecutorUUIDString),
                            resultSet.getString(Names.BAN_OPERATOR_NAME)
                    ),
                    resultSet.getLong(Names.BAN_EXPRESS),
                    resultSet.getLong(Names.BAN_ISSUED)
            ));
        }

        return user;
    }

    @Override
    public void addHistoryNode(@NonNull PunishmentHistoryNode node) {

    }

    @Override
    public List<PunishmentHistoryNode> loadHistory(long during) {
        return List.of();
    }

    @SneakyThrows
    protected void createTables() {
        @Cleanup Statement statement = getConnection().createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `%s`
                (
                    `%s` VARCHAR(36) NOT NULL,
                    `%s` VARCHAR(16) NOT NULL,
                                
                    `%s` VARCHAR(36) DEFAULT NULL,
                    `%s` VARCHAR(16) DEFAULT NULL,
                    `%s` LONGTEXT    DEFAULT NULL,
                    `%s` LONG   DEFAULT NULL,
                    `%s` LONG   DEFAULT NULL,
                                
                    `%s` VARCHAR(36) DEFAULT NULL,
                    `%s` VARCHAR(16) DEFAULT NULL,
                    `%s` LONGTEXT    DEFAULT NULL,
                    `%s` LONG   DEFAULT NULL,
                    `%s` LONG   DEFAULT NULL,
                                
                    PRIMARY KEY (`%s`),
                    UNIQUE (`%s`)
                );
                """.formatted(
                Names.TABLE,
                Names.UUID,
                Names.NAME,

                Names.MUTE_OPERATOR_UUID,
                Names.MUTE_OPERATOR_NAME,
                Names.MUTE_REASON,
                Names.MUTE_EXPRESS,
                Names.MUTE_ISSUED,

                Names.BAN_OPERATOR_UUID,
                Names.BAN_OPERATOR_NAME,
                Names.BAN_REASON,
                Names.BAN_EXPRESS,
                Names.BAN_ISSUED,

                Names.UUID,
                Names.NAME
        ));
    }

    public class Names {
        public final static String
                TABLE = "xpun_players",
                UUID = "uuid",
                NAME = "name",

        MUTE_OPERATOR_UUID = "mute_operator_uuid",
                MUTE_OPERATOR_NAME = "mute_operator_name",
                MUTE_REASON = "mute_reason",
                MUTE_EXPRESS = "mute_express",
                MUTE_ISSUED = "mute_issued",

        BAN_OPERATOR_UUID = "ban_operator_uuid",
                BAN_OPERATOR_NAME = "ban_operator_name",
                BAN_REASON = "ban_reason",
                BAN_EXPRESS = "ban_express",
                BAN_ISSUED = "ban_issued";
    }

}
