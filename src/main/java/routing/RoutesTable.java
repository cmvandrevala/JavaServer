package routing;

import http_action.HTTPAction;
import http_action.NullAction;
import http_action.ReadFromTextFileAction;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

public class RoutesTable {

    public enum Verb {
        OPTIONS, GET, HEAD, POST, PUT, DELETE, PATCH
    }

    class Route {
        public String url;
        public Verb verb;
        public HTTPAction action;
        public Hashtable<String,String> data;
        Route(String url, Verb verb, HTTPAction action) {
            this.url = url;
            this.verb = verb;
            this.action = action;
            this.data = new Hashtable<>();
        }
    }

    public ArrayList<Route> routesTable = new ArrayList<>();

    public void syncPublicRoutes(PathToUrlMapper mapper) {
        File[] files = mapper.filesInPublicDirectory();
        for (File file : files) {
            String[] filename = file.getAbsolutePath().split("public");
            addRoute(filename[1], RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        }
    }

    public void addRoute(String url, Verb verb, HTTPAction action) {
        if(routeNotDefinedForURL(url)) {
            routesTable.add(new Route(url, Verb.OPTIONS, new NullAction()));
            routesTable.add(new Route(url, verb, action));
        } else if(!urlHasVerb(url, verb)) {
            routesTable.add(new Route(url, verb, action));
        }
    }

    public void addRoute(String url, Verb verb) {
        if(routeNotDefinedForURL(url)) {
            routesTable.add(new Route(url, Verb.OPTIONS, new NullAction()));
            routesTable.add(new Route(url, verb, new NullAction()));
        } else if(!urlHasVerb(url, verb)) {
            routesTable.add(new Route(url, verb, new NullAction()));
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
