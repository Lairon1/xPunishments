package com.lairon.xpc.service.impl;

import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.model.PunishmentHistoryNode;
import com.lairon.xpc.model.PunishmentHistoryNodeType;
import com.lairon.xpc.service.PunishmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class DefaultPunishmentService implements PunishmentService {

    private final DataProvider dataProvider;

    @Override
    public boolean canUseChat(@NonNull Player player) {
        Punishment punishment = player.getMute();
        if (punishment == null) return true;
        if (punishment.getDuration() < System.currentTimeMillis()) {
            dataProvider.addHistoryNode(new PunishmentHistoryNode(player, player.getMute(), PunishmentHistoryNodeType.MUTE));
            player.setMute(null);
            dataProvider.save(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean canJoin(@NonNull Player player) {
        Punishment punishment = player.getBan();
        if (punishment == null) return true;
        if (punishment.getDuration() < System.currentTimeMillis()) {
            dataProvider.addHistoryNode(new PunishmentHistoryNode(player, player.getMute(), PunishmentHistoryNodeType.BAN));
            player.setBan(null);
            dataProvider.save(player);
            return true;
        }
        return false;
    }

    @Override
    public String formatPunishmentDuration(@NonNull Punishment punishment, @NonNull String format) {
        return punishment.getDuration() + "";
    }

    @Override
    public String formatPunishmentExpires(@NonNull Punishment punishment, @NonNull String format) {
        return new SimpleDateFormat(format).format(new Date(punishment.getIssued() + punishment.getDuration()));
    }
}
