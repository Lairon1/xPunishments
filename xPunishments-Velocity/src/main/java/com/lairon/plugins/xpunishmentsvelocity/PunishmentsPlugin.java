package com.lairon.plugins.xpunishmentsvelocity;

import com.google.inject.Inject;
import com.lairon.plugins.xpunishmentsvelocity.configmodule.VelocityYamlConfigModule;
import com.lairon.plugins.xpunishmentsvelocity.service.VelocityEntityService;
import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.config.settings.SettingsConfig;
import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.data.sql.sqlite.SQLiteDataProvider;
import com.lairon.xpc.handler.JoinHandler;
import com.lairon.xpc.handler.impl.DefaultJoinHandler;
import com.lairon.xpc.handler.punishment.BanHandler;
import com.lairon.xpc.handler.punishment.impl.DefaultBanHandler;
import com.lairon.xpc.placeholder.DefaultPlaceholder;
import com.lairon.xpc.service.EntityService;
import com.lairon.xpc.service.PlaceholderParserService;
import com.lairon.xpc.service.impl.DefaultPlaceholderParserService;
import com.lairon.xpc.service.punishment.BanService;
import com.lairon.xpc.service.punishment.impl.DefaultBanService;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.slf4j.Logger;
import ru.lairon.service.message.MessageService;
import ru.lairon.service.message.impl.VelocityMessageService;
import ru.lairon.service.placeholder.PlaceholderService;
import ru.lairon.service.placeholder.impl.DefaultPlaceholderService;

import java.io.File;

@Plugin(
        id = "xpunishments",
        name = "xPunishments",
        version = "1.0-SNAPSHOT"
)
@Getter
public class PunishmentsPlugin {

    /**
     * Injects
     */
    @Inject
    private Logger logger;
    @Inject
    private ProxyServer server;

    private File dataDir = new File("plugins" + File.separator + "xPunishments" + File.separator);

    /**
     * Configs
     */
    private SettingsConfig settings;
    private LangConfig lang;

    /**
     * Data
     */
    private DataProvider dataProvider;

    /**
     * Services
     */
    private MessageService messageService;
    private PlaceholderService placeholderService;
    private BanService banService;
    private EntityService entityService;
    private PlaceholderParserService placeholderParserService;

    /**Handlers*/
    private BanHandler banHandler;
    private JoinHandler joinHandler;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

        /**Configs*/
        settings = new SettingsConfig(dataDir + File.separator + "settings.yml", VelocityYamlConfigModule.class);
        lang = new LangConfig(dataDir + File.separator + "lang.yml", VelocityYamlConfigModule.class);
        settings.reload();
        lang.reload();

        /**Data*/
        dataProvider = new SQLiteDataProvider(dataDir + File.separator + "data.db");

        /**services*/
        messageService = new VelocityMessageService(server);
        placeholderService = new DefaultPlaceholderService('{', '}');
        placeholderService.registerPlaceholder(new DefaultPlaceholder(lang));
        placeholderParserService = new DefaultPlaceholderParserService(lang, settings);
        entityService = new VelocityEntityService(server);

        banService = new DefaultBanService(
                placeholderService,
                placeholderParserService,
                entityService,
                dataProvider,
                lang
        );

        /**Handlers*/
        joinHandler = new DefaultJoinHandler(
                banService,
                entityService,
                placeholderService,
                placeholderParserService,
                lang
        );
        banHandler = new DefaultBanHandler(
                banService,
                entityService,
                messageService,
                placeholderParserService,
                placeholderService,
                lang
        );

    }


}
