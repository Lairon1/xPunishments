package com.lairon.xpc.config;

public interface LangConfig {

    default String getBanCause() {
        return """
                &7---------------------
                &cВы были забанены
                                
                &7На время: &6{duration}
                &7Истекает в: &6{issued}
                &7По причине: &6{reason}
                &7---------------------
                """;
    }

    default String getForever() {
        return "&cНАВСЕГДА";
    }


}
