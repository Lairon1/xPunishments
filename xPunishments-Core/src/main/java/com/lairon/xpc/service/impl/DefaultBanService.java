package com.lairon.xpc.service.impl;

import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.model.NamedEntity;
import com.lairon.xpc.model.Player;
import com.lairon.xpc.service.BanService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultBanService implements BanService {

    private final DataProvider dataProvider;

    @Override
    public void ban(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason) {

    }

    @Override
    public void tempBan(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason, long duration) {

    }

    @Override
    public void unban(@NonNull NamedEntity executor, @NonNull Player punished, @NonNull String reason) {

    }
}
