package com.lairon.plugins.xpunishmentsvelocity.listener;

import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.handler.JoinHandler;
import com.lairon.xpc.model.Player;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerConnectEvent {

    private final DataProvider dataProvider;
    private final JoinHandler joinHandler;

    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        Player player = dataProvider.findByUUID(event.getPlayer().getUniqueId()).get();
        if(player == null){
            player = new Player(event.getPlayer().getUniqueId(), event.getPlayer().getUsername());
            dataProvider.save(player);
        }
        joinHandler.onJoin(player);
    }
}
