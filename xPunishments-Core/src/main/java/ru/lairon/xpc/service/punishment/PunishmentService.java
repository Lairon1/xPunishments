package ru.lairon.xpc.service.punishment;

import ru.lairon.xpc.model.User;
import ru.lairon.xpc.service.punishment.exeption.ExecutionPunishmentException;
import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;

public interface PunishmentService {

    void temporarily(@NonNull NamedEntity operator, @NonNull User user, String reason, long duration)
            throws ExecutionPunishmentException;

    void permanent(@NonNull NamedEntity operator, @NonNull User user, String reason)
            throws ExecutionPunishmentException;

    void pardon(@NonNull NamedEntity operator, @NonNull User user)
            throws ExecutionPunishmentException;

    boolean can(@NonNull User user);
}
