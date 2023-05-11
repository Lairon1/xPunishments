package com.lairon.xpc.handler;

import com.lairon.xpc.model.User;
import lombok.NonNull;

public interface JoinHandler {

    void onJoin(@NonNull User user);

}
