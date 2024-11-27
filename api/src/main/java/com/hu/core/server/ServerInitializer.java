package main.java.com.hu.core.server;

import com.sun.net.httpserver.HttpServer;
import main.java.com.hu.core.controller.RouterController;

import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class ServerInitializer {

    private static final Logger logger = Logger.getLogger(ServerInitializer.class.getName());
    private final int port;

    public ServerInitializer(int port) {
        this.port = port;
    }

    public void startServer() throws Exception {
        HttpServer server = createServer();
        RouterController router = new RouterController();

        // Create context for the API
        server.createContext("/api", router);

        server.start();
        logger.info("Server is listening on port " + port + "...");

        // Add shutdown hook for graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop(1);
            logger.info("Server stopped gracefully.");
        }));
    }

    private HttpServer createServer() throws Exception {
        return HttpServer.create(new InetSocketAddress(port), 0);
    }
}