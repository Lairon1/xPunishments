package ru.lairon.plugins.xpunishmentsvelocity.adapter;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.NonNull;
import ru.lairon.plugins.xpunishmentsvelocity.model.ConsoleEntity;
import ru.lairon.service.namedentity.NamedEntity;
import ru.lairon.service.namedentity.impl.DefaultNamedEntity;

public class VelocityAdapter {


    public static NamedEntity adaptNamedEntity(@NonNull CommandSource commandSource) {
        if (commandSource instanceof ConsoleCommandSource)
            return ConsoleEntity.getInstance();
        if (commandSource instanceof Player player)
            return new DefaultNamedEntity(player.getUniqueId(), player.getUsername());
        return null;
    }

}
