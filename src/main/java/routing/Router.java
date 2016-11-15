package routing;

import http_request.HTTPRequest;
import http_response.HTTPResponse;

import java.io.*;
import java.util.Hashtable;

public class Router {

    private RoutingTable routingTable;
    private String rootDirectory;

    public Router(RoutingTable routingTable) {
        this.routingTable = routingTable;
        this.rootDirectory = "cob_spec/public";
    }

    public HTTPResponse route(HTTPRequest request) {
        String verb = request.verb();
        String url = request.url();
        String[] verbList = routingTable.listRoutesForUrl(url);

        if(verbList.length == 0) {
            return notFoundResponse();
        }

        if(!routingTable.urlHasVerb(url, verb)) {
            return fourOhFiveResponse();
        }

        if (verb.equals("HEAD")) {
            return head();
        } else if (verb.equals("GET")) {
            return get(url);
        } else if (verb.equals("OPTIONS")) {
            return options(url);
        } else if ( verb.equals("PUT")) {
            return put();
        } else {
            return post();
        }
    }

    private HTTPResponse fourOhFiveResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "405");
        params.put("Message", "Method Not Allowed");
        return new HTTPResponse(params);
    }

    private HTTPResponse put() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cob_spec/public/foo.html", true)));
            out.println("\n<p>data = foo</p>");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return head();
    }

    private HTTPResponse post() {
        return head();
    }

    private HTTPResponse head() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        return new HTTPResponse(params);
    }

    private HTTPResponse get(String url) {
        Hashtable<String,String> params = new Hashtable<String, String>();
        String path;
        if(url.equals("/")) {
            path = this.rootDirectory + "/index.html";
        } else {
            File filenameWithHTMLEnding = new File(this.rootDirectory + "/" + url.substring(1) + ".html");
            if(filenameWithHTMLEnding.exists()) {
                path = this.rootDirectory + "/" + url.substring(1) + ".html";
            } else {
                path = this.rootDirectory + "/" + url.substring(1);
            }
        }
        try {
            String body = readFile(path);
            params.put("Body", body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        params.put("Status-Code", "200");
        params.put("Message", "OK");
        return new HTTPResponse(params);
    }

    private HTTPResponse options(String url) {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Allow", appendVerbs(url));
        params.put("Body", "");
        return new HTTPResponse(params);
    }

    private HTTPResponse notFoundResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        return new HTTPResponse(params);
    }

    private String appendVerbs(String url) {
        String[] verbs = routingTable.listRoutesForUrl(url);
        StringBuilder sb = new StringBuilder();
        String delimitter = "";
        for (String verb : verbs) {
            sb.append(delimitter).append(verb);
            delimitter = ",";
        }
        return sb.toString();
    }

    private String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\r\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}

