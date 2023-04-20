package com.lairon.xpc.handler;

import com.lairon.xpc.model.NamedEntity;
import com.lairon.xpc.model.Player;
import lombok.NonNull;

public interface BanHandler {

    void ban(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason);
    void tempBan(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason, long time);
    void unBan(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason);

}
