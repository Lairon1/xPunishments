package ru.lairon.xpc.config.lang.ban;

import lombok.Getter;
import ru.lairon.libs.xconfig.Config;
import ru.lairon.libs.xconfig.annotation.Path;

@Getter
@Path("Permanent.")
public class PermanentBan extends Config {

    @Path("Usage")
    private String usage = "{def_prefix} Используйте: &6/ban <user> <reason>";

    @Path("Announce")
    private String announce = "{def_prefix} &6{operator_name}&7 заблокировал пользователя &6{user_name}&7 " +
                              "[&4<hover:show_text:'&7Комментарий:&6 {reason}<newline>&7Заблокирован на: &6{duration}'>ПОДРОБНЕЕ&7</hover>]";

    @Path("Message")
    private String message = "{def_prefix} Вы успешно заблокировали пользователя &6{user_name}&7.";

    @Path("Cause")
    private String cause = """
            Вы были заблокированы!
            
            &7Вас заблокировал: &6{operator_name}
            &7Комментарий: &6{reason}
            &7Вы заблокирован на: &6{duration}
            
            &7Обжаловать блокировку можно по ссылке: &6https://some.site.ru/forum
            &7Платный разбан можно приобрести на сайте: &6https://some.site.ru/donate
            """;

}
