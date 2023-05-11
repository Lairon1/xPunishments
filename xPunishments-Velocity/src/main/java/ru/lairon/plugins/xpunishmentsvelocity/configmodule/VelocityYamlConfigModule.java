package ru.lairon.plugins.xpunishmentsvelocity.configmodule;

import lombok.NonNull;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import ru.lairon.libs.xconfig.configmodule.ConfigModule;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class VelocityYamlConfigModule implements ConfigModule {

    private ConfigurationNode rootNode;
    private YAMLConfigurationLoader loader;

    @Override
    public void load(@NonNull File file) {
        loader = YAMLConfigurationLoader
                .builder()
                .setPath(file.toPath())
                .setIndent(2)
                .build();
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean contains(@NonNull String path) {
        return !getNode(path).isEmpty();
    }

    @Override
    public void set(@NonNull String path, @NonNull Object data) {
        getNode(path).setValue(data);
    }

    @Override
    public void setComments(@NonNull String path, @NonNull List<String> comments) {
    }

    @Override
    public void save(@NonNull File file) {
        try {
            loader.save(rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object get(@NonNull String s, Object o) {
        Object data = get(s);
        return data == null ? o : data;
    }

    @Override
    public Object get(@NonNull String path) {
        return getNode(path).getValue();
    }

    public ConfigurationNode getNode(String path){
        Objects.requireNonNull(path, "path can not be null");
        if(path.isBlank()) throw new IllegalArgumentException("path can not be blank");
        return rootNode.getNode(path.split("\\."));
    }
}
