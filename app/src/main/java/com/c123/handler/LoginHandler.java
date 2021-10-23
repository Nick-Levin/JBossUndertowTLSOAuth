package com.c123.handler;

import com.c123.model.User;
import com.c123.persistence.UserPersistenceLayer;
import com.c123.security.HashUtil;
import com.c123.security.JWTUtil;
import com.google.gson.Gson;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHandler implements HttpHandler {

    private static final Logger log = LoggerFactory.getLogger(RegisterHandler.class);
    private final UserPersistenceLayer userPersistenceLayer;
    private final Gson gson;

    public LoginHandler() {
        gson = new Gson();
        userPersistenceLayer = new UserPersistenceLayer();
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getRequestReceiver().receiveFullString(this::onSuccess, this::onFailure);
    }

    private void onSuccess(HttpServerExchange exchange, String data) {
        User user = gson.fromJson(data, User.class);
        try {
            userPersistenceLayer.receiveByUsernameAndPassword(user.getUsername(), HashUtil.hashString(user.getPassword()))
                    .ifPresentOrElse(usr -> { // USER DATA OCCURS
                        String token = JWTUtil.createToken(usr);
                        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json");
                        exchange.getResponseSender().send("{\"token\":\""+token+"\"}");
                    }, () -> { // LOGIN FAILED DUE TO INVALID DATA
                        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json");
                        exchange.getResponseSender().send("{\"msg\":\"username or password invalid\"}");
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void onFailure(HttpServerExchange exchange, Throwable err) {
        err.printStackTrace();
        log.error(err.getMessage());
        throw new RuntimeException(err.getMessage());
    }

}
