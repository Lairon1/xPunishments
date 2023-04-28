package com.lairon.xpc.service;

import com.lairon.xpc.model.NamedEntity;
import com.lairon.xpc.model.Player;
import lombok.NonNull;

public interface BanService {

    void ban(@NonNull NamedEntity operator, @NonNull Player player, @NonNull String reason);
    void tempBan(@NonNull NamedEntity operator, @NonNull Player player, @NonNull String reason, long duration);
    void unban(@NonNull NamedEntity operator, @NonNull Player player, @NonNull String reason);

}
