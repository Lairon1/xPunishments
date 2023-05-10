package com.lairon.xpc.service.ep;

import com.lairon.xpc.model.Player;
import com.lairon.xpc.service.ep.exeption.ExecutionPunishmentException;
import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;

public interface ExecutionPunishmentService {

    void temporarily(@NonNull NamedEntity operator, @NonNull Player player, String reason, long duration, boolean silent)
            throws ExecutionPunishmentException;

    void permanent(@NonNull NamedEntity operator, @NonNull Player player, String reason, boolean silent)
            throws ExecutionPunishmentException;

    void pardon(@NonNull NamedEntity operator, @NonNull Player player, boolean silent)
            throws ExecutionPunishmentException;

}
