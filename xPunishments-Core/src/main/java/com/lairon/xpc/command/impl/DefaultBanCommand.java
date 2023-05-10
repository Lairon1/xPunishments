package com.lairon.xpc.command.impl;

import com.lairon.xpc.command.BanCommand;
import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.model.Player;
import com.lairon.xpc.permission.BanPermissions;
import com.lairon.xpc.service.EntityService;
import com.lairon.xpc.service.ep.BanService;
import com.lairon.xpc.service.ep.exeption.ExecutionPunishmentException;
import com.lairon.xpc.service.ep.exeption.impl.AlreadyPunished;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.lairon.service.message.MessageService;
import ru.lairon.service.namedentity.NamedEntity;
import ru.lairon.service.placeholder.PlaceholderService;

import java.util.List;

@RequiredArgsConstructor
public class DefaultBanCommand implements BanCommand {

    private final DataProvider dataProvider;
    private final MessageService messageService;
    private final PlaceholderService placeholderService;
    private final EntityService entityService;
    private final BanService banService;
    private final LangConfig lang;
    @Override
    public void execute(@NonNull NamedEntity executor, @NonNull List<String> args) {
        if(!entityService.hasPermission(executor, BanPermissions.COMMAND_USE)){
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getDontHavePermissions(),
                    "permission", BanPermissions.COMMAND_USE));
            return;
        }
        if(args.size() < 1){
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang
                    .getBanLang().getPerm().getUsage()));
            return;
        }
        Player player = dataProvider.findByName(args.get(1)).orElse(null);
        if(player == null){
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getPlayerWasNotFound(),
                    "player", args.get(1)));
            return;
        }
        StringBuilder reason = new StringBuilder();
        if(args.size() > 1){
            for (int i = 1; i < args.size(); i++) {
                reason.append(args.get(i) + " ");
            }
        }
        try {
            banService.permanent(executor, player, reason.length() != 0 ? reason.toString() : null, false);
        } catch (ExecutionPunishmentException e) {
            messageService.sendChat(executor, placeholderService.applyPlaceholders(executor, lang.getBanLang()
                    .getAlready(), "player", player.getName()));
        }
    }

}
