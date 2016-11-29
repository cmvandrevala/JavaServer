package routing;

import http_request.Request;
import http_response.*;

import java.io.*;
import java.util.Hashtable;

public class Router {

    private RoutingTable routingTable;

    public Router(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    public HTTPResponse route(Request request) throws IOException {

        String verb = request.verb();
        String url = request.url();
        String[] verbList = routingTable.listRoutesForUrl(url);

        if(request.isBadRequest()) {
            return new Response400();
        }

        if(verbList.length == 0) {
            return new Response404();
        }

        if(!routingTable.urlHasVerb(url, verb)) {
            return new Response405();
        }

        if(verb.equals("PUT") && request.contentLength().equals("")) {
            return new Response411();
        }

        switch (verb) {
            case "HEAD":
                return new HeadResponse();
            case "GET":
                return get(url);
            case "OPTIONS":
                return options(url);
            case "PUT":
                return put(request);
            default:
                return post();
        }

    }

    private Response get(String url) throws IOException {
        Hashtable<String,String> params = new Hashtable<String, String>();
        File file = new PathToUrlMapper().fileCorrespondingToUrl(url);
        if(file.exists()) { params.put("Body", readFile(file.getAbsolutePath())); }
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        return new Response(params);
    }

    private Response options(String url) {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Allow", availableVerbs(url));
        return new Response(params);
    }

    private HTTPResponse put(Request request) throws IOException {
        File file = new PathToUrlMapper().fileCorrespondingToUrl(request.url());
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
        out.println("<p>" + request.body() + "</p>");
        out.close();
        return new HeadResponse();
    }

    private HTTPResponse post() {
        return new HeadResponse();
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
