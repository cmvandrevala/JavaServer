package http_response;

import utilities.FormattedStrings;

public class Response400 implements HTTPResponse {

    public int statusCode() {
        return 400;
    }

    public String responseString() {
        return "HTTP/1.1 400 Bad Request" + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF;
    }

}
