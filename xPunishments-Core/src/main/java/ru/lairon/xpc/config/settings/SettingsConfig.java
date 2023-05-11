package ru.lairon.xpc.config.settings;

import lombok.Getter;
import lombok.NonNull;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;
import ru.lairon.libs.xconfig.configmodule.ConfigModule;

@Getter
@Path("Settings")
public class SettingsConfig extends Config {

    @Path("Format.date")
    private String dateFormat = "dd.MM.yyyy hh:mm:ss";

    @Path("Format.Duration")
    private String durationFormat = "dd дней HH часов mm минут ss секунд";

    public SettingsConfig(@NonNull String path, Class<? extends ConfigModule> module) {
        super(path, module);
    }
}
