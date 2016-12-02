package routing;

import http_request.Request;
import http_response.*;

import java.io.IOException;

public class Router {

    private RoutesTable routesTable = RoutesTable.getInstance();

    public HTTPResponse route(Request request) throws IOException {

        if(response418condition(request)) {
            return new Response418();
        }

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

        routesTable.executeAction(request);

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
                return new PostResponse(request);
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
        String[] verbList = this.routesTable.listVerbsForUrl(request.url());
        return verbList.length == 0;
    }

    private boolean response405condition(Request request) {
        return !this.routesTable.urlHasVerb(request.url(), request.verb());
    }

    private boolean response411condition(Request request) {
        return request.verb().equals("PUT") && request.contentLength().equals("") ||
                request.verb().equals("POST") && request.contentLength().equals("");
    }

    private boolean response418condition(Request request) {
        return request.url().equals("/coffee");
    }

}
