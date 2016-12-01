package server;

import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;
import routing.DummyAction;
import routing.RoutingTable;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        DummyAction action = new DummyAction();
        RoutingTable routingTable = RoutingTable.getInstance();
        routingTable.addRoute("/", "GET", action);
        routingTable.addRoute("/", "HEAD", action);

        routingTable.addRoute("/foo", "GET", action);
        routingTable.addRoute("/foo", "HEAD", action);
        routingTable.addRoute("/foo", "PUT", action);

        routingTable.addRoute("/method_options", "GET", action);
        routingTable.addRoute("/method_options", "HEAD", action);
        routingTable.addRoute("/method_options", "POST", action);
        routingTable.addRoute("/method_options", "PUT", action);

        routingTable.addRoute("/method_options2", "GET", action);

        routingTable.addRoute("/form", "GET", action);
        routingTable.addRoute("/form", "PUT", action);
        routingTable.addRoute("/form", "POST", action);

        routingTable.addRoute("/file1", "GET", action);
        routingTable.addRoute("/file1", "HEAD", action);

        routingTable.addRoute("/file2", "GET", action);

        routingTable.addRoute("/text-file.txt", "GET", action);

        Server server = new Server(5000, 10);
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        server.start();

    }

}


