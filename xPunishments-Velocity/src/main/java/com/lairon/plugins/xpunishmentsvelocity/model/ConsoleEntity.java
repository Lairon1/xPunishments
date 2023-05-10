package com.lairon.plugins.xpunishmentsvelocity.model;

import ru.lairon.service.namedentity.NamedEntity;

import java.util.UUID;

public class ConsoleEntity implements NamedEntity {

    private static final UUID UUID = java.util.UUID.fromString("00000000-0000-0000-0000-000000000000");
    private static final String NAME = "CONSOLE";

    private ConsoleEntity() {}

    @Override
    public java.util.UUID getUUID() {
        return UUID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    private static final ConsoleEntity instance = new ConsoleEntity();

    public static ConsoleEntity getInstance(){
        return instance;
    }

}
