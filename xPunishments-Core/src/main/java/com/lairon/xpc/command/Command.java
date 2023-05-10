package com.lairon.xpc.command;

import lombok.NonNull;
import ru.lairon.service.namedentity.NamedEntity;

import java.util.List;

public interface Command {

    void execute(@NonNull NamedEntity executor, @NonNull List<String> args);

}
