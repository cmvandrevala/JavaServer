package http_response;

import http_request.Request;
import utilities.FormattedStrings;

public class PostResponse implements HTTPResponse {

    private Request request;

    public PostResponse(Request request) {
        this.request = request;
    }

    public int statusCode() {
        return 200;
    }

    public String responseString() {
        return "HTTP/1.1 200 OK" + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF;
    }
}