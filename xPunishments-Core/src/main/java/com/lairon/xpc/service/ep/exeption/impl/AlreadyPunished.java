package com.lairon.xpc.service.ep.exeption.impl;

import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.service.ep.exeption.ExecutionPunishmentException;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class AlreadyPunished extends ExecutionPunishmentException {

    private final Player player;
    private final Punishment punishment;

    public AlreadyPunished(@NonNull Player player, @NonNull Punishment punishment) {
        super("Player " + player.getName() + " is already punished");
        this.player = player;
        this.punishment = punishment;
    }
}
