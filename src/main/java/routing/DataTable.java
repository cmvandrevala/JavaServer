package routing;

import http_request.Request;
import http_response.Response;
import http_response.ResponseBuilder;
import utilities.FormattedStrings;

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

    public synchronized void executeAction(Request request, RoutesTable routesTable) {
        for(RoutesTable.Route route : routesTable.routesTable) {
            if((route.verb == RoutesTable.Verb.valueOf(request.verb())) && (route.url.equals(request.url()))) {
                route.action.execute(request, this);
            }
        }
    }

    synchronized Response generateResponse(Request request, RoutesTable routesTable) {
        if(!request.range().equals("")) {
            return rangeResponse(request);
        } else if(request.verb().equals("OPTIONS")) {
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

    String partialContent(Request request) {
        if(request.range().equals("")) {
            return fullBody(request.url());
        } else if(bounds(request)[0] > 0) {
            return fullBody(request.url()).substring(bounds(request)[0], bounds(request)[1]);
        } else {
            return fullBody(request.url()).substring(bounds(request)[0], bounds(request)[1]);
        }
    }

    private int[] bounds(Request request) {
        String[] range = request.range().split("=");
        String[] limits = range[1].split("-");
        int lowerBound = 0;
        int upperBound = fullBody(request.url()).length();

        if(containsLowerBound(request) && containsUpperBound(request)) {
            lowerBound = Integer.parseInt(limits[0]);
            upperBound = Integer.parseInt(limits[1]) + 1;
        } else if(containsLowerBound(request) && !containsUpperBound(request)) {
            lowerBound = Integer.parseInt(limits[0]);
        } else {
            lowerBound = upperBound - Integer.parseInt(limits[1]);
        }

        int[] boundsArray = {lowerBound, upperBound};
        return boundsArray;
    }

    private boolean containsLowerBound(Request request) {
        String[] range = request.range().split("=");
        String[] limits = range[1].split("-");
        return !limits[0].equals("");
    }

    private boolean containsUpperBound(Request request) {
        String[] range = request.range().split("=");
        String[] limits = range[1].split("-");
        return limits.length > 1;
    }

    private Response rangeResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addProtocol("HTTP/1.1").addStatusCode(206).addStatusMessage("Partial Content");
        builder.addContentType(contentType(request)).addBody(partialContent(request));
        if(containsUpperBound(request) && !containsLowerBound(request)) {
            int adjustedLowerBound = bounds(request)[0] + 1;
            builder.addContentRange("bytes " + adjustedLowerBound + "-" + bounds(request)[1] + "/" + fullBody(request.url()).length());
        } else {
            builder.addContentRange("bytes " + bounds(request)[0] + "-" + bounds(request)[1] + "/" + fullBody(request.url()).length());
        }
        return builder.build();
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
        builder.addProtocol("HTTP/1.1").addStatusCode(204).addStatusMessage("No Content");
        builder.addETag(eTag(request.url())).addContentLocation("/patch-content.txt");
        return builder.build();
    }

    private Response generalResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        String setCookie = retrieveData(request.url(), "Set-Cookie");
        builder.addProtocol("HTTP/1.1").addStatusCode(200).addStatusMessage("OK");
        builder.addSetCookie(setCookie).addContentType(contentType(request));
        builder.addETag(eTag(request.url())).addBody(fullBody(request.url()));
        return builder.build();
    }

    private Response redirectResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        String location = retrieveData(request.url(), "Location");
        builder.addProtocol("HTTP/1.1").addStatusCode(302);
        builder.addStatusMessage("Found").addLocation(location);
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

    private String fullBody(String url) {
        return retrieveData(url, "Body");
    }

    private String eTag(String url) {
        return retrieveData(url, "ETag");
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
