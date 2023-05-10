package com.lairon.xpc.service.ep.impl;

import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.service.EntityService;
import com.lairon.xpc.service.ep.BanService;
import com.lairon.xpc.service.ep.exeption.impl.AlreadyPunished;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.lairon.service.message.MessageService;
import ru.lairon.service.namedentity.NamedEntity;
import ru.lairon.service.placeholder.PlaceholderService;

@RequiredArgsConstructor
public class DefaultBanService implements BanService {

    private final MessageService messageService;
    private final PlaceholderService placeholderService;
    private final EntityService entityService;
    private final DataProvider dataProvider;
    private final LangConfig lang;

    @Override
    public void temporarily(@NonNull NamedEntity operator, @NonNull Player player, String reason, long duration, boolean silent) {

    }

    @Override
    public void permanent(@NonNull NamedEntity operator, @NonNull Player player, String reason, boolean silent)
            throws AlreadyPunished {

        Punishment ban = new Punishment(operator, -1, -1);
        ban.setReason(reason);
        if (player.getBan() != null)
            throw new AlreadyPunished(player, ban);

        player.setBan(ban);

        entityService.disconnect(player, placeholderService.applyPlaceholders(player, lang.getBanLang().getPerm().getCause(),
                "{operator}", operator.getName(), reason == null ? lang.getReasonNotIndicated() : reason));

        if (!silent) {
            messageService.announceChat(placeholderService.applyPlaceholders(operator, lang.getBanLang().getPerm().getAnnounce(),
                    "operator", operator.getName(),
                    "player", player.getName(),
                    "reason", reason == null ? lang.getReasonNotIndicated() : reason));
        }

        dataProvider.save(player);
    }

    @Override
    public void pardon(@NonNull NamedEntity operator, @NonNull Player player, boolean silent) {

    }
}
