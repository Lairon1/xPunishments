package com.lairon.xpc.service.impl;

import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.model.NamedEntity;
import com.lairon.xpc.model.Player;
import com.lairon.xpc.service.BanService;
import lombok.NonNull;

import com.google.inject.Inject;

public class DefaultBanService implements BanService {

    @Inject
    private DataProvider dataProvider;

    @Override
    public void ban(@NonNull NamedEntity operator, @NonNull Player player, @NonNull String reason) {

    }

    @Override
    public void tempBan(@NonNull NamedEntity operator, @NonNull Player player, @NonNull String reason, long duration) {

    }

    @Override
    public void unban(@NonNull NamedEntity operator, @NonNull Player player, @NonNull String reason) {

    }
}
