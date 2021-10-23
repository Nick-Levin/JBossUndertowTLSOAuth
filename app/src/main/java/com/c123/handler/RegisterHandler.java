package com.c123.handler;

import com.c123.model.User;
import com.c123.persistence.UserPersistenceLayer;
import com.google.gson.Gson;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterHandler implements HttpHandler {

    private static final Logger log = LoggerFactory.getLogger(RegisterHandler.class);
    private final UserPersistenceLayer userPersistenceLayer;
    private final Gson gson;

    public RegisterHandler() {
        gson = new Gson();
        userPersistenceLayer = new UserPersistenceLayer();
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getRequestReceiver().receiveFullString(this::onSuccess, this::onFailure);
    }

    private void onSuccess(HttpServerExchange exchange, String data) {
        if(data.isEmpty()) {
            log.error("User is not received");
        }

        User user = gson.fromJson(data, User.class);

        // Check if user with same data already exists in DB
        userPersistenceLayer.receiveByEmail(user.getEmail())
                .ifPresentOrElse(
                        usr -> { // EXISTS
                            exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json");
                            exchange.getResponseSender().send("{\"msg\":\"user already exists\"}");
                        },
                        () -> { // NOT EXISTS
                            try {
                                userPersistenceLayer.create(user); // Register new user
                                exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, "application/json");
                                exchange.getResponseSender().send("{\"msg\":\"user registered successfully\"}");
                            } catch (Exception e) {
                                onFailure(exchange, e);
                            }
                        }
                );
    }

    private void onFailure(HttpServerExchange exchange, Throwable err) {
        err.printStackTrace();
        log.error(err.getMessage());
        throw new RuntimeException(err.getMessage());
    }

}
