package ru.lairon.xpc.config.lang.ban;

import lombok.Getter;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;

@Getter
@Path("Ban.")
public class BanLang extends Config {

    @Path("Already")
    private String already = "{def_prefix} Пользователь &6{user_name}&7 уже наказан.";


    private PermanentBan permanent = new PermanentBan();
    private TemporaryBan temporary = new TemporaryBan();

}
