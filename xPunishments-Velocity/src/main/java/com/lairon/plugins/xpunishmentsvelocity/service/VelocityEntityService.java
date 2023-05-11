package com.lairon.plugins.xpunishmentsvelocity.service;

import com.lairon.plugins.xpunishmentsvelocity.model.ConsoleEntity;
import com.lairon.xpc.permission.Permission;
import com.lairon.xpc.service.EntityService;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import ru.lairon.service.namedentity.NamedEntity;

import java.util.Objects;

@RequiredArgsConstructor
public class VelocityEntityService implements EntityService {

    private final ProxyServer server;

    @Override
    public boolean hasPermission(@NonNull NamedEntity player, @NonNull Permission permission) {
        String stringPermission = "xpn." + permission
                .name()
                .replace("_", ".")
                .toLowerCase();

        if (player == ConsoleEntity.getInstance())
            return server.getConsoleCommandSource().hasPermission(stringPermission);

        Player vPlayer = server.getPlayer(player.getUUID()).orElse(null);
        if (vPlayer == null) return false;

        return vPlayer.hasPermission(stringPermission);
    }

    @Override
    public void disconnect(@NonNull NamedEntity player, @NonNull String reason) {
        server.getPlayer(player.getUUID()).map(vPlayer -> {
            Component finalReason = MiniMessage.miniMessage().deserialize(legacyAmpersandDeserialize(reason));
            vPlayer.disconnect(finalReason);
            return vPlayer;
        });
    }

    private String legacyAmpersandDeserialize(@NonNull String input) {
        Objects.requireNonNull(input, "message can not be null");
        char[] messageArr = input.toCharArray();

        for (int i = 0; i < messageArr.length; ++i) {
            if (messageArr[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(messageArr[i + 1]) != -1) {
                messageArr[i] = 167;
                messageArr[i + 1] = Character.toLowerCase(messageArr[i + 1]);
            }
        }

        return new String(messageArr);
    }

}
