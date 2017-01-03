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

    public String retrieveData(String url, String dataKey) {
        for(Route route : dataTable) {
            if(route.url.equals(url) && route.data.containsKey(dataKey)) {
                return route.data.get(dataKey);
            }
        }
        return "";
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
