package com.danilkompaniets.server;

import com.danilkompaniets.server.config.Configuration;
import com.danilkompaniets.server.config.ConfigurationManager;
import com.danilkompaniets.server.core.ServerListenerThread;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.util.logging.Logger;

public class HttpServer {
    private final static Logger LOGGER = Logger.getLogger(HttpServer.class.getName());

    public  static void main(String[] args) throws ConfigurationException, IOException {
        LOGGER.info("Server started");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port " + conf.getPort());
        LOGGER.info("Using webroot " + conf.getWebroot());

        ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
        serverListenerThread.start();
    }
}
