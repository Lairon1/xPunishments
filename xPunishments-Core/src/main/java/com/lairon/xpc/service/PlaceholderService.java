package com.lairon.xpc.service;

import com.lairon.xpc.model.NamedEntity;
import lombok.NonNull;

import java.util.Map;

public interface PlaceholderService {

    String setPlaceholders(@NonNull NamedEntity entity, @NonNull String str, @NonNull Map<String, String> placeholders);
    String setPlaceholders(@NonNull NamedEntity entity, @NonNull String str, @NonNull String... placeholders);

}
