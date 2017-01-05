package routing;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import http_action.HTTPAction;
import http_action.NullAction;
import http_action.ReadFromFileAction;

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
        public String authorizationRealm;
        public Hashtable<String,String> data;
        public String encodedAuthorization;
        Route(String url, Verb verb, HTTPAction action, String authorizationRealm, String username, String password) {
            this.url = url;
            this.verb = verb;
            this.action = action;
            this.authorizationRealm = authorizationRealm;
            String digest = username + ":" + password;
            this.encodedAuthorization = Base64.encode(digest.getBytes());
        }
    }

    public ArrayList<Route> routesTable = new ArrayList<>();

    public void addAuthorizedRoute(String url, Verb verb, HTTPAction action, String authorizationRealm, String username, String password) {
        addRouteToTable(url, verb, action, authorizationRealm, username, password);
    }

    public void addAuthorizedRoute(String url, Verb verb, String authorizationRealm, String username, String password) {
        addRouteToTable(url, verb, new NullAction(), authorizationRealm, username, password);
    }

    public void addRoute(String url, Verb verb, HTTPAction action) {
        addRouteToTable(url, verb, action, "", "", "");
    }

    public void addRoute(String url, Verb verb) {
        addRouteToTable(url, verb, new NullAction(), "", "", "");
    }

    public boolean isAuthorizedRoute(String url, Verb verb) {
        for(Route route : routesTable) {
            if((route.verb == verb) && (route.url.equals(url)) && (!route.authorizationRealm.equals(""))) {
                return true;
            }
        }
        return false;
    }

    public boolean isAuthorizedRoute(String url, String verb) {
        return isAuthorizedRoute(url, Verb.valueOf(verb));
    }

    public String getAuthorization(String url, String verb) {
        return getAuthorization(url, Verb.valueOf(verb));
    }

    public String getAuthorization(String url, Verb verb) {
        for(Route route : routesTable) {
            if(isAuthorizedRoute(url, verb) && (route.verb == verb) && (route.url.equals(url))) {
                return route.encodedAuthorization;
            }
        }
        return "";
    }

    public String getRealm(String url, Verb verb) {
        for(Route route : routesTable) {
            if((route.verb == verb) && (route.url.equals(url))) {
                return route.authorizationRealm;
            }
        }
        return "";
    }

    public String getRealm(String url, String verb) {
        return getRealm(url, Verb.valueOf(verb));
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

    void syncPublicRoutes(PathToUrlMapper mapper) {
        File[] files = mapper.filesInPublicDirectory();
        for (File file : files) {
            String[] filename = file.getAbsolutePath().split("public");
            addRoute(filename[1], RoutesTable.Verb.GET, new ReadFromFileAction(mapper));
        }
    }

    public String[] formattedVerbsForUrl(String url) {
        if(routeNotDefinedForURL(url)) { return new String[0]; }
        ArrayList<Verb> verbs = verbsForUrl(url);
        String[] output = new String[verbs.size()];
        for(int indx = 0; indx < verbs.size(); indx++) {
            output[indx] = verbs.get(indx).name();
        }
        return output;
    }

    private void addRouteToTable(String url, Verb verb, HTTPAction action, String authorizationRealm, String username, String password) {
        if(routeNotDefinedForURL(url)) {
            routesTable.add(new Route(url, Verb.OPTIONS, new NullAction(), authorizationRealm, username, password));
            routesTable.add(new Route(url, verb, action, authorizationRealm, username, password));
        } else if(!urlHasVerb(url, verb)) {
            routesTable.add(new Route(url, verb, action, authorizationRealm, username, password));
        }
    }

    private ArrayList<Verb> verbsForUrl(String url) {
        ArrayList<Verb> verbs = new ArrayList<>();
        for(Route route : routesTable) {
            if(route.url.equals(url)) {
                verbs.add(route.verb);
            }
        }
        return verbs;
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
