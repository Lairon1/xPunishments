package ru.lairon.plugins.xpunishmentsvelocity;

import com.google.inject.Inject;
import ru.lairon.plugins.xpunishmentsvelocity.configmodule.VelocityYamlConfigModule;
import ru.lairon.plugins.xpunishmentsvelocity.service.VelocityEntityService;
import ru.lairon.xpc.config.lang.LangConfig;
import ru.lairon.xpc.config.settings.SettingsConfig;
import ru.lairon.xpc.data.DataProvider;
import ru.lairon.xpc.data.sql.sqlite.SQLiteDataProvider;
import ru.lairon.xpc.handler.JoinHandler;
import ru.lairon.xpc.handler.impl.DefaultJoinHandler;
import ru.lairon.xpc.handler.punishment.BanHandler;
import ru.lairon.xpc.handler.punishment.impl.DefaultBanHandler;
import ru.lairon.xpc.placeholder.DefaultPlaceholder;
import ru.lairon.xpc.service.EntityService;
import ru.lairon.xpc.service.PlaceholderParserService;
import ru.lairon.xpc.service.impl.DefaultPlaceholderParserService;
import ru.lairon.xpc.service.punishment.BanService;
import ru.lairon.xpc.service.punishment.impl.DefaultBanService;
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
