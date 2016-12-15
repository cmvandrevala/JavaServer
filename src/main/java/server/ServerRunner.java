package server;

import http_action.*;
import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;
import routing.DataTable;
import routing.RoutesTable;

public class ServerRunner {

    public static void main(String args[]) throws Exception {

        DataTable dataTable = new DataTable();
        RoutesTable routesTable = new RoutesTable();

        routesTable.addRoute("/", RoutesTable.Verb.GET, new DirectoryListingAction());
        routesTable.addRoute("/", RoutesTable.Verb.HEAD);

        routesTable.addRoute("/tea", RoutesTable.Verb.GET);

        routesTable.addRoute("/foo", RoutesTable.Verb.GET);
        routesTable.addRoute("/foo", RoutesTable.Verb.HEAD);
        routesTable.addRoute("/foo", RoutesTable.Verb.PUT, new PutAction());

        routesTable.addRoute("/method_options", RoutesTable.Verb.GET);
        routesTable.addRoute("/method_options", RoutesTable.Verb.HEAD);
        routesTable.addRoute("/method_options", RoutesTable.Verb.POST, new PostAction());
        routesTable.addRoute("/method_options", RoutesTable.Verb.PUT, new PutAction());

        routesTable.addRoute("/method_options2", RoutesTable.Verb.GET);

        routesTable.addRoute("/form", RoutesTable.Verb.GET);
        routesTable.addRoute("/form", RoutesTable.Verb.PUT, new PutAction());
        routesTable.addRoute("/form", RoutesTable.Verb.POST, new PostAction());
        routesTable.addRoute("/form", RoutesTable.Verb.DELETE, new DeleteAction());

        routesTable.addRoute("/file1", RoutesTable.Verb.GET, new ReadFromTextFileAction());
        routesTable.addRoute("/file1", RoutesTable.Verb.HEAD);

        routesTable.addRoute("/file2", RoutesTable.Verb.GET, new ReadFromTextFileAction());

        routesTable.addRoute("/text-file.txt", RoutesTable.Verb.GET, new ReadFromTextFileAction());

        routesTable.addRoute("/partial-content.txt", RoutesTable.Verb.GET, new ReadFromTextFileAction());

        routesTable.addRoute("/patch-content.txt", RoutesTable.Verb.GET, new ReadFromTextFileAction());

        routesTable.addRoute("/parameters", RoutesTable.Verb.GET, new QueryParametersAction());

        routesTable.addRoute("/image.jpeg", RoutesTable.Verb.GET);

        routesTable.addRoute("/image.png", RoutesTable.Verb.GET);

        routesTable.addRoute("/image.gif", RoutesTable.Verb.GET);

        routesTable.addRoute("/redirect", RoutesTable.Verb.GET, new RedirectAction("http://localhost:5000/"));
        routesTable.addRoute("/redirect2", RoutesTable.Verb.GET, new RedirectAction("http://52.15.206.185:5000/tea"));
        routesTable.addRoute("/redirect3", RoutesTable.Verb.GET, new RedirectAction("http://52.15.206.185:5000/coffee"));

        routesTable.addRoute("/cookie", RoutesTable.Verb.GET, new UrlReturnsCookieAction());

        routesTable.addRoute("/eat_cookie", RoutesTable.Verb.GET, new UrlAcceptsCookieAction());

        Server server = new Server(5000, routesTable, dataTable);
        DefaultMessages defaultMessages = new DefaultMessages();
        server.registerObserver(new ConsoleLog(defaultMessages));
        server.registerObserver(new FileLog(defaultMessages));
        new Thread(server).start();

    }

}
