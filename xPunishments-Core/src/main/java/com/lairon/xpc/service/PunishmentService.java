package com.lairon.xpc.service;

import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.Punishable;
import lombok.NonNull;

public interface PunishmentService {

    boolean canUseChat(@NonNull Player player);
    boolean canJoin(@NonNull Player player);

}
