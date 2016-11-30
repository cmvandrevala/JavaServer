package routing;

import http_request.Request;
import http_response.*;

import java.io.*;

public class Router {

    private RoutingTable routingTable;

    public Router(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    public HTTPResponse route(Request request) throws IOException {

        String verb = request.verb();
        String url = request.url();
        String[] verbList = routingTable.listRoutesForUrl(url);

        if(request.isBadRequest()) {
            return new Response400();
        }

        if(verbList.length == 0) {
            return new Response404();
        }

        if(!routingTable.urlHasVerb(url, verb)) {
            return new Response405();
        }

        if(verb.equals("PUT") && request.contentLength().equals("")) {
            return new Response411();
        }

        switch (verb) {
            case "HEAD":
                return new HeadResponse();
            case "GET":
                return new GetResponse(request);
            case "OPTIONS":
                return new OptionsResponse(availableVerbs(url));
            case "PUT":
                return put(request);
            default:
                return post();
        }

    }

    private HTTPResponse put(Request request) throws IOException {
        File file = new PathToUrlMapper().fileCorrespondingToUrl(request.url());
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
        out.println("<p>" + request.body() + "</p>");
        out.close();
        return new HeadResponse();
    }

    private HTTPResponse post() {
        return new HeadResponse();
    }

    private String availableVerbs(String url) {
        String[] verbs = routingTable.listRoutesForUrl(url);
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (String verb : verbs) {
            sb.append(delimiter).append(verb);
            delimiter = ",";
        }
        return sb.toString();
    }

}
