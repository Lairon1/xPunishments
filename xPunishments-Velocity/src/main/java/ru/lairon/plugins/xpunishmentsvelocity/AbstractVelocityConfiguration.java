package ru.lairon.plugins.xpunishmentsvelocity;

import ru.lairon.plugins.xpunishmentsvelocity.configmodule.VelocityYamlConfigModule;
import ru.lairon.plugins.xpunishmentsvelocity.service.VelocityEntityService;
import ru.lairon.service.message.MessageService;
import ru.lairon.service.message.impl.VelocityMessageService;
import ru.lairon.xpc.AbstractConfiguration;
import ru.lairon.xpc.config.lang.LangConfig;
import ru.lairon.xpc.config.settings.SettingsConfig;
import ru.lairon.xpc.data.DataProvider;
import ru.lairon.xpc.data.sql.sqlite.SQLiteDataProvider;
import ru.lairon.xpc.service.EntityService;

import java.io.File;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.velocitypowered.api.proxy.ProxyServer;

@Configuration

public class AbstractVelocityConfiguration extends AbstractConfiguration {

    @Autowired
    @Qualifier("proxyServer")
    private ProxyServer proxyServer;

    @Autowired
    @Qualifier("dataDir")
    private File dataDir;


    @Override
    @Bean
    public LangConfig langConfig() {
        final LangConfig config = new LangConfig(dataDir + File.separator + "lang.yml", VelocityYamlConfigModule.class);
        config.reload();
        return config;
    }

    @Override
    @Bean
    public SettingsConfig settingsConfig() {
        final SettingsConfig config = new SettingsConfig(dataDir + File.separator + "settings.yml", VelocityYamlConfigModule.class);
        config.reload();
        return config;
    }

    @Override
    @Bean
    public DataProvider dataProvider() {
        try {
            Files.createDirectories(dataDir.toPath());
            Files.createFile(new File(dataDir + File.separator + "data.db").toPath());
        } catch (Exception e) {
        }

        DataProvider dataProvider = new SQLiteDataProvider(dataDir + File.separator + "data.db");
        return dataProvider;
    }

    @Override
    @Bean
    public EntityService entityService() {
        return new VelocityEntityService(proxyServer);
    }

    @Override
    @Bean
    public MessageService messageService() {
        return new VelocityMessageService(proxyServer);
    }

}
