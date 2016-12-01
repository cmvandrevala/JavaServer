package http_response;

import http_request.Request;
import routing.RoutingTable;
import utilities.FormattedStrings;

public class OptionsResponse implements HTTPResponse {

    private Request request;
    private RoutingTable routingTable = RoutingTable.getInstance();

    public OptionsResponse(Request request) {
        this.request = request;
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
        String[] verbs = routingTable.listVerbsForUrl(request.url());
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (String verb : verbs) {
            sb.append(delimiter).append(verb);
            delimiter = ",";
        }
        return sb.toString();
    }

}
