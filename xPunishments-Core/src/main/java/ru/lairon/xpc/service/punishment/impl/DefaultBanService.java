package ru.lairon.xpc.service.punishment.impl;

import ru.lairon.xpc.config.lang.LangConfig;
import ru.lairon.xpc.data.DataProvider;
import ru.lairon.xpc.model.Punishment;
import ru.lairon.xpc.model.PunishmentHistoryNode;
import ru.lairon.xpc.model.PunishmentHistoryNodeType;
import ru.lairon.xpc.model.User;
import ru.lairon.xpc.service.EntityService;
import ru.lairon.xpc.service.PlaceholderParserService;
import ru.lairon.xpc.service.punishment.BanService;
import ru.lairon.xpc.service.punishment.exeption.impl.AlreadyPunishedException;
import ru.lairon.xpc.service.punishment.exeption.impl.NotPunishedYetException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.lairon.service.namedentity.NamedEntity;
import ru.lairon.service.placeholder.PlaceholderService;

@RequiredArgsConstructor
public class DefaultBanService implements BanService {

    private final PlaceholderService placeholderService;
    private final PlaceholderParserService placeholderParserService;
    private final EntityService entityService;
    private final DataProvider dataProvider;
    private final LangConfig lang;

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
                placeholderParserService.toPlaceholdersPunishment(user, ban)));

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
                placeholderParserService.toPlaceholdersPunishment(user, ban))
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

    @Override
    public boolean can(@NonNull User user) {
        Punishment punishment = user.getBan();
        if (punishment == null) return true;
        if (punishment.getDuration() < System.currentTimeMillis()) {
            dataProvider.addHistoryNode(new PunishmentHistoryNode(user, user.getMute(), PunishmentHistoryNodeType.BAN));
            user.setBan(null);
            dataProvider.save(user);
            return true;
        }
        return false;
    }
}
