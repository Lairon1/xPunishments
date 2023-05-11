package com.lairon.xpc.service.impl;

import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.config.settings.SettingsConfig;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.model.User;
import com.lairon.xpc.service.PlaceholderParserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.lairon.service.namedentity.NamedEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class DefaultPlaceholderParserService implements PlaceholderParserService{

    private final LangConfig lang;
    private final SettingsConfig settings;

    @Override
    public Map<String, Object> toPlaceholdersPunishment(@NonNull User user, @NonNull Punishment punishment) {
        return new HashMap<>(){{
            put("operator_name", punishment.getOperator().getName());
            put("operator_uuid", punishment.getOperator().getUUID());
            put("user_name", user.getName());
            put("user_uuid", user.getUUID());
            put("reason", punishment.getReason() == null ? lang.getReasonNotIndicated() : punishment.getReason());
            put("duration", formatPunishmentDuration(punishment));
            put("issued", formatPunishmentIssued(punishment));
        }};
    }

    @Override
    public Map<String, Object> toPlaceholdersEntity(@NonNull NamedEntity entity) {
        return new HashMap<>(){{
            put("user_uuid", entity.getUUID());
            put("user_name", entity.getName());
        }};
    }


    public String formatPunishmentDuration(@NonNull Punishment punishment) {
        if(punishment.getDuration() == -1) return lang.getPermanent();
        return punishment.getDuration() + "";
    }

    public String formatPunishmentIssued(@NonNull Punishment punishment) {
        if(punishment.getIssued() == -1) return lang.getPermanent();
        return new SimpleDateFormat(settings.getDateFormat()).format(new Date(punishment.getIssued() + punishment.getDuration()));
    }

}
