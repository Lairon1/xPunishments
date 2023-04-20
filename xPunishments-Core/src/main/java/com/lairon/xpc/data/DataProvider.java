package com.lairon.xpc.data;

import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.PunishmentHistoryNode;
import com.lairon.xpc.model.PunishmentHistoryNodeType;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DataProvider {

    Optional<Player> findByUUID(@NonNull UUID uuid);
    Optional<Player> findByName(@NonNull String name);
    void addHistoryNode(@NonNull PunishmentHistoryNode node);
    List<PunishmentHistoryNode> loadHistory(long during);
    void save(@NonNull Player player);

}
