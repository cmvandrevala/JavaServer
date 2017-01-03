package routing;

import http_request.Request;
import http_response.*;

import java.io.File;

public class Router {

    private PathToUrlMapper mapper;
    private RoutesTable routesTable;
    private DataTable dataTable;

    public Router(PathToUrlMapper mapper, RoutesTable routesTable, DataTable dataTable) {
        this.mapper = mapper;
        this.routesTable = routesTable;
        this.dataTable = dataTable;
    }

    public Router(RoutesTable routesTable, DataTable dataTable) {
        this.mapper = new PathToUrlMapper("public/");
        this.routesTable = routesTable;
        this.dataTable = dataTable;
    }

    public Response route(Request request) {

        routesTable.syncPublicRoutes(mapper);

        if(response418condition(request)) {
            return ResponseBuilder.default418Response();
        } else if(response400condition(request)) {
            return ResponseBuilder.default400Response();
        } else if(response404condition(request)) {
            return ResponseBuilder.default404Response();
        } else if(response405condition(request)) {
            return ResponseBuilder.default405Response();
        } else if(response411condition(request)) {
            return ResponseBuilder.default411Response();
        }

        dataTable.executeAction(request, routesTable);

        ResponseGenerator responseGenerator = new ResponseGenerator(this.routesTable, this.dataTable);

        return responseGenerator.generateResponse(request);

    }

    private boolean response400condition(Request request) {
        return request.isBadRequest();
    }

    private boolean response404condition(Request request) {
        String[] verbList = this.routesTable.formattedVerbsForUrl(request.url());
        return verbList.length == 0 || imageFileDoesNotExist(request);
    }

    private boolean response405condition(Request request) {
        return !this.routesTable.urlHasVerb(request.url(), request.verb());
    }

    private boolean response411condition(Request request) {
        return request.verb().equals("PUT") && request.contentLength().equals("") ||
                request.verb().equals("POST") && request.contentLength().equals("") ||
                request.verb().equals("PATCH") && request.contentLength().equals("");
    }

    private boolean response418condition(Request request) {
        return request.url().equals("/coffee");
    }

    private boolean imageFileDoesNotExist(Request request) {
        File file = mapper.fileCorrespondingToUrl(request.url());
        return !file.exists() && isImageFile(request);
    }

    private boolean isImageFile(Request request) {
        return request.url().contains(".jpeg") ||
                request.url().contains(".png") ||
                request.url().contains(".gif");
    }

}
