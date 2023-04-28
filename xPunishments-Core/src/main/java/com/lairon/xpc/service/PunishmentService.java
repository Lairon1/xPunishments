package com.lairon.xpc.service;

import com.lairon.xpc.model.Player;
import com.lairon.xpc.model.Punishment;
import lombok.NonNull;

public interface PunishmentService {

    boolean canUseChat(@NonNull Player player);
    boolean canJoin(@NonNull Player player);

    String formatPunishmentDuration(@NonNull Punishment punishment, @NonNull String format);
    String formatPunishmentExpires(@NonNull Punishment punishment, @NonNull String format);

}
