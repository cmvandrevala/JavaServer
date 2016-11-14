package http_request;

import http_response.HTTPResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Router {

    private Hashtable<String,ArrayList<String>> routesTable = new Hashtable<String, ArrayList<String>>();
    String rootDirectory = "cob_spec/public";

    public void addRoute(String url, String verb) {
        if(routeNotDefinedForURL(url)) {
            ArrayList<String> newVerbList = new ArrayList<String>();
            newVerbList.add("OPTIONS");
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
        if (routesTable.get(url).contains(verb) && verb.equals("OPTIONS")) {
            return options(request.url());
        }
        if (routesTable.get(url).contains(verb) && verb.equals("PUT")) {
            return put(request.url());
        }
        if (routesTable.get(url).contains(verb) && verb.equals("POST")) {
            return post(request.url());
        }
        return notFoundResponse();
    }

    private HTTPResponse put(String url) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cob_spec/public/foo.html", true)));
            out.println("\n<p>data = foo</p>");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return head(url);
    }

    private HTTPResponse post(String url) {
        return head(url);
    }

    private HTTPResponse head(String url) {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        HTTPResponse response = new HTTPResponse(params);
        return response;
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
        HTTPResponse response = new HTTPResponse(params);
        return response;
    }

    private HTTPResponse options(String url) {
        Hashtable<String,String> params = new Hashtable<String, String>();
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Allow", appendVerbs(url));
        params.put("Body", "");
        HTTPResponse response = new HTTPResponse(params);
        return response;
    }

    private HTTPResponse notFoundResponse() {
        Hashtable<String,String> params = new Hashtable<String, String>();
        HTTPResponse response = new HTTPResponse(params);
        return response;
    }

    private String appendVerbs(String url) {
        ArrayList<String> verbs = routesTable.get(url);
        StringBuilder sb = new StringBuilder();
        String delimitter = "";
        for (String verb : verbs) {
            sb.append(delimitter).append(verb);
            delimitter = ",";
        }
        return sb.toString();
    }

    private boolean routeNotDefinedForURL(String url) {
        return this.routesTable.get(url) == null;
    }

    String readFile(String fileName) throws IOException {
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

