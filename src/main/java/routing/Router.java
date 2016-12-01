package routing;

import http_request.Request;
import http_response.*;

import java.io.IOException;

public class Router {

    private RoutingTable routingTable = RoutingTable.getInstance();

    public HTTPResponse route(Request request) throws IOException {

        if(response400condition(request)) {
            return new Response400();
        }

        if(response404condition(request)) {
            return new Response404();
        }

        if(response405condition(request)) {
            return new Response405();
        }

        if(response411condition(request)) {
            return new Response411();
        }

        switch (request.verb()) {
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
            case "DELETE":
                return new DeleteResponse(request);
            default:
                return new Response400();
        }

    }

    private boolean response400condition(Request request) {
        return request.isBadRequest();
    }

    private boolean response404condition(Request request) {
        String[] verbList = this.routingTable.listVerbsForUrl(request.url());
        return verbList.length == 0;
    }

    private boolean response405condition(Request request) {
        return !this.routingTable.urlHasVerb(request.url(), request.verb());
    }

    private boolean response411condition(Request request) {
        return request.verb().equals("PUT") && request.contentLength().equals("") ||
                request.verb().equals("POST") && request.contentLength().equals("");
    }

}
