package com.example;

// App.java
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        // Create an instance of the HttpServer, specifying the host and port
        HttpServer server = new HttpServer("localhost", 8081);

        // GET handler for the "/hello" endpoint
        // This handler responds with a simple "Hello, World!" message
        // It sets the "Content-Type" header to "text/plain"
        server.addHandler("/hello", "GET", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, "Hello, World!");
        });

        // POST handler for the "/echo" endpoint
        // This handler echoes back the request body in the response
        // It sets the "Content-Type" header to "text/plain"
        server.addHandler("/echo", "POST", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, request.getBody());
        });

        // PUT handler for the "/put" endpoint
        // This handler responds with a message indicating that a PUT request was received
        // It sets the "Content-Type" header to "text/plain"
        server.addHandler("/put", "PUT", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, "PUT request received");
        });

        // PATCH handler for the "/patch" endpoint
        // This handler responds with a message indicating that a PATCH request was received
        // It sets the "Content-Type" header to "text/plain"
        server.addHandler("/patch", "PATCH", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, "PATCH request received");
        });

        // DELETE handler for the "/delete" endpoint
        // This handler responds with a message indicating that a DELETE request was received
        // It sets the "Content-Type" header to "text/plain"
        server.addHandler("/delete", "DELETE", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, "DELETE request received");
        });

        // Handler for the "/params" endpoint
        // This handler demonstrates accessing request parameters (headers, method, body)
        // It retrieves the headers, method, and body from the request
        // and includes them in the response
        // It sets the "Content-Type" header to "text/plain"
        server.addHandler("/params", "GET", request -> {
            Map<String, String> headers = request.getHeaders();
            String method = request.getMethod();
            String body = request.getBody();

            String response = "Received request with:\n" +
                    "Method: " + method + "\n" +
                    "Headers: " + headers.toString() + "\n" +
                    "Body: " + body;

            headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, response);
        });

        try {
            // Start the server
            // This will bind the server to the specified host and port
            // and start listening for incoming requests
            server.start();
        } catch (IOException e) {
            // If an IOException occurs while starting the server,
            // catch the exception and print the stack trace for debugging purposes
            e.printStackTrace();
        }
    }
}