package server;

import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;
import routing.NullAction;
import routing.RoutingTable;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        NullAction action = new NullAction();
        RoutingTable routingTable = RoutingTable.getInstance();
        routingTable.addRoute("/", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/", RoutingTable.Verb.HEAD, action);

        routingTable.addRoute("/foo", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/foo", RoutingTable.Verb.HEAD, action);
        routingTable.addRoute("/foo", RoutingTable.Verb.PUT, action);

        routingTable.addRoute("/method_options", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/method_options", RoutingTable.Verb.HEAD, action);
        routingTable.addRoute("/method_options", RoutingTable.Verb.POST, action);
        routingTable.addRoute("/method_options", RoutingTable.Verb.PUT, action);

        routingTable.addRoute("/method_options2", RoutingTable.Verb.GET, action);

        routingTable.addRoute("/form", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/form", RoutingTable.Verb.PUT, action);
        routingTable.addRoute("/form", RoutingTable.Verb.POST, action);

        routingTable.addRoute("/file1", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/file1", RoutingTable.Verb.HEAD, action);

        routingTable.addRoute("/file2", RoutingTable.Verb.GET, action);

        routingTable.addRoute("/text-file.txt", RoutingTable.Verb.GET, action);

        Server server = new Server(5000);
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        new Thread(server).start();

    }

}
