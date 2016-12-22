package routing;

import http_request.Request;
import http_response.Response;
import http_response.ResponseBuilder;

import java.util.ArrayList;
import java.util.Hashtable;

public class DataTable {

    private class Route {
        public final String url;
        public Hashtable<String,String> data;
        Route(String url) {
            this.url = url;
            this.data = new Hashtable<>();
        }
    }

    private final ArrayList<Route> dataTable = new ArrayList<>();

    public void addData(String url, String dataKey, String dataValue) {
        addRoute(url);
        for(Route route : dataTable) {
            if(route.url.equals(url)) {
                route.data.put(dataKey, dataValue);
            }
        }
    }

    public void removeAllData(String url) {
        for(Route route : dataTable) {
            if(route.url.equals(url)) {
                route.data = new Hashtable<>();
            }
        }
    }

    public void executeAction(Request request, RoutesTable routesTable) {
        for(RoutesTable.Route route : routesTable.routesTable) {
            if((route.verb == RoutesTable.Verb.valueOf(request.verb())) && (route.url.equals(request.url()))) {
                route.action.execute(request, this);
            }
        }
    }

    Response generateResponse(Request request, RoutesTable routesTable) {
        if(request.verb().equals("OPTIONS")) {
            return optionsResponse(request, routesTable);
        } else if(request.verb().equals("PATCH")) {
            return patchResponse(request);
        } else if(retrieveData(request.url(), "Location").equals("")) {
            return generalResponse(request);
        } else {
            return redirectResponse(request);
        }
    }

    public String retrieveData(String url, String dataKey) {
        for(Route route : dataTable) {
            if(route.url.equals(url) && route.data.containsKey(dataKey)) {
                return route.data.get(dataKey);
            }
        }
        return "";
    }

    public void addRoute(String url) {
        if(routeNotDefinedForURL(url)) {
            dataTable.add(new Route(url));
        }
    }

    private Response optionsResponse(Request request, RoutesTable routesTable) {
        ResponseBuilder builder = new ResponseBuilder();
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (String v : routesTable.formattedVerbsForUrl(request.url())) {
            sb.append(delimiter).append(v);
            delimiter = ",";
        }
        builder.addProtocol("HTTP/1.1").addStatusCode(200).addStatusMessage("OK").addAllow(sb.toString());
        return builder.build();
    }

    private Response patchResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        String eTag = retrieveData(request.url(), "ETag");
        builder.addProtocol("HTTP/1.1").addStatusCode(204).addStatusMessage("No Content");
        builder.addETag(eTag).addContentLocation("/patch-content.txt");
        return builder.build();
    }

    private Response generalResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        String body = retrieveData(request.url(), "Body");
        String setCookie = retrieveData(request.url(), "Set-Cookie");
        String eTag = retrieveData(request.url(), "ETag");
        String cType = contentType(request);
        builder.addProtocol("HTTP/1.1").addStatusCode(200).addStatusMessage("OK");
        builder.addSetCookie(setCookie).addContentType(cType).addETag(eTag).addBody(body);
        return builder.build();
    }

    private Response redirectResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        String location = retrieveData(request.url(), "Location");
        builder.addProtocol("HTTP/1.1").addStatusCode(302).addStatusMessage("Found").addLocation(location);
        return builder.build();
    }

    private boolean routeNotDefinedForURL(String url) {
        for(Route route : dataTable) {
            if(route.url.equals(url)) {
                return false;
            }
        }
        return true;
    }

    private String contentType(Request request) {
        String[] contentAndExtension = request.url().split("\\.");

        if(contentAndExtension.length < 2) {
            return "text/html";
        }

        switch(contentAndExtension[1]) {
            case "txt":
                return "text/plain";
            case "jpeg":
                return "image/jpeg";
            case "jpg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            default:
                return "text/html";
        }
    }

}
