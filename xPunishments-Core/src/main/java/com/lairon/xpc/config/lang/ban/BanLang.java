package com.lairon.xpc.config.lang.ban;

import lombok.Getter;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;

@Getter
@Path("Ban.")
public class BanLang extends Config {

    @Path("Already")
    private String already = "{def_prefix} Игрок {player} уже наказан.";

    private PermanentBan perm = new PermanentBan();
    private TemporaryBan temp = new TemporaryBan();

}
