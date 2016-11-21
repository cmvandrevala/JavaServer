package routing;

import java.util.ArrayList;
import java.util.Hashtable;

public class RoutingTable {

    private static RoutingTable instance = null;
    private Hashtable<String,ArrayList<String>> routesTable = new Hashtable<String, ArrayList<String>>();

    protected RoutingTable() {}

    public static RoutingTable getInstance() {
        if(instance == null) {
            instance = new RoutingTable();
        }
        return instance;
    }

    public void addRoute(String url, String verb) {
        if(routeNotDefinedForURL(url)) {
            ArrayList<String> newVerbList = new ArrayList<String>();
            newVerbList.add("OPTIONS");
            newVerbList.add(verb);
            routesTable.put(url, newVerbList);
        } else if(!verbAlreadyDefinedForURL(url, verb)) {
            ArrayList<String> currentVerbList = this.routesTable.get(url);
            currentVerbList.add(verb);
        }
    }

    String[] listRoutesForUrl(String url) {
        if(routeNotDefinedForURL(url)) {
            return new String[0];
        } else {
            ArrayList<String> routes = routesTable.get(url);
            String[] output = new String[routes.size()];
            return routes.toArray(output);
        }
    }

    boolean urlHasVerb(String url, String verb) {
        return routesTable.get(url).contains(verb);
    }

    void clearData() {
        routesTable = new Hashtable<String, ArrayList<String>>();
    }

    private boolean routeNotDefinedForURL(String url) {
        return this.routesTable.get(url) == null;
    }

    private boolean verbAlreadyDefinedForURL(String url, String verb) {
        return this.routesTable.get(url).contains(verb);
    }

}
