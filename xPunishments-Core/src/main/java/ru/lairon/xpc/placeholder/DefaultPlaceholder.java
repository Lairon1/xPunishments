package ru.lairon.xpc.placeholder;

import ru.lairon.xpc.config.lang.LangConfig;
import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;
import ru.lairon.service.placeholder.defaultplaceholder.Placeholder;
import ru.lairon.service.placeholder.defaultplaceholder.PlaceholderSettings;

public class DefaultPlaceholder extends Placeholder {

    private final LangConfig config;

    public DefaultPlaceholder(@NonNull LangConfig config) {
        super(PlaceholderSettings.builder("def")
                .author("0xLairon1")
                .version("0.0.1")
                .build());
        this.config = config;
    }

    @Override
    public String apply(NamedEntity namedEntity, String s) {
        String value = config.getDefaultPlaceholders().get(s);
        if(value == null) return "";
        else return value;
    }
}
