package main.java.com.hu.picit;

import main.java.com.hu.core.server.ServerInitializer;

import java.util.logging.Logger;

public class Api {

    private static final Logger logger = Logger.getLogger(Api.class.getName());
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {
        try {
            int port = getPortFromArgs(args);
            ServerInitializer serverInitializer = new ServerInitializer(port);
            serverInitializer.startServer();
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
}