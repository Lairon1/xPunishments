package ru.lairon.xpc.service;

import ru.lairon.xpc.model.Punishment;
import ru.lairon.xpc.model.User;
import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;

import java.util.Map;

public interface PlaceholderParserService {

    Map<String, Object> toPlaceholdersPunishment(@NonNull User user, @NonNull Punishment punishment);
    Map<String, Object> toPlaceholdersEntity(@NonNull NamedEntity entity);

}
