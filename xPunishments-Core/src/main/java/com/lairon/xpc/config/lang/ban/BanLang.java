package com.lairon.xpc.config.lang.ban;

import lombok.Getter;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;

@Getter
@Path("Ban.")
public class BanLang extends Config {

    @Path("cannot")
    private String cannot = "&7[&6xPM&7] Вы не можете забанить игрока &6{player}&7 так как у него выше права доступа.";

    private PermanentBan perm = new PermanentBan();
    private TemporaryBan temp = new TemporaryBan();

}
