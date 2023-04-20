package com.lairon.xpc.service.impl;

import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.Punishable;
import com.lairon.xpc.model.Punishment;
import com.lairon.xpc.service.PunishmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultPunishmentService implements PunishmentService {

    private final DataProvider dataProvider;

    @Override
    public boolean canUseChat(@NonNull Player player) {
        Punishment punishment = player.getMute();
        if(punishment == null) return true;
        if(punishment.getDuration() < System.currentTimeMillis()){
            //Save to history
            player.setMute(null);
            dataProvider.save(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean canJoin(@NonNull Player player) {
        Punishment punishment = player.getBan();
        if(punishment == null) return true;
        if(punishment.getDuration() < System.currentTimeMillis()){
            //Save to history
            player.setBan(null);
            dataProvider.save(player);
            return true;
        }
        return false;
    }
}
