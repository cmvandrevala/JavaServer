package routing;

import java.util.ArrayList;

public class RoutingTable {

    private enum Verb {
        OPTIONS, GET, HEAD, POST, PUT, DELETE
    }

    private class Route {
        public String url;
        public Verb verb;
        public HTTPAction action;
        Route(String url, Verb verb, HTTPAction action) {
            this.url = url;
            this.verb = verb;
            this.action = action;
        }
    }

    private static RoutingTable instance = null;
    private ArrayList<Route> routesTable = new ArrayList<>();

    protected RoutingTable() {}

    public static RoutingTable getInstance() {
        if(instance == null) { instance = new RoutingTable(); }
        return instance;
    }

    public void addRoute(String url, String verb, HTTPAction action) {
        if(!validVerb(verb)) {
            return;
        }
        if(routeNotDefinedForURL(url)) {
            routesTable.add(new Route(url, Verb.OPTIONS, new DummyAction()));
            routesTable.add(new Route(url, Verb.valueOf(verb), action));
        } else if(!verbAlreadyDefinedForURL(url, verb)) {
            routesTable.add(new Route(url, Verb.valueOf(verb), action));
        }
    }

    public String[] listVerbsForUrl(String url) {
        if(routeNotDefinedForURL(url)) {
            return new String[0];
        } else {
            ArrayList<Verb> verbs = new ArrayList<>();
            for(Route route : routesTable) {
                if(route.url.equals(url)) {
                    verbs.add(route.verb);
                }
            }
            String[] output = new String[verbs.size()];
            for(int i = 0; i < verbs.size(); i++) {
                output[i] = verbs.get(i).name();
            }
            return output;
        }
    }

    boolean urlHasVerb(String url, String verb) {
        if(!validVerb(verb)) {
            return false;
        }
        for(Route route : routesTable) {
            if((route.verb.name().equals(verb)) && (route.url.equals(url))) {
                return true;
            }
        }
        return false;
    }

    public void clearData() {
        routesTable = new ArrayList<>();
    }

    private boolean routeNotDefinedForURL(String url) {
        for(Route route : routesTable) {
            if(route.url.equals(url)) {
                return false;
            }
        }
        return true;
    }

    private boolean verbAlreadyDefinedForURL(String url, String verb) {
        for(Route route : routesTable) {
            if((route.verb.name().equals(verb)) && (route.url.equals(url))) {
                return true;
            }
        }
        return false;
    }

    private boolean validVerb(String verb) {
        Verb[] verbs = Verb.values();
        for(Verb testVerb : verbs) {
            if(verb.equals(testVerb.name())) {
                return true;
            }
        }
        return false;
    }

}
