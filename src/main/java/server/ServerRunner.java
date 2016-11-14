package server;

import http_request.Router;
import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        Router router = new Router();
        router.addRoute("/", "GET");
        router.addRoute("/", "HEAD");
        router.addRoute("/foo", "GET");
        router.addRoute("/foo", "HEAD");
        router.addRoute("/method_options", "GET");
        router.addRoute("/method_options", "HEAD");
        router.addRoute("/method_options", "POST");
        router.addRoute("/method_options", "PUT");
        router.addRoute("/method_options2", "GET");

        router.addRoute("/file1", "GET");
        router.addRoute("/file2", "GET");


        Server server = new Server(router);
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        server.start();

    }

}
