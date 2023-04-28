package com.lairon.xpc;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lairon.xpc.config.lang.LangConfig;
import com.lairon.xpc.config.settings.SettingsConfig;
import com.lairon.xpc.data.DataProvider;
import com.lairon.xpc.handler.BanHandler;
import com.lairon.xpc.handler.ChatHandler;
import com.lairon.xpc.handler.JoinHandler;
import com.lairon.xpc.service.BanService;
import com.lairon.xpc.service.PlaceholderService;
import com.lairon.xpc.service.PlayerService;
import com.lairon.xpc.service.PunishmentService;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PunishmentCore {

    private LangConfig lang;
    private SettingsConfig settings;
    private DataProvider dataProvider;
    private BanHandler banHandler;
    private ChatHandler chatHandler;
    private JoinHandler joinHandler;
    private BanService banService;
    private PlaceholderService placeholderService;
    private PlayerService playerService;
    private PunishmentService punishmentService;

    private InjectModule injectModule;
    private Injector injector;

    public PunishmentCore() {
        injectModule = new InjectModule();
    }

    public void start() {
        injector = Guice.createInjector(injectModule);

        lang = injector.getInstance(LangConfig.class);


    }


    @Setter
    @Getter
    private static class InjectModule extends AbstractModule {


        @Override
        protected void configure() {

        }

        public <T> void bindInstance(Class<T> aClass, T o) {
            bind(aClass).toInstance(o);
        }

        public <T> void bind(Class<T> aClass, Class<? extends T> tClass) {
            bind(aClass).to(tClass);
        }


    }

}
