package com.lairon.xpc.config.lang;

import com.lairon.xpc.config.lang.ban.BanLang;
import lombok.Getter;
import lombok.NonNull;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;
import ru.lairon.libs.xconfig.configmodule.ConfigModule;

import java.util.Map;

@Getter
@Path("Lang.")
public class LangConfig extends Config {

    @Path("DefaultPlaceholders")
    private Map<String, String> defaultPlaceholders = Map.of("prefix", "&7[&6xPM&7]");

    @Path("PlayerWasNotFound")
    private String playerWasNotFound = "{def_prefix} Игрок &6{player}&7 никогда не заходил на сервер.";

    @Path("DontHavePermissions")
    private String dontHavePermissions = "{def_prefix} У вас недостаточно прав для этого! Вы должны иметь право &6{permission}&7.";

    @Path("ReasonNotIndicated")
    private String reasonNotIndicated = "&4НЕ УКАЗАНА";

    private final BanLang banLang = new BanLang();


    public LangConfig(@NonNull String path, Class<? extends ConfigModule> module) {
        super(path, module);
    }
}
