package ru.lairon.xpc.config.lang;

import ru.lairon.xpc.config.lang.ban.BanLang;
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
    private String playerWasNotFound = "{def_prefix} Пользователь &6{user_name}&7 никогда не заходил на сервер.";

    @Path("DontHavePermissions")
    private String dontHavePermissions = "{def_prefix} У вас недостаточно прав для этого! Вы должны иметь право &6{permission}&7.";

    @Path("ReasonNotIndicated")
    private String reasonNotIndicated = "&4НЕ УКАЗАНА";
    @Path("Permanent")
    private String permanent = "&4НАВСЕГДА";

    @Path("CantYourself")
    private String cantYourself = "{def_prefix} Вы не можете наказать самого себя.";

    @Path("UnknownError")
    private String unknownError = "{def_prefix} Произошла неизвестная ошибка! (&c{errorName}&7) Смотрите консоль.";

    private final BanLang banLang = new BanLang();


    public LangConfig(@NonNull String path, Class<? extends ConfigModule> module) {
        super(path, module);
    }
}
