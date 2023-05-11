package ru.lairon.xpc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.lairon.service.namedentity.NamedEntity;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class User implements NamedEntity, Punishable {

    private final UUID uuid;
    private final String name;
    private Punishment mute;
    private Punishment ban;

    public UUID getUUID() {
        return uuid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof NamedEntity entity)) return false;
        return uuid.equals(entity.getUUID());
    }

}
