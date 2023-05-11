package ru.lairon.xpc.service.punishment.exeption;

import lombok.NonNull;

public class ExecutionPunishmentException extends Exception{

    public ExecutionPunishmentException(@NonNull String message) {
        super(message);
    }
}
