package routing;

import java.util.ArrayList;
import java.util.Hashtable;

class RoutesData {

    private class Route {
        public String url;
        public Hashtable<String,String> data;
        Route(String url) {
            this.url = url;
            this.data = new Hashtable<>();
        }
    }

    private ArrayList<Route> routesList = new ArrayList<>();

    public void add(String url, String dataKey, String dataValue) {
        routesList.add(new Route(url));
        for(Route route : routesList) {
            if(route.url.equals(url)) {
                route.data.put(dataKey, dataValue);
            }
        }
    }

    void removeAll(String url) {
        for(Route route : routesList) {
            if(route.url.equals(url)) {
                route.data = new Hashtable<>();
            }
        }
    }

    String retrieve(String url, String dataKey) {
        for(Route route : routesList) {
            if(route.url.equals(url) && route.data.containsKey(dataKey)) {
                return route.data.get(dataKey);
            }
        }
        return "";
    }
}
