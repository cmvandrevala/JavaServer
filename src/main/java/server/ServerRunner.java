package server;

import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;
import http_action.NullAction;
import routing.RoutesTable;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        NullAction action = new NullAction();
        RoutesTable routesTable = RoutesTable.getInstance();
        routesTable.addRoute("/", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/", RoutesTable.Verb.HEAD, action);

        routesTable.addRoute("/foo", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/foo", RoutesTable.Verb.HEAD, action);
        routesTable.addRoute("/foo", RoutesTable.Verb.PUT, action);

        routesTable.addRoute("/method_options", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/method_options", RoutesTable.Verb.HEAD, action);
        routesTable.addRoute("/method_options", RoutesTable.Verb.POST, action);
        routesTable.addRoute("/method_options", RoutesTable.Verb.PUT, action);

        routesTable.addRoute("/method_options2", RoutesTable.Verb.GET, action);

        routesTable.addRoute("/form", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/form", RoutesTable.Verb.PUT, action);
        routesTable.addRoute("/form", RoutesTable.Verb.POST, action);

        routesTable.addRoute("/file1", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/file1", RoutesTable.Verb.HEAD, action);

        routesTable.addRoute("/file2", RoutesTable.Verb.GET, action);

        routesTable.addRoute("/text-file.txt", RoutesTable.Verb.GET, action);

        Server server = new Server(5000);
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        new Thread(server).start();

    }

}
