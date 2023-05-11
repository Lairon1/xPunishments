package ru.lairon.plugins.xpunishmentsvelocity.listener;

import ru.lairon.xpc.data.DataProvider;
import ru.lairon.xpc.handler.JoinHandler;
import ru.lairon.xpc.model.User;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerConnectEvent {

    private final DataProvider dataProvider;
    private final JoinHandler joinHandler;

    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        User user = dataProvider.findByUUID(event.getPlayer().getUniqueId()).get();
        if(user == null){
            user = new User(event.getPlayer().getUniqueId(), event.getPlayer().getUsername());
            dataProvider.save(user);
        }
        joinHandler.onJoin(user);
    }
}
