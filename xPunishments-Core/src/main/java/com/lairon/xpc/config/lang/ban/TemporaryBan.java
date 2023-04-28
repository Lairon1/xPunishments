package com.lairon.xpc.config.lang.ban;

import lombok.Getter;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;

@Getter
@Path("Temporary.")
public class TemporaryBan extends Config {

    @Path("Usage")
    private String usage = "&7[&6xPM&7] Используйте: &6/tempban <player> <time> <reason>";

    @Path("Announce")
    private String announce = "&7[&6xPM&7] Игрок &6{operator}&7 забанил на &6{time}&7 игрока &6{player}&7 [&4ПОДРОБНЕЕ&7]";

    @Path("Message")
    private String message = "&7[&6xPM&7] Вы успешно забанили игрока &6{player}&7 на &6{time}&7.";

    @Path("Cause")
    private String cause = """
            Ваш аккаунт временно заблокирован!
            
            Вас забанил: &6{operator}
            По причине: &6{reason}
            &7Забанены на: &6{time}
            &7Блокировка продлится до: &6{expires}
            
            Обжаловать блокировку можно по ссылке: https://some.site.ru/forum
            Платный разбан можно приобрести на сайте: https://some.site.ru/donate
            """;

}

