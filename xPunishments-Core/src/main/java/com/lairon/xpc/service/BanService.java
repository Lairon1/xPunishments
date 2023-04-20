package com.lairon.xpc.service;

import com.lairon.xpc.model.NamedEntity;
import com.lairon.xpc.model.Player;
import lombok.NonNull;

public interface BanService {

    void ban(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason);
    void tempBan(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason, long duration);
    void unban(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason);

}
