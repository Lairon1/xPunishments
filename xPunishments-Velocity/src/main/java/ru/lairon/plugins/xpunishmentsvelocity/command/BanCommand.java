package ru.lairon.plugins.xpunishmentsvelocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import ru.lairon.plugins.xpunishmentsvelocity.adapter.VelocityAdapter;
import ru.lairon.service.namedentity.NamedEntity;
import ru.lairon.xpc.data.DataProvider;
import ru.lairon.xpc.handler.punishment.BanHandler;
import ru.lairon.xpc.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class BanCommand implements SimpleCommand {

    private final BanHandler banHandler;
    private final DataProvider dataProvider;

    @Override
    public void execute(Invocation invocation) {
        if (invocation.arguments().length < 1) {
            invocation.source().sendMessage(Component.text("Укажите ник кого банить то"));
            return;
        }


        NamedEntity executor = VelocityAdapter.adaptNamedEntity(invocation.source());
        User user = dataProvider.findByName(invocation.arguments()[0]).orElse(null);
        if (user == null) {
            invocation.source().sendMessage(Component.text("Игрок не найден"));
            return;
        }

        String reason = null;
        if (invocation.arguments().length > 2) {
            invocation.arguments()[0] = "";
            reason = String.join(" ", invocation.arguments());
        }


        banHandler.permanent(executor, user, reason, false);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return SimpleCommand.super.suggestAsync(invocation);
    }

}
