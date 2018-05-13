package main;

import chat.WebSocketChatServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {context});
        server.setHandler(handlers);

        server.start();
        log.info("Server started");
        server.join();

    }
}
