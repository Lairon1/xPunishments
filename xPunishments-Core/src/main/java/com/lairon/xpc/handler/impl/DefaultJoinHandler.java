package com.lairon.xpc.handler.impl;

import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.config.settings.SettingsConfig;
import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.handler.JoinHandler;
import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.service.EntityService;
import com.lairon.xpc.service.PunishmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.lairon.service.placeholder.PlaceholderService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class DefaultJoinHandler implements JoinHandler {

    private final PunishmentService punishmentService;
    private final EntityService entityService;
    private final PlaceholderService placeholderService;
    private final LangConfig lang;
    private final SettingsConfig settings;

    @Override
    public void onJoin(@NonNull Player player) {
        if (!punishmentService.canJoin(player)) {
            Punishment ban = player.getBan();
            String cause = ban.getDuration() == -1
                    ? lang.getBanLang().getPerm().getCause()
                    : lang.getBanLang().getTemp().getCause();
            Map<String, String> placeholders = new HashMap<>() {{
                put("operator", ban.getOperator().getName());
                put("reason", ban.getReason() == null ? lang.getReasonNotIndicated() : ban.getReason());
                if (ban.getDuration() != -1) {
                    put("time", punishmentService.formatPunishmentDuration(ban, settings.getDurationFormat()));
                    put("time", punishmentService.formatPunishmentExpires(ban, settings.getDateFormat()));
                }
            }};
            entityService.disconnect(player, placeholderService.applyPlaceholders(player, cause, placeholders));
        }
    }


}
