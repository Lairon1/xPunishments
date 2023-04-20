package com.lairon.xpc.model.impl;

import com.lairon.xpc.model.NamedEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class DefaultNamedEntity implements NamedEntity {

    private final UUID uuid;
    private final String name;

}
