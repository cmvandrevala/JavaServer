package server;

import http_request.Router;
import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        Router router = new Router();
        router.addRoute("/", "GET");
        router.addRoute("/foo", "GET");
        router.addRoute("/", "HEAD");
        router.addRoute("/foo", "HEAD");


        Server server = new Server(router);
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        server.start();

    }

}
