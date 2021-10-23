package com.c123.router;

import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;

public class MainRouter {

    private MainRouter() {}

    public static HttpHandler buildRouter() {
        return new RoutingHandler(true)
                .addAll(UserRouter.buildRouter());
    }

}
