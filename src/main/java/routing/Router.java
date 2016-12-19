package routing;

import http_request.Request;
import http_response.*;

public class Router {

    private RoutesTable routesTable;
    private DataTable dataTable;

    public Router(RoutesTable routesTable, DataTable dataTable) {
        this.routesTable = routesTable;
        this.dataTable = dataTable;
    }

    public Response route(Request request) {

        if(response418condition(request)) {
            return ResponseBuilder.default418Response();
        }

        if(response400condition(request)) {
            return ResponseBuilder.default400Response();
        }

        if(response404condition(request)) {
            return ResponseBuilder.default404Response();
        }

        if(response405condition(request)) {
            return ResponseBuilder.default405Response();
        }

        if(response411condition(request)) {
            return ResponseBuilder.default411Response();
        }

        dataTable.executeAction(request, routesTable);

        return dataTable.generateResponse(request, routesTable);

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
