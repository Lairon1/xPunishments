package com.lairon.xpc.handler;

import com.lairon.xpc.model.Player;
import lombok.NonNull;

public interface ChatHandler {

    boolean onChat(@NonNull Player player);

}
