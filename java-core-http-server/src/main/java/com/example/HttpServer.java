package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpServer {
    private final String host;
    private final int port;
    private final Map<String, Map<String, HttpHandler>> handlers;
    private volatile boolean running;
    private ServerSocketChannel serverChannel;
    private Selector selector;

    public HttpServer(String host, int port) {
        this.host = host;
        this.port = port;
        this.handlers = new HashMap<>();
        this.running = false;
    }

    public void addHandler(String path, String method, HttpHandler handler) {
        handlers.computeIfAbsent(path, k -> new HashMap<>()).put(method, handler);
    }

    public void start() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(host, port));
        serverChannel.configureBlocking(false);

        selector = Selector.open();

        if (selector.isOpen()) {
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } else {
            throw new IOException("Selector is closed");
        }


        running = true;

        while (running) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            for (SelectionKey key : selectedKeys) {
                if (key.isAcceptable()) {
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int bytesRead = clientChannel.read(buffer);

                    if (bytesRead > 0) {
                        buffer.flip();
                        String request = StandardCharsets.UTF_8.decode(buffer).toString();

                        HttpRequest httpRequest = parseRequest(request);
                        HttpResponse httpResponse = handleRequest(httpRequest);

                        ByteBuffer responseBuffer = ByteBuffer.wrap(httpResponse.toString().getBytes());
                        clientChannel.write(responseBuffer);
                    }

                    clientChannel.close();
                }
            }

            selectedKeys.clear();
        }
    }

    public void stop() throws IOException {
        running = false;
        if (selector != null) {
            selector.wakeup();
            selector.close();
        }
        if (serverChannel != null) {
            serverChannel.close();
        }
    }

    private HttpRequest parseRequest(String request) {
        String[] lines = request.split("\\r\\n");
        String[] firstLine = lines[0].split(" ");
        String method = firstLine[0];
        String path = firstLine[1];

        Map<String, String> headers = new HashMap<>();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isEmpty()) {
                break;
            }
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }

        String body = null;
        if (lines.length > 2 && headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            body = lines[lines.length - 1].substring(0, contentLength);
        }

        return new HttpRequest(method, path, headers, body);
    }

    public HttpResponse handleRequest(HttpRequest request) {
        String path = request.getPath();
        String method = request.getMethod();

        if (handlers.containsKey(path)) {
            Map<String, HttpHandler> methodHandlers = handlers.get(path);
            if (methodHandlers.containsKey(method)) {
                HttpHandler handler = methodHandlers.get(method);
                return handler.handle(request);
            }
        }

        return new HttpResponse(404, "Not Found", new HashMap<>(), "");
    }
}