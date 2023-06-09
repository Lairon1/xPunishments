package ru.lairon.xpc.data;

import ru.lairon.xpc.model.User;
import ru.lairon.xpc.model.PunishmentHistoryNode;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataProvider {

    Optional<User> findByUUID(@NonNull UUID uuid);
    Optional<User> findByName(@NonNull String name);
    void addHistoryNode(@NonNull PunishmentHistoryNode node);
    List<PunishmentHistoryNode> loadHistory(long during);
    void save(@NonNull User user);

}
