package com.lairon.xpc.handler.punishment;

import com.lairon.xpc.model.User;
import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;

public interface PunishmentHandler {

    void permanent(@NonNull NamedEntity executor, @NonNull User user, String reason, boolean silent);

    void temporary(@NonNull NamedEntity executor, @NonNull User user, long duration, String reason, boolean silent);

    void pardon(@NonNull NamedEntity executor, @NonNull User user, boolean silent);
}
