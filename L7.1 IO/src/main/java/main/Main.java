package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static final int PORT = 5050;


    public static void main(String[] args) throws IOException {
        try (ServerSocket s = new ServerSocket(PORT)) {
            logger.info("Server started");
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = s.accept();
                try {
                    new ChatServer(socket);
                } catch (IOException e) {
                    // Если завершится неудачей, то закрывается сокет.
                    // В противном случае, нить закроет его:
                    socket.close();
                    logger.error("Error in operation with socket ", e);
                }
            }
        }
    }
}
