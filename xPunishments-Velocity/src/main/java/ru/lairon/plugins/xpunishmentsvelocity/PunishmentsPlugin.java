package ru.lairon.plugins.xpunishmentsvelocity;

import lombok.Getter;
import ru.lairon.plugins.xpunishmentsvelocity.command.BanCommand;
import ru.lairon.plugins.xpunishmentsvelocity.listener.PlayerConnectListener;
import ru.lairon.xpc.data.DataProvider;
import ru.lairon.xpc.handler.JoinHandler;
import ru.lairon.xpc.handler.punishment.BanHandler;

import java.io.File;

import org.slf4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(
        id = "xpunishments",
        name = "xPunishments",
        version = "1.0-SNAPSHOT"
)
@Getter
public class PunishmentsPlugin {

    @Inject
    private Logger logger;
    @Inject
    private ProxyServer server;

    private File dataDir = new File("plugins" + File.separator + "xPunishments" + File.separator);

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean("proxyServer", ProxyServer.class, () -> server);
        context.registerBean("dataDir", File.class, () -> dataDir);
        context.register(AbstractVelocityConfiguration.class);
        context.refresh();

        BanCommand banCommand = new BanCommand(context.getBean("banHandler", BanHandler.class),
                context.getBean("dataProvider", DataProvider.class));
        server.getCommandManager().register(server.getCommandManager().metaBuilder("ban").plugin(this).build(), banCommand);

        PlayerConnectListener playerConnectListener = new PlayerConnectListener(context.getBean("dataProvider", DataProvider.class),
                context.getBean("joinHandler", JoinHandler.class));
        server.getEventManager().register(this, playerConnectListener);
    }


}
