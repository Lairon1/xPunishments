package com.lairon.plugins.xpunishmentsvelocity.service;

import com.google.inject.Inject;
import com.lairon.plugins.xpunishmentsvelocity.model.ConsoleEntity;
import com.lairon.xpc.model.NamedEntity;
import com.lairon.xpc.service.PlayerService;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class VelocityPlayerService implements PlayerService {

    @Inject
    private ProxyServer server;

    @Override
    public void sendMessage(@NonNull NamedEntity player, @NonNull String message) {
        Component finalMessage = MiniMessage.miniMessage().deserialize(legacyAmpersandDeserialize(message));
        if (player == ConsoleEntity.getInstance()) {
            server.getConsoleCommandSource().sendMessage(finalMessage);
            return;
        }
        server.getPlayer(player.getUuid()).map(vPlayer -> {
            vPlayer.sendMessage(finalMessage);
            return vPlayer;
        });
    }

    @Override
    public boolean hasPermission(@NonNull NamedEntity player, @NonNull String permission) {
        if (player == ConsoleEntity.getInstance())
            return server.getConsoleCommandSource().hasPermission(permission);

        Player vPlayer = server.getPlayer(player.getUuid()).orElse(null);
        if (vPlayer == null) return false;

        return vPlayer.hasPermission(permission);
    }

    @Override
    public void disconnect(@NonNull NamedEntity player, @NonNull String reason) {
        server.getPlayer(player.getUuid()).map(vPlayer -> {
            Component finalReason = MiniMessage.miniMessage().deserialize(legacyAmpersandDeserialize(reason));
            vPlayer.disconnect(finalReason);
            return vPlayer;
        });
    }

    @Override
    public void announce(@NonNull String message) {
        Component finalMessage = MiniMessage.miniMessage().deserialize(legacyAmpersandDeserialize(message));
        server.getAllPlayers().forEach(player -> player.sendMessage(finalMessage));
    }

    private String legacyAmpersandDeserialize(@NonNull String input) {
        char[] chars = input.toCharArray();
        char[] result = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (
                    current == '&'
                    && chars.length > i + 1
                    && "123456789abcdefklmnor".indexOf(chars[i + 1]) > -1
            ) {
                current = '§';
            }
            result[i] = current;
        }
        return new String(result);
    }

}
