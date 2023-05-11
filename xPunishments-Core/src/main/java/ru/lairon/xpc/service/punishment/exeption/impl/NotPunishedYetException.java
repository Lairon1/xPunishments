package ru.lairon.xpc.service.punishment.exeption.impl;

import ru.lairon.xpc.model.User;
import ru.lairon.xpc.service.punishment.exeption.ExecutionPunishmentException;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class NotPunishedYetException extends ExecutionPunishmentException {

    private User user;

    public NotPunishedYetException(@NonNull User user) {
        super("User " + user.getName() + " not punished yet");
        this.user = user;
    }

}
