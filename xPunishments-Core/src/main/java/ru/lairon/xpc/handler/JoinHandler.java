package ru.lairon.xpc.handler;

import ru.lairon.xpc.model.User;
import lombok.NonNull;

public interface JoinHandler {

    void onJoin(@NonNull User user);

}
