package com.lairon.xpc.model;

import lombok.Data;

@Data
public class Punishment {

    private final NamedEntity executor;
    private String reason;
    private final long duration;
    private final long issued;

}
