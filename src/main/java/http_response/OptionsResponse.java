package http_response;

import http_request.Request;
import routing.RoutesTable;
import utilities.FormattedStrings;

public class OptionsResponse implements HTTPResponse {

    private Request request;
    private RoutesTable routesTable;

    public OptionsResponse(Request request, RoutesTable routesTable) {
        this.request = request;
        this.routesTable = routesTable;
    }

    public int statusCode() {
        return 200;
    }

    public String responseString() {
        return "HTTP/1.1 200 OK" + FormattedStrings.CRLF +
                "Allow: " + availableVerbs() + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF;
    }

    private String availableVerbs() {
        String[] verbs = routesTable.listVerbsForUrl(request.url());
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (String verb : verbs) {
            sb.append(delimiter).append(verb);
            delimiter = ",";
        }
        return sb.toString();
    }

}
