package com.lairon.xpc.service;

import com.lairon.xpc.model.NamedEntity;
import lombok.NonNull;

public interface PlayerService {
    void sendMessage(@NonNull NamedEntity player, @NonNull String message);

    boolean hasPermission(@NonNull NamedEntity player, @NonNull String permission);

    void disconnect(@NonNull NamedEntity player, @NonNull String reason);

    void announce(@NonNull String message);
}
