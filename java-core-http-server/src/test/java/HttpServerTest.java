import com.example.HttpRequest;
import com.example.HttpResponse;
import com.example.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {
    private static HttpServer server;
    private static Thread serverThread;


    @BeforeAll
    public static void setUp() throws IOException {
        server = new HttpServer("localhost", 8081);

        server.addHandler("/hello", "GET", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, "Hello, World!");
        });

        server.addHandler("/echo", "POST", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, request.getBody());
        });

        server.addHandler("/put", "PUT", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, "PUT request received");
        });

        server.addHandler("/patch", "PATCH", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, "PATCH request received");
        });

        server.addHandler("/delete", "DELETE", request -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/plain");
            return new HttpResponse(200, "OK", headers, "DELETE request received");
        });

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

        serverThread = new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @AfterAll
    public static void tearDown() throws IOException, InterruptedException {
        server.stop();
        serverThread.join();
    }

    @Test
    public void testGetHello() {
        HttpRequest request = new HttpRequest("GET", "/hello", new HashMap<>(), null);
        HttpResponse response = server.handleRequest(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("text/plain", response.getHeaders().get("Content-Type"));
        assertEquals("Hello, World!", response.getBody());
    }

    @Test
    public void testPostEcho() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "10");
        HttpRequest request = new HttpRequest("POST", "/echo", headers, "Echo this!");
        HttpResponse response = server.handleRequest(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("text/plain", response.getHeaders().get("Content-Type"));
        assertEquals("Echo this!", response.getBody());
    }

    @Test
    public void testPutRequest() {
        HttpRequest request = new HttpRequest("PUT", "/put", new HashMap<>(), null);
        HttpResponse response = server.handleRequest(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("text/plain", response.getHeaders().get("Content-Type"));
        assertEquals("PUT request received", response.getBody());
    }

    @Test
    public void testPatchRequest() {
        HttpRequest request = new HttpRequest("PATCH", "/patch", new HashMap<>(), null);
        HttpResponse response = server.handleRequest(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("text/plain", response.getHeaders().get("Content-Type"));
        assertEquals("PATCH request received", response.getBody());
    }

    @Test
    public void testDeleteRequest() {
        HttpRequest request = new HttpRequest("DELETE", "/delete", new HashMap<>(), null);
        HttpResponse response = server.handleRequest(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("text/plain", response.getHeaders().get("Content-Type"));
        assertEquals("DELETE request received", response.getBody());
    }

    @Test
    public void testParamsRequest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer abcdef123456");
        HttpRequest request = new HttpRequest("GET", "/params", headers, "{\"key\": \"value\"}");
        HttpResponse response = server.handleRequest(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
        assertEquals("text/plain", response.getHeaders().get("Content-Type"));
        String expectedBody = "Received request with:\n" +
                "Method: GET\n" +
                "Headers: {Authorization=Bearer abcdef123456, Content-Type=application/json}\n" +
                "Body: {\"key\": \"value\"}";
        assertEquals(expectedBody, response.getBody());
    }
}