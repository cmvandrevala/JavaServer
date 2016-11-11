package http_request;

import http_response.HTTPResponse;

import java.util.ArrayList;
import java.util.Hashtable;

public class Router {

    private Hashtable<String,ArrayList<String>> routesTable = new Hashtable<String, ArrayList<String>>();

    public void addRoute(String url, String verb) {
        if(routeNotDefinedForURL(url)) {
            ArrayList<String> newVerbList = new ArrayList<String>();
            newVerbList.add(verb);
            routesTable.put(url, newVerbList);
        } else {
            ArrayList<String> currentVerbList = this.routesTable.get(url);
            currentVerbList.add(verb);
        }
    }

    public HTTPResponse route(HTTPRequest request) {
        String verb = request.verb();
        String url = request.url();
        if(routesTable.get(url) == null) {
            return notFoundResponse();
        }
        if (routesTable.get(url).contains(verb) && verb.equals("HEAD")) {
            return head(request.url());
        }
        if (routesTable.get(url).contains(verb) && verb.equals("GET")) {
            return get(request.url());
        }
        return notFoundResponse();
    }

    private HTTPResponse head(String url) {
        Hashtable<String,String> params = new Hashtable<String, String>();
        if(url.equals("/") || url.equals("/foo")) {
            params.put("Status-Code", "200");
            params.put("Message", "OK");
        }
        HTTPResponse response = new HTTPResponse(params);
        return response;
    }

    private HTTPResponse get(String url) {
        Hashtable<String,String> params = new Hashtable<String, String>();
        if(url.equals("/")) {
            params.put("Status-Code", "200");
            params.put("Message", "OK");
            params.put("Body", "<h1>Hello World!</h1>");
        }
        if(url.equals("/foo")) {
            params.put("Status-Code", "200");
            params.put("Message", "OK");
            params.put("Body", "foo");
        }
        HTTPResponse response = new HTTPResponse(params);
        return response;
    }

    private HTTPResponse notFoundResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        HTTPResponse response = new HTTPResponse(params);
        return response;
    }

    private boolean routeNotDefinedForURL(String url) {
        return this.routesTable.get(url) == null;
    }

}

