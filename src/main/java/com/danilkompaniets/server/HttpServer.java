package com.danilkompaniets.server;

import com.danilkompaniets.server.config.Configuration;
import com.danilkompaniets.server.config.ConfigurationManager;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public  static void main(String[] args) throws ConfigurationException {
        System.out.println("Server started");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using port " + conf.getPort());
        System.out.println("Using webroot " + conf.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title>JAVA HTTP SERVER</title></head><body><h1>Simple http web server</h1></body></html>";

            final String CRLF = "\n\r";

            String response =
                    "HTTP/1.1 200 OK" + CRLF +
                            "Content-length: " + html.getBytes().length + CRLF +
                            CRLF +
                            html +
                            CRLF +
                            CRLF; //Status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
