package com.lairon.plugins.xpunishmentsvelocity.service;

import com.lairon.plugins.xpunishmentsvelocity.model.ConsoleEntity;
import com.lairon.xpc.model.NamedEntity;
import com.lairon.xpc.service.PlayerService;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.connection.Player;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

@RequiredArgsConstructor
public class VelocityPlayerService implements PlayerService {

    private final ProxyServer server;
    
    @Override
    public void sendMessage(@NonNull NamedEntity player, @NonNull String message) {
        Component finalMessage = MiniMessage.miniMessage().deserialize(legacyAmpersandDeserialize(message));
        if (player == ConsoleEntity.getInstance()) {
            server.consoleCommandSource().sendMessage(finalMessage);
            return;
        }
        Player vPlayer = server.player(player.getUuid());
        if (vPlayer == null) return;
        vPlayer.sendMessage(finalMessage);
    }

    @Override
    public boolean hasPermission(@NonNull NamedEntity player, @NonNull String permission) {
        if (player == ConsoleEntity.getInstance())
            return server.consoleCommandSource().hasPermission(permission);

        Player vPlayer = server.player(player.getUuid());
        if (vPlayer == null) return false;

        return vPlayer.hasPermission(permission);
    }

    @Override
    public void disconnect(@NonNull NamedEntity player, @NonNull String reason) {
        Player vPlayer = server.player(player.getUuid());
        if (vPlayer == null) return;
        Component finalReason = MiniMessage.miniMessage().deserialize(legacyAmpersandDeserialize(reason));
        vPlayer.disconnect(finalReason);
    }

    @Override
    public void announce(@NonNull String message) {
        Component finalMessage = MiniMessage.miniMessage().deserialize(legacyAmpersandDeserialize(message));
        server.connectedPlayers().forEach(player -> player.sendMessage(finalMessage));
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
