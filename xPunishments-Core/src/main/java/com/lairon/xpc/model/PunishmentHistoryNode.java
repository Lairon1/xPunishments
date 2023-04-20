package com.lairon.xpc.model;

import lombok.Data;

@Data
public class PunishmentHistoryNode {

    private final Player punished;
    private final Punishment punishment;
    private final PunishmentHistoryNodeType type;

}
