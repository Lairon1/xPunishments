package com.lairon.xpc.model;

import lombok.Data;

@Data
public class PunishmentHistoryNode {

    private final NamedEntity player;
    private final Punishment punishment;
    private final PunishmentHistoryNodeType type;

}
