package com.c123.middleware;

import io.undertow.server.HttpHandler;

public abstract class HttpMiddleware implements HttpHandler {

    final HttpHandler nextHandler;

    public HttpMiddleware(HttpHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

}
