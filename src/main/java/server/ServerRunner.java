package server;

import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;
import routing.RoutingTable;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        RoutingTable routingTable = RoutingTable.getInstance();
        routingTable.addRoute("/", "GET");
        routingTable.addRoute("/", "HEAD");

        routingTable.addRoute("/foo", "GET");
        routingTable.addRoute("/foo", "HEAD");
        routingTable.addRoute("/foo", "PUT");

        routingTable.addRoute("/method_options", "GET");
        routingTable.addRoute("/method_options", "HEAD");
        routingTable.addRoute("/method_options", "POST");
        routingTable.addRoute("/method_options", "PUT");

        routingTable.addRoute("/method_options2", "GET");

        routingTable.addRoute("/form", "PUT");
        routingTable.addRoute("/form", "POST");

        routingTable.addRoute("/file1", "GET");
        routingTable.addRoute("/file1", "HEAD");

        routingTable.addRoute("/file2", "GET");

        routingTable.addRoute("/text-file.txt", "GET");

        Server server = new Server(5000, 10);
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        server.start();

    }

}


