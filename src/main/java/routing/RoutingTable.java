package routing;

import java.util.ArrayList;
import java.util.Hashtable;

public class RoutingTable {

    private Hashtable<String,ArrayList<String>> routesTable = new Hashtable<String, ArrayList<String>>();

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

    public String[] listRoutesForUrl(String url) {
        if(routeNotDefinedForURL(url)) {
            return new String[0];
        } else {
            ArrayList<String> routes = routesTable.get(url);
            String[] output = new String[routes.size()];
            return routes.toArray(output);
        }
    }

    private boolean routeNotDefinedForURL(String url) {
        return this.routesTable.get(url) == null;
    }

    private boolean verbAlreadyDefinedForURL(String url, String verb) {
        return this.routesTable.get(url).contains(verb);
    }

}
