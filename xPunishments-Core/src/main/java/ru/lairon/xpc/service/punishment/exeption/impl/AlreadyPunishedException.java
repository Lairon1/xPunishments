package ru.lairon.xpc.service.punishment.exeption.impl;

import ru.lairon.xpc.model.User;
import ru.lairon.xpc.model.Punishment;
import ru.lairon.xpc.service.punishment.exeption.ExecutionPunishmentException;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class AlreadyPunishedException extends ExecutionPunishmentException {

    private final User user;
    private final Punishment punishment;

    public AlreadyPunishedException(@NonNull User user, @NonNull Punishment punishment) {
        super("User " + user.getName() + " is already punished");
        this.user = user;
        this.punishment = punishment;
    }
}
