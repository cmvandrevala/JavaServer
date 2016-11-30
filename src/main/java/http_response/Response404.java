package http_response;

import utilities.FormattedStrings;

public class Response404 implements HTTPResponse {

    public int statusCode() {
        return 404;
    }

    public String responseString() {
        return "HTTP/1.1 404 Not Found" + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF;
    }

}
