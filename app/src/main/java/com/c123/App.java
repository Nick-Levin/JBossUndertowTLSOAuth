package com.c123;

import com.c123.config.AppConfig;
import com.c123.router.MainRouter;
import com.c123.security.SSLContextBuilder;
import io.undertow.Undertow;

public class App {

    public static void main(String ...args) throws Exception {

        final int port = Integer.parseInt(AppConfig.getProperty("server.port"));
        final String host = AppConfig.getProperty("server.host");

        Undertow.builder()
                .addHttpsListener(port, host, SSLContextBuilder.build())
                .setHandler(MainRouter.buildRouter())
                .build()
                .start();

    }

}
