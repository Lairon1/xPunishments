package com.lairon.xpc.service;

import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;

public interface EntityService {
    boolean hasPermission(@NonNull NamedEntity entity, @NonNull String permission);

    void disconnect(@NonNull NamedEntity entity, @NonNull String reason);


}
