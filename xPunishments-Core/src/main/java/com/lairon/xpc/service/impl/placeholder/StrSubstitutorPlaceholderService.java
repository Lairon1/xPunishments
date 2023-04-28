package com.lairon.xpc.service.impl.placeholder;

import com.lairon.xpc.model.NamedEntity;
import lombok.NonNull;
import org.apache.commons.lang3.text.StrSubstitutor;

import java.util.Map;

public class StrSubstitutorPlaceholderService extends AbstractPlaceholderService {

    @Override
    public String setPlaceholders(@NonNull NamedEntity entity, @NonNull String str, @NonNull Map<String, String> placeholders) {
        return new StrSubstitutor(placeholders, "{", "}").replace(str);
    }
}
