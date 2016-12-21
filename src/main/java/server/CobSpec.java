package server;

import http_action.*;
import logging.ConsoleLog;
import logging.DefaultMessages;
import logging.FileLog;
import routing.DataTable;
import routing.PathToUrlMapper;
import routing.RoutesTable;

import java.util.Arrays;

public class CobSpec {

    public static void main(String args[]) throws Exception {

        DataTable dataTable = new DataTable();
        RoutesTable routesTable = new RoutesTable();

        int indexOfPublicDirectoryFlag = Arrays.asList(args).indexOf("-d");
        if(indexOfPublicDirectoryFlag == -1) {
            System.out.println("You must specify a public directory. For example, \"java -jar JavaServer.jar -d my/public/directory\".");
            return;
        }

        PathToUrlMapper mapper = new PathToUrlMapper(args[indexOfPublicDirectoryFlag + 1]);

        routesTable.addRoute("/", RoutesTable.Verb.GET, new DirectoryListingAction(mapper));
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

        routesTable.addRoute("/file1", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        routesTable.addRoute("/file1", RoutesTable.Verb.HEAD);

        routesTable.addRoute("/file2", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));

        routesTable.addRoute("/text-file.txt", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));

        routesTable.addRoute("/partial-content.txt", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));

        routesTable.addRoute("/patch-content.txt", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        routesTable.addRoute("/patch-content.txt", RoutesTable.Verb.PATCH, new PatchWithETagAction(mapper));
        dataTable.addData("/patch-content.txt", "ETag", "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec");

        routesTable.addRoute("/parameters", RoutesTable.Verb.GET, new QueryParametersAction());

        routesTable.addRoute("/image.jpeg", RoutesTable.Verb.GET);

        routesTable.addRoute("/image.png", RoutesTable.Verb.GET);

        routesTable.addRoute("/image.gif", RoutesTable.Verb.GET);

        routesTable.addRoute("/redirect", RoutesTable.Verb.GET, new RedirectAction("http://localhost:5000/"));
        routesTable.addRoute("/redirect2", RoutesTable.Verb.GET, new RedirectAction("http://52.15.206.185:5000/tea"));
        routesTable.addRoute("/redirect3", RoutesTable.Verb.GET, new RedirectAction("http://52.15.206.185:5000/coffee"));

        routesTable.addRoute("/cookie", RoutesTable.Verb.GET, new UrlReturnsCookieAction());

        routesTable.addRoute("/eat_cookie", RoutesTable.Verb.GET, new UrlAcceptsCookieAction());

        Runner runner = new Runner(routesTable, dataTable, mapper);

        int indexOfPortNumberFlag = Arrays.asList(args).indexOf("-p");
        if(indexOfPortNumberFlag >= 0) {
            runner.portNumber = Integer.parseInt(args[indexOfPortNumberFlag + 1]);
        }

        int indexOfThreadNumberFlag = Arrays.asList(args).indexOf("-t");
        if(indexOfThreadNumberFlag >= 0) {
            runner.numberOfThreads = Integer.parseInt(args[indexOfPortNumberFlag + 1]);
        }

        DefaultMessages defaultMessages = new DefaultMessages();
        runner.registerObserver(new ConsoleLog(defaultMessages));
        runner.registerObserver(new FileLog(defaultMessages));

        new Thread(runner).start();

    }

}
