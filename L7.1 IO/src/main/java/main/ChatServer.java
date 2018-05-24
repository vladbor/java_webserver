package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class ChatServer extends Thread {
    static final Logger logger = LogManager.getLogger(ChatServer.class.getName());


    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ChatServer(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //включаем autoflush буфера
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        // Если любой из вышеприведенных вызовов приведет к возникновению исключения, то вызывающий отвечает за
        // закрытие сокета.
        // В противном случае, нить в методе run закроет его.

        logger.info("Starting new thread");
        start(); // вызываем run()
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("Bye")) {
                    break;
                }
                out.println(str);
            }
        } catch (IOException e) {
            logger.error("IOException ", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Socket not closed ", e);
            }
        }
    }
}
