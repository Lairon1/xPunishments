package com.lairon.xpc.handler;

import com.lairon.xpc.model.Player;
import lombok.NonNull;

public interface JoinHandler {

    void onJoin(@NonNull Player player);

}
