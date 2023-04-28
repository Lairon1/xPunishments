package com.lairon.plugins.xpunishmentsvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "xpunishmentsvelocity",
        name = "xPunishments-Velocity",
        version = "1.0-SNAPSHOT"
)
public class PunishmentsPlugin {

    @Inject
    private Logger logger;
    @Inject
    private ProxyServer server;
    @Inject
    @DataDirectory
    private Path dataDir;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

    }


}
