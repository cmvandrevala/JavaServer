package server;

import http_action.*;
import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;
import routing.RoutesTable;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        RoutesTable routesTable = RoutesTable.getInstance();

        routesTable.addRoute("/", RoutesTable.Verb.GET, new NullAction());
        routesTable.addRoute("/", RoutesTable.Verb.HEAD, new NullAction());

        routesTable.addRoute("/foo", RoutesTable.Verb.GET, new NullAction());
        routesTable.addRoute("/foo", RoutesTable.Verb.HEAD, new NullAction());
        routesTable.addRoute("/foo", RoutesTable.Verb.PUT, new PutAction());

        routesTable.addRoute("/method_options", RoutesTable.Verb.GET, new NullAction());
        routesTable.addRoute("/method_options", RoutesTable.Verb.HEAD, new NullAction());
        routesTable.addRoute("/method_options", RoutesTable.Verb.POST, new PostAction());
        routesTable.addRoute("/method_options", RoutesTable.Verb.PUT, new PutAction());

        routesTable.addRoute("/method_options2", RoutesTable.Verb.GET, new NullAction());

        routesTable.addRoute("/form", RoutesTable.Verb.GET, new NullAction());
        routesTable.addRoute("/form", RoutesTable.Verb.PUT, new PutAction());
        routesTable.addRoute("/form", RoutesTable.Verb.POST, new PostAction());
        routesTable.addRoute("/form", RoutesTable.Verb.DELETE, new DeleteAction());

        routesTable.addRoute("/file1", RoutesTable.Verb.GET, new ReadFromTextFileAction());
        routesTable.addRoute("/file1", RoutesTable.Verb.HEAD, new NullAction());

        routesTable.addRoute("/file2", RoutesTable.Verb.GET, new ReadFromTextFileAction());

        routesTable.addRoute("/text-file.txt", RoutesTable.Verb.GET, new ReadFromTextFileAction());

        Server server = new Server(5000);
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        new Thread(server).start();

    }

}
