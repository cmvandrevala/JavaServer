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

    public Response executeAction(Request request, RoutesTable routesTable) {
        ResponseBuilder builder = new ResponseBuilder();
        String url = request.url();
        String verb = request.verb();
        for(RoutesTable.Route route : routesTable.routesTable) {
            if((route.verb == RoutesTable.Verb.valueOf(verb)) && (route.url.equals(url))) {
                route.action.execute(request, this);
            }
        }
        if(request.verb().equals("OPTIONS")) {
            StringBuilder sb = new StringBuilder();
            String delimiter = "";
            for (String v : routesTable.listVerbsForUrl(request.url())) {
                sb.append(delimiter).append(v);
                delimiter = ",";
            }
            builder.addProtocol("HTTP/1.1").addStatusCode("200").addStatusMessage("OK").addAllow(sb.toString());
            return builder.build();
        }
        if(retrieveData(request.url(), "Location").equals("")) {
            String body = retrieveData(request.url(), "Body");
            String setCookie = retrieveData(request.url(), "Set-Cookie");
            String cType = contentType(request);
            builder.addProtocol("HTTP/1.1").addStatusCode("200").addStatusMessage("OK").addSetCookie(setCookie).addContentType(cType).addBody(body);
            return builder.build();
        } else {
            String location = retrieveData(request.url(), "Location");
            builder.addProtocol("HTTP/1.1").addStatusCode("302").addStatusMessage("Found").addLocation(location);
            return builder.build();
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
