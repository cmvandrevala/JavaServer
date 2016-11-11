package http_request;

import http_response.HTTPResponse;

import java.util.Hashtable;

public class Router {

    public HTTPResponse route(HTTPRequest request) {
        if (request.verb().equals("HEAD")) { return head(request.url()); }
        if (request.verb().equals("GET")) { return get(request.url()); }
        return defaultResponse();
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

    private HTTPResponse defaultResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        HTTPResponse response = new HTTPResponse(params);
        return response;
    }

}

