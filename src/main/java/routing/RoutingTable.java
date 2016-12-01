package routing;

import java.util.ArrayList;
import java.util.Hashtable;

public class RoutingTable {

    private enum Verb {
        OPTIONS, GET, HEAD, POST, PUT, DELETE
    }

    private static RoutingTable instance = null;
    private Hashtable<String,ArrayList<Verb>> routesTable = new Hashtable<String, ArrayList<Verb>>();

    protected RoutingTable() {}

    public static RoutingTable getInstance() {
        if(instance == null) {
            instance = new RoutingTable();
        }
        return instance;
    }

    public void addRoute(String url, String verb) {
        if(!validVerb(verb)) {
            return;
        }
        if(routeNotDefinedForURL(url)) {
            ArrayList<Verb> newVerbList = new ArrayList<Verb>();
            newVerbList.add(Verb.OPTIONS);
            newVerbList.add(Verb.valueOf(verb));
            routesTable.put(url, newVerbList);
        } else if(!verbAlreadyDefinedForURL(url, verb)) {
            ArrayList<Verb> currentVerbList = this.routesTable.get(url);
            currentVerbList.add(Verb.valueOf(verb));
        }
    }

    public String[] listRoutesForUrl(String url) {
        if(routeNotDefinedForURL(url)) {
            return new String[0];
        } else {
            ArrayList<Verb> routes = routesTable.get(url);
            String[] output = new String[routes.size()];
            for(int i = 0; i < routes.size(); i++) {
                output[i] = routes.get(i).name();
            }
            return output;
        }
    }

    boolean urlHasVerb(String url, String verb) {
        if(validVerb(verb)) {
            return routesTable.get(url).contains(Verb.valueOf(verb));
        } else {
            return false;
        }
    }

    public void clearData() {
        routesTable = new Hashtable<String, ArrayList<Verb>>();
    }

    private boolean routeNotDefinedForURL(String url) {
        return this.routesTable.get(url) == null;
    }

    private boolean verbAlreadyDefinedForURL(String url, String verb) {
        return this.routesTable.get(url).contains(Verb.valueOf(verb));
    }

    private boolean validVerb(String route) {
        Verb[] verbs = Verb.values();
        for(Verb verb : verbs) {
            if(route.equals(verb.name())) {
                return true;
            }
        }
        return false;
    }

}
