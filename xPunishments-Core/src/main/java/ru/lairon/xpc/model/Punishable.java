package ru.lairon.xpc.model;

import lombok.NonNull;

public interface Punishable {

    Punishment getMute();
    void setMute(@NonNull Punishment mute);
    Punishment getBan();
    void setBan(@NonNull Punishment ban);

}
