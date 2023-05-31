package ru.lairon.xpc;

import ru.lairon.service.message.MessageService;
import ru.lairon.service.placeholder.PlaceholderService;
import ru.lairon.service.placeholder.impl.DefaultPlaceholderService;
import ru.lairon.xpc.config.lang.LangConfig;
import ru.lairon.xpc.config.settings.SettingsConfig;
import ru.lairon.xpc.data.DataProvider;
import ru.lairon.xpc.handler.JoinHandler;
import ru.lairon.xpc.handler.impl.DefaultJoinHandler;
import ru.lairon.xpc.handler.punishment.BanHandler;
import ru.lairon.xpc.handler.punishment.impl.DefaultBanHandler;
import ru.lairon.xpc.service.EntityService;
import ru.lairon.xpc.service.PlaceholderParserService;
import ru.lairon.xpc.service.impl.DefaultPlaceholderParserService;
import ru.lairon.xpc.service.punishment.BanService;
import ru.lairon.xpc.service.punishment.impl.DefaultBanService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class AbstractConfiguration {

    @Bean
    public JoinHandler joinHandler(BanService banService, EntityService entityService, PlaceholderService placeholderService,
            PlaceholderParserService placeholderParserService, LangConfig langConfig) {
        return new DefaultJoinHandler(banService, entityService, placeholderService, placeholderParserService, langConfig);
    }

    @Bean
    public BanHandler banHandler(BanService banService, EntityService entityService, MessageService messageService,
            PlaceholderParserService placeholderParserService, PlaceholderService placeholderService, LangConfig langConfig) {
        return new DefaultBanHandler(banService, entityService, messageService, placeholderParserService, placeholderService, langConfig);
    }

    @Bean
    public BanService banService(PlaceholderService placeholderService, PlaceholderParserService placeholderParserService,
            EntityService entityService, DataProvider dataProvider, LangConfig langConfig) {
        return new DefaultBanService(placeholderService, placeholderParserService, entityService, dataProvider, langConfig);
    }

    @Bean
    public abstract LangConfig langConfig();

    @Bean
    public abstract SettingsConfig settingsConfig();

    @Bean
    public abstract DataProvider dataProvider();

    @Bean
    public abstract EntityService entityService();

    @Bean
    public PlaceholderService placeholderService() {
        return new DefaultPlaceholderService('{', '}');
    }

    @Bean
    public abstract MessageService messageService();

    @Bean
    public PlaceholderParserService placeholderParserService(LangConfig langConfig, SettingsConfig settingsConfig) {
        return new DefaultPlaceholderParserService(langConfig, settingsConfig);
    }

}
