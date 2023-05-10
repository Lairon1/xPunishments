package com.lairon.plugins.xpunishmentsvelocity;

import com.google.inject.Inject;
import com.lairon.plugins.xpunishmentsvelocity.commands.VelocityCommandInterface;
import com.lairon.plugins.xpunishmentsvelocity.configmodule.VelocityYamlConfigModule;
import com.lairon.plugins.xpunishmentsvelocity.listener.PlayerConnectEvent;
import com.lairon.plugins.xpunishmentsvelocity.service.VelocityEntityService;
import com.lairon.xpc.command.BanCommand;
import com.lairon.xpc.command.impl.DefaultBanCommand;
import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.config.settings.SettingsConfig;
import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.data.sql.sqlite.SQLiteDataProvider;
import com.lairon.xpc.handler.JoinHandler;
import com.lairon.xpc.handler.impl.DefaultJoinHandler;
import com.lairon.xpc.placeholder.DefaultPlaceholder;
import com.lairon.xpc.service.EntityService;
import com.lairon.xpc.service.PunishmentService;
import com.lairon.xpc.service.ep.BanService;
import com.lairon.xpc.service.ep.impl.DefaultBanService;
import com.lairon.xpc.service.impl.DefaultPunishmentService;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import ru.lairon.service.message.MessageService;
import ru.lairon.service.message.impl.VelocityMessageService;
import ru.lairon.service.placeholder.PlaceholderService;
import ru.lairon.service.placeholder.impl.DefaultPlaceholderService;

import java.io.File;
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

    private DataProvider dataProvider;
    private MessageService messageService;
    private LangConfig lang;
    private SettingsConfig settings;
    private PlaceholderService placeholderService;
    private EntityService entityService;
    private BanService banService;
    private PunishmentService punishmentService;

    private BanCommand banCommand;
    private JoinHandler joinHandler;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        long startInit = System.currentTimeMillis(),
                startLoadingConfigs = startInit, startPluginInit;

        logger.info("Loading Configuration Files...");
        settings = new SettingsConfig(dataDir + File.separator + "settings.yml", VelocityYamlConfigModule.class);
        lang = new LangConfig(dataDir + File.separator + "lang.yml", VelocityYamlConfigModule.class);
        settings.reload();
        lang.reload();

        logger.info("Loading configuration files was successful for " + (System.currentTimeMillis() - startLoadingConfigs) + "ms");
        logger.info("Plugin initialization");
        startPluginInit = System.currentTimeMillis();

        dataProvider = new SQLiteDataProvider(dataDir + File.separator + "data.db");
        messageService = new VelocityMessageService(server);
        placeholderService = new DefaultPlaceholderService('{', '}');
        placeholderService.registerPlaceholder(new DefaultPlaceholder(lang));
        banService = new DefaultBanService(
                messageService,
                placeholderService,
                entityService,
                dataProvider,
                lang
        );
        entityService = new VelocityEntityService(server);
        punishmentService = new DefaultPunishmentService(dataProvider);

        banCommand = new DefaultBanCommand(
                dataProvider,
                messageService,
                placeholderService,
                entityService,
                banService,
                lang
        );
        joinHandler = new DefaultJoinHandler(
                punishmentService,
                entityService,
                placeholderService,
                lang,
                settings
        );

        CommandManager commandManager = server.getCommandManager();
        commandManager.register(
                commandManager.metaBuilder("ban").plugin(this).build(),
                new VelocityCommandInterface(banCommand, dataProvider));

        server.getEventManager().register(this, new PlayerConnectEvent(dataProvider, joinHandler));


        logger.info("Plugin initialization completed successfully in " + (System.currentTimeMillis() - startPluginInit) + "ms");
        logger.info("Plugin launched for " + (System.currentTimeMillis() - startInit) + "ms");

    }


}
