import com.sun.net.httpserver.HttpServer;
import controllers.ResourceController;
import controllers.RouterController;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.logging.Logger;

public class Api {

    private static final Logger logger = Logger.getLogger(Api.class.getName());
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        try {
            int port = getPortFromArgs(args);
            HttpServer server = createServer(port);

            // Automatically register controllers
            RouterController router = new RouterController(Arrays.asList(
                new ResourceController()
            ));

            // Create context for the API
            server.createContext("/api", router);

            server.start();
            logger.info("Server is listening on port " + port + "...");

            // Add shutdown hook for graceful shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                server.stop(1);
                logger.info("Server stopped gracefully.");
            }));

        } catch (Exception e) {
            logger.severe("Error starting server: " + e.getMessage());
        }
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.warning("Invalid port number provided, using default port " + DEFAULT_PORT);
            }
        }
        return DEFAULT_PORT;
    }

    private static HttpServer createServer(int port) throws Exception {
        return HttpServer.create(new InetSocketAddress(port), 0);
    }
}