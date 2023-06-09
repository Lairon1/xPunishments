package ru.lairon.xpc.service;

import ru.lairon.xpc.permission.Permission;
import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;

public interface EntityService {
    boolean hasPermission(@NonNull NamedEntity entity, @NonNull Permission permission);

    void disconnect(@NonNull NamedEntity entity, @NonNull String reason);


}
