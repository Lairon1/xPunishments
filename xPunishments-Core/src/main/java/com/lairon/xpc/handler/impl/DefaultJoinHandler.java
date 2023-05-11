package com.lairon.xpc.handler.impl;

import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.handler.JoinHandler;
import com.lairon.xpc.model.User;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.service.EntityService;
import com.lairon.xpc.service.PlaceholderParserService;
import com.lairon.xpc.service.punishment.BanService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.lairon.service.placeholder.PlaceholderService;

import java.util.Map;

@RequiredArgsConstructor
public class DefaultJoinHandler implements JoinHandler {

    private final BanService banService;
    private final EntityService entityService;
    private final PlaceholderService placeholderService;
    private final PlaceholderParserService placeholderParserService;
    private final LangConfig lang;

    @Override
    public void onJoin(@NonNull User user) {
        if (!banService.can(user)) {
            Punishment ban = user.getBan();
            String cause = ban.getDuration() == -1
                    ? lang.getBanLang().getPermanent().getCause()
                    : lang.getBanLang().getTemporary().getCause();

            Map<String, Object> placeholders = placeholderParserService.toPlaceholdersPunishment(user, ban);

            entityService.disconnect(user, placeholderService.applyPlaceholders(user, cause, placeholders));
        }
    }


}
