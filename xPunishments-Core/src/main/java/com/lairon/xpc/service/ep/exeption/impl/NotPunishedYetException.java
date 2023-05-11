package com.lairon.xpc.service.ep.exeption.impl;

import com.lairon.xpc.model.User;
import com.lairon.xpc.service.ep.exeption.ExecutionPunishmentException;
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
