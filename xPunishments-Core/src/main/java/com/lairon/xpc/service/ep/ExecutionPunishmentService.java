package com.lairon.xpc.service.ep;

import com.lairon.xpc.model.User;
import com.lairon.xpc.service.ep.exeption.ExecutionPunishmentException;
import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;

public interface ExecutionPunishmentService {

    void temporarily(@NonNull NamedEntity operator, @NonNull User user, String reason, long duration)
            throws ExecutionPunishmentException;

    void permanent(@NonNull NamedEntity operator, @NonNull User user, String reason)
            throws ExecutionPunishmentException;

    void pardon(@NonNull NamedEntity operator, @NonNull User user)
            throws ExecutionPunishmentException;

}
