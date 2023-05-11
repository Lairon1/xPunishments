package com.lairon.xpc.service;

import com.lairon.xpc.model.User;
import com.lairon.xpc.model.Punishment;
import lombok.NonNull;

public interface PunishmentService {

    boolean canUseChat(@NonNull User user);
    boolean canJoin(@NonNull User user);

    String formatPunishmentDuration(@NonNull Punishment punishment, @NonNull String format);
    String formatPunishmentIssued(@NonNull Punishment punishment, @NonNull String format);

}
