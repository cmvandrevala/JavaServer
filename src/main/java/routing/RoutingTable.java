package routing;

import http_action.HTTPAction;
import http_action.NullAction;
import http_request.Request;

import java.util.ArrayList;

public class RoutingTable {

    public enum Verb {
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

    public void addRoute(String url, Verb verb, HTTPAction action) {
        if(routeNotDefinedForURL(url)) {
            routesTable.add(new Route(url, Verb.OPTIONS, new NullAction()));
            routesTable.add(new Route(url, verb, action));
        } else if(!urlHasVerb(url, verb)) {
            routesTable.add(new Route(url, verb, action));
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

    public void clearData() {
        routesTable = new ArrayList<>();
    }

    void executeAction(Request request) {
        String url = request.url();
        Verb verb = Verb.valueOf(request.verb());
        for(Route route : routesTable) {
            if((route.verb == verb) && (route.url.equals(url))) {
                route.action.execute(request);
            }
        }
    }

    boolean urlHasVerb(String url, String verb) {
        for(Verb acceptedVerb : Verb.values()) {
            if(acceptedVerb.name().equals(verb)) {
                return urlHasVerb(url, Verb.valueOf(verb));
            }
        }
        return false;
    }

    boolean urlHasVerb(String url, Verb verb) {
        for(Route route : routesTable) {
            if((route.verb == verb) && (route.url.equals(url))) {
                return true;
            }
        }
        return false;
    }

    private boolean routeNotDefinedForURL(String url) {
        for(Route route : routesTable) {
            if(route.url.equals(url)) {
                return false;
            }
        }
        return true;
    }

}
