package routing;

import http_request.HTTPRequest;
import http_response.HTTPResponse;

import java.io.*;
import java.util.Hashtable;

public class Router {

    private RoutingTable routingTable;

    public Router(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    public HTTPResponse route(HTTPRequest request) throws IOException {

        String verb = request.verb();
        String url = request.url();
        String[] verbList = routingTable.listRoutesForUrl(url);

        if(request.isBadRequest()) {
            return response400();
        }

        if(verbList.length == 0) {
            return response404();
        }

        if(!routingTable.urlHasVerb(url, verb)) {
            return response405();
        }

        if(verb.equals("PUT") && request.contentLength().equals("")) {
            return response411();
        }

        if (verb.equals("HEAD")) {
            return head();
        } else if (verb.equals("GET")) {
            return get(url);
        } else if (verb.equals("OPTIONS")) {
            return options(url);
        } else if (verb.equals("PUT")) {
            return put(request);
        } else {
            return post();
        }

    }

    private HTTPResponse response400() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "400");
        params.put("Message", "Bad Request");
        return new HTTPResponse(params);
    }

    private HTTPResponse response404() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "404");
        params.put("Message", "Not Found");
        return new HTTPResponse(params);
    }

    private HTTPResponse response405() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "405");
        params.put("Message", "Method Not Allowed");
        return new HTTPResponse(params);
    }

    private HTTPResponse response411() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "411");
        params.put("Message", "Length Required");
        return new HTTPResponse(params);
    }

    private HTTPResponse head() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        return new HTTPResponse(params);
    }

    private HTTPResponse get(String url) throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        File file = new PathToUrlMapper().fileCorrespondingToUrl(url);
        if(file.exists()) { params.put("Body", readFile(file.getAbsolutePath())); }
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        return new HTTPResponse(params);
    }

    private HTTPResponse options(String url) {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Allow", availableVerbs(url));
        return new HTTPResponse(params);
    }

    private HTTPResponse put(HTTPRequest request) throws IOException {
        File file = new PathToUrlMapper().fileCorrespondingToUrl(request.url());
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
        out.println("<p>" + request.body() + "</p>");
        out.close();
        return head();
    }

    private HTTPResponse post() {
        return head();
    }

    private String availableVerbs(String url) {
        String[] verbs = routingTable.listRoutesForUrl(url);
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (String verb : verbs) {
            sb.append(delimiter).append(verb);
            delimiter = ",";
        }
        return sb.toString();
    }

    private String readFile(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String str;
        while ((str = in.readLine()) != null) {
            contentBuilder.append(str);
        }
        in.close();
        return contentBuilder.toString();
    }
}
