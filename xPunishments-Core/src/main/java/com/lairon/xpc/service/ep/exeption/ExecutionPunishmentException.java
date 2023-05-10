package com.lairon.xpc.service.ep.exeption;

import lombok.NonNull;

public class ExecutionPunishmentException extends Exception{

    public ExecutionPunishmentException(@NonNull String message) {
        super(message);
    }
}
