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

    public void addBody(String url, String body) {
        addData(url, "Body", body);
    }

    public String retrieveBody(String url) {
        return retrieveData(url, "Body");
    }

    public void addETag(String url, String eTag) {
        addData(url, "ETag", eTag);
    }

    public String retrieveETag(String url) {
        return retrieveData(url, "ETag");
    }

    public void addLocation(String url, String location) {
        addData(url, "Location", location);
    }

    public String retrieveLocation(String url) {
        return retrieveData(url, "Location");
    }

    public void addSetCookie(String url, String cookie) {
        addData(url, "Set-Cookie", cookie);
    }

    public String retrieveSetCookie(String url) {
        return retrieveData(url, "Set-Cookie");
    }

    public void addCustomData(String url, String key, String value) {
        addData(url, key, value);
    }

    public String retrieveCustomData(String url, String key) {
        return retrieveData(url, key);
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

    private String retrieveData(String url, String dataKey) {
        for(Route route : dataTable) {
            if(route.url.equals(url) && route.data.containsKey(dataKey)) {
                return route.data.get(dataKey);
            }
        }
        return "";
    }

    private void addData(String url, String dataKey, String dataValue) {
        addRoute(url);
        for(Route route : dataTable) {
            if(route.url.equals(url)) {
                route.data.put(dataKey, dataValue);
            }
        }
    }

    private void addRoute(String url) {
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

}
