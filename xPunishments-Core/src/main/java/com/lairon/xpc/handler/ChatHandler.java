package com.lairon.xpc.handler;

import com.lairon.xpc.model.User;
import lombok.NonNull;

public interface ChatHandler {

    boolean onChat(@NonNull User user);

}
