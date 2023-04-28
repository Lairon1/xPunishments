package com.lairon.xpc.service.impl.placeholder;

import com.lairon.xpc.model.NamedEntity;
import com.lairon.xpc.service.PlaceholderService;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPlaceholderService implements PlaceholderService {
    @Override
    public String setPlaceholders(@NonNull NamedEntity entity, @NonNull String str, @NonNull String... placeholders) {
        if(placeholders.length % 2 != 0)
            throw new IllegalArgumentException("Each placeholder must have a value");
        Map<String, String> plc = new HashMap<>();
        for (int i = 0; i < placeholders.length; i += 2) {
            plc.put(placeholders[i], placeholders[i+1]);
        }
        return setPlaceholders(entity, str, plc);
    }
}
