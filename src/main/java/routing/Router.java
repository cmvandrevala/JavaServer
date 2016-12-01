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

        if(response411condition(request)) {
            return new Response411();
        }

        switch (verb) {
            case "HEAD":
                return new HeadResponse();
            case "GET":
                return new GetResponse(request);
            case "OPTIONS":
                return new OptionsResponse(request);
            case "PUT":
                return new PutResponse(request);
            case "POST":
                return new PutResponse(request);
            default:
                return new Response400();
        }

    }

    private boolean response411condition(Request request) {
        return request.verb().equals("PUT") && request.contentLength().equals("") ||
                request.verb().equals("POST") && request.contentLength().equals("");
    }

}
