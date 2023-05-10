package com.lairon.plugins.xpunishmentsvelocity.commands;

import com.lairon.plugins.xpunishmentsvelocity.model.ConsoleEntity;
import com.lairon.xpc.command.Command;
import com.lairon.xpc.data.DataProvider;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.Arrays;

@RequiredArgsConstructor
public class VelocityCommandInterface implements SimpleCommand {

    private final Command command;
    private final DataProvider dataProvider;


    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

        if (source instanceof Player player) {
            com.lairon.xpc.model.Player corePlayer = dataProvider
                    .findByUUID(player.getUniqueId()).get();
            if (corePlayer == null) {
                corePlayer = new com.lairon.xpc.model.Player(player.getUniqueId(), player.getUsername());
                dataProvider.save(corePlayer);
            }
            command.execute(corePlayer, Arrays.asList(invocation.arguments()));
            return;
        } else if (source instanceof ConsoleCommandSource) {
            command.execute(ConsoleEntity.getInstance(), Arrays.asList(invocation.arguments()));
            return;
        }
        source.sendMessage(Component.text("Who are you??!"));
        return;
    }



}
