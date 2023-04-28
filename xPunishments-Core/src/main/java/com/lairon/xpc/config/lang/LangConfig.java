package com.lairon.xpc.config.lang;

import com.lairon.xpc.config.lang.ban.BanLang;
import lombok.Getter;
import lombok.NonNull;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;
import ru.lairon.libs.xconfig.configmodule.ConfigModule;

@Getter
@Path("Lang.")
public class LangConfig extends Config {


    private final BanLang banLang = new BanLang();

    public LangConfig(@NonNull String path, Class<? extends ConfigModule> module) {
        super(path, module);
    }
}
