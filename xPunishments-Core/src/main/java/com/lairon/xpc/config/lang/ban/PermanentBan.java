package com.lairon.xpc.config.lang.ban;

import lombok.Getter;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;

@Getter
@Path("Permanent.")
public class PermanentBan extends Config {

    @Path("Usage")
    private String usage = "{def_prefix} Используйте: &6/ban <player> <reason>";

    @Path("Announce")
    private String announce = "{def_prefix} Игрок &6{operator}&7 забанил игрока &6{player}&7 [&4ПОДРОБНЕЕ&7]";

    @Path("Message")
    private String message = "{def_prefix} Вы успешно забанили игрока &6{player}&7.";

    @Path("Cause")
    private String cause = """
            &7Ваш аккаунт заблокирован &4НАВСЕГДА&7!
            
            &7Вас забанил: &6{operator}
            &7По причине: &6{reason}
            
            &7Обжаловать блокировку можно по ссылке: &6https://some.site.ru/forum
            &7Платный разбан можно приобрести на сайте: &6https://some.site.ru/donate
            """;

}
