package com.lairon.xpc.service.impl;

import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.model.User;
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
    public boolean canUseChat(@NonNull User user) {
        Punishment punishment = user.getMute();
        if (punishment == null) return true;
        if (punishment.getDuration() < System.currentTimeMillis()) {
            dataProvider.addHistoryNode(new PunishmentHistoryNode(user, user.getMute(), PunishmentHistoryNodeType.MUTE));
            user.setMute(null);
            dataProvider.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean canJoin(@NonNull User user) {
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

    @Override
    public String formatPunishmentDuration(@NonNull Punishment punishment, @NonNull String format) {
        return punishment.getDuration() + "";
    }

    @Override
    public String formatPunishmentIssued(@NonNull Punishment punishment, @NonNull String format) {
        return new SimpleDateFormat(format).format(new Date(punishment.getIssued() + punishment.getDuration()));
    }
}
