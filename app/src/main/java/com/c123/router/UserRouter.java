package com.c123.router;

import com.c123.handler.LoginHandler;
import com.c123.handler.RegisterHandler;
import com.c123.handler.StatusHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Methods;

public class UserRouter {

    private UserRouter() {}

    public static RoutingHandler buildRouter() {
        return new RoutingHandler()
                .add(Methods.POST, "users/login", new LoginHandler())
                .add(Methods.POST, "users/register", new RegisterHandler())
                .add(Methods.GET, "users/", new StatusHandler());
    }

}
