package com.lairon.xpc.service.ep.impl;

import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.config.settings.SettingsConfig;
import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.model.User;
import com.lairon.xpc.service.EntityService;
import com.lairon.xpc.service.PunishmentService;
import com.lairon.xpc.service.ep.BanService;
import com.lairon.xpc.service.ep.exeption.impl.AlreadyPunishedException;
import com.lairon.xpc.service.ep.exeption.impl.NotPunishedYetException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.lairon.service.namedentity.NamedEntity;
import ru.lairon.service.placeholder.PlaceholderService;

@RequiredArgsConstructor
public class DefaultBanService implements BanService {

    private final PunishmentService punishmentService;
    private final PlaceholderService placeholderService;
    private final EntityService entityService;
    private final DataProvider dataProvider;
    private final LangConfig lang;
    private final SettingsConfig settings;

    @Override
    public void temporarily(@NonNull NamedEntity operator, @NonNull User user, String reason, long duration)
            throws AlreadyPunishedException {

        if (user.getBan() != null)
            throw new AlreadyPunishedException(user, user.getBan());

        Punishment ban = new Punishment(operator, duration, System.currentTimeMillis() + duration);
        ban.setReason(reason);
        user.setBan(ban);

        entityService.disconnect(user, placeholderService.applyPlaceholders(user, lang
                        .getBanLang()
                        .getTemporary()
                        .getCause(),
                "{operator}", operator.getName(),
                reason == null ? lang.getReasonNotIndicated() : reason,
                "duration", punishmentService.formatPunishmentDuration(ban, settings.getDurationFormat()),
                "issued", punishmentService.formatPunishmentIssued(ban, settings.getDateFormat())));

        dataProvider.save(user);
    }

    @Override
    public void permanent(@NonNull NamedEntity operator, @NonNull User user, String reason)
            throws AlreadyPunishedException {
        if (user.getBan() != null)
            throw new AlreadyPunishedException(user, user.getBan());

        Punishment ban = new Punishment(operator, -1, -1);
        ban.setReason(reason);

        user.setBan(ban);

        entityService.disconnect(user, placeholderService.applyPlaceholders(user, lang
                        .getBanLang()
                        .getPermanent()
                        .getCause(),
                "{operator}", operator.getName(),
                "reason", reason == null ? lang.getReasonNotIndicated() : reason)
        );

        dataProvider.save(user);
    }

    @Override
    public void pardon(@NonNull NamedEntity operator, @NonNull User user)
            throws NotPunishedYetException {
        if(user.getBan() == null)
            throw new NotPunishedYetException(user);
        user.setBan(null);
        dataProvider.save(user);
    }
}
