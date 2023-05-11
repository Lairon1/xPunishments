package com.lairon.xpc.service.ep.exeption.impl;

import com.lairon.xpc.model.User;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.service.ep.exeption.ExecutionPunishmentException;
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
