package com.lairon.xpc.handler.punishment.impl;

import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.handler.punishment.BanHandler;
import com.lairon.xpc.model.User;
import com.lairon.xpc.permission.Permission;
import com.lairon.xpc.service.EntityService;
import com.lairon.xpc.service.PlaceholderParserService;
import com.lairon.xpc.service.punishment.BanService;
import com.lairon.xpc.service.punishment.exeption.ExecutionPunishmentException;
import com.lairon.xpc.service.punishment.exeption.impl.AlreadyPunishedException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.lairon.service.message.MessageService;
import ru.lairon.service.namedentity.NamedEntity;
import ru.lairon.service.placeholder.PlaceholderService;

import java.util.Map;

@RequiredArgsConstructor
public class DefaultBanHandler implements BanHandler {

    private final BanService banService;
    private final EntityService entityService;
    private final MessageService messageService;
    private final PlaceholderParserService placeholderParserService;
    private final PlaceholderService placeholderService;
    private final LangConfig lang;

    @Override
    public void permanent(@NonNull NamedEntity executor, @NonNull User user, String reason, boolean silent) {
        if (!entityService.hasPermission(executor, Permission.USE_BAN_PERMANENT)) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getDontHavePermissions(),
                    "permission", Permission.USE_BAN_PERMANENT.name()
            ));
            return;
        }
        if (executor.equals(user)) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getCantYourself()));
            return;
        }
        if (silent && !entityService.hasPermission(executor, Permission.USE_BAN_PERMANENT_SILENT)) {
            silent = false;
        }
        try {
            banService.permanent(executor, user, reason);
            Map<String, Object> placeholders = placeholderParserService.toPlaceholdersPunishment(user, user.getBan());
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getBanLang()
                    .getPermanent()
                    .getMessage(), placeholders));
            if (!silent) {
                messageService.announceChat(placeholderService.applyPlaceholders(executor, lang.getBanLang()
                        .getPermanent()
                        .getAnnounce(), placeholders));
            }
        } catch (AlreadyPunishedException e) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(
                    executor,
                    lang.getBanLang().getAlready(),
                    placeholderParserService.toPlaceholdersEntity(user)
            ));
        } catch (ExecutionPunishmentException e) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getUnknownError(),
                    "errorName", e.getClass().getSimpleName()
            ));
        }
    }

    @Override
    public void temporary(@NonNull NamedEntity executor, @NonNull User user, long duration, String reason, boolean silent) {
        if (!entityService.hasPermission(executor, Permission.USE_BAN_TEMPORARY)) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getDontHavePermissions(),
                    "permission", Permission.USE_BAN_TEMPORARY.name()
            ));
            return;
        }
        if (executor.equals(user)) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getCantYourself()));
            return;
        }
        if (silent && !entityService.hasPermission(executor, Permission.USE_BAN_TEMPORARY_SILENT)) {
            silent = false;
        }

        try {
            banService.temporarily(executor, user, reason, duration);
            Map<String, Object> placeholders = placeholderParserService.toPlaceholdersPunishment(user, user.getBan());

            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor,
                    lang.getBanLang()
                            .getTemporary()
                            .getMessage(),
                    placeholders));
            if (!silent) {
                messageService.announceChat(placeholderService.applyPlaceholders(executor,
                        lang.getBanLang()
                                .getTemporary()
                                .getAnnounce(),
                        placeholders));
            }
        } catch (AlreadyPunishedException e) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(
                    executor,
                    lang.getBanLang().getAlready(),
                    placeholderParserService.toPlaceholdersEntity(user)
            ));
        } catch (ExecutionPunishmentException e) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getUnknownError(),
                    "errorName", e.getClass().getSimpleName()
            ));
        }

    }

    @Override
    public void pardon(@NonNull NamedEntity executor, @NonNull User user, boolean silent) {

    }


}
