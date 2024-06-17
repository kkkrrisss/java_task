package com.example;

public interface HttpHandler {
    HttpResponse handle(HttpRequest request);
}
