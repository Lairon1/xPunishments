package com.lairon.xpc.handler.impl;

import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.config.settings.SettingsConfig;
import com.lairon.xpc.handler.JoinHandler;
import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.service.PlaceholderService;
import com.lairon.xpc.service.PlayerService;
import com.lairon.xpc.service.PunishmentService;
import lombok.NonNull;

import com.google.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class DefaultJoinHandler implements JoinHandler {

    @Inject
    private PunishmentService punishmentService;
    @Inject
    private PlayerService playerService;
    @Inject
    private PlaceholderService placeholderService;
    @Inject
    private LangConfig lang;
    @Inject
    private SettingsConfig settings;

    @Override
    public void onJoin(@NonNull Player player) {
        if (!punishmentService.canJoin(player)) {
            Punishment ban = player.getBan();
            String cause = ban.getDuration() == -1
                    ? lang.getBanLang().getPerm().getCause()
                    : lang.getBanLang().getTemp().getCause();
            Map<String, String> placeholders = new HashMap<>() {{
                put("operator", ban.getOperator().getName());
                put("reason", ban.getReason());
                if (ban.getDuration() != -1) {
                    put("time", punishmentService.formatPunishmentDuration(ban, settings.getDurationFormat()));
                    put("time", punishmentService.formatPunishmentExpires(ban, settings.getDateFormat()));
                }
            }};
            playerService.disconnect(player, placeholderService.setPlaceholders(player, cause, placeholders));
        }
    }


}
