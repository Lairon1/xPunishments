package ru.lairon.xpc.handler;

import ru.lairon.xpc.model.User;
import lombok.NonNull;

public interface ChatHandler {

    boolean onChat(@NonNull User user);

}
