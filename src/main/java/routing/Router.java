package routing;

import http_request.Request;
import http_response.HTTPResponse;
import http_response.Response;
import http_response.Response400;
import http_response.Response404;

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

    private Response response405() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "405");
        params.put("Message", "Method Not Allowed");
        return new Response(params);
    }

    private Response response411() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "411");
        params.put("Message", "Length Required");
        return new Response(params);
    }

    private Response head() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        return new Response(params);
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

    private Response put(Request request) throws IOException {
        File file = new PathToUrlMapper().fileCorrespondingToUrl(request.url());
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
        out.println("<p>" + request.body() + "</p>");
        out.close();
        return head();
    }

    private Response post() {
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
