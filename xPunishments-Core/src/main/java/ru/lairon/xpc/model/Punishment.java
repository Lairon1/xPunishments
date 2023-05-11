package ru.lairon.xpc.model;

import lombok.Data;
import ru.lairon.service.namedentity.NamedEntity;

@Data
public class Punishment {

    private final NamedEntity operator;
    private String reason;
    private final long duration;
    private final long issued;

}
