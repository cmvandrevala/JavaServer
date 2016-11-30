package http_response;

import utilities.FormattedStrings;

public class Response405 implements HTTPResponse {

    public int statusCode() {
        return 405;
    }

    public String responseString() {
        return "HTTP/1.1 405 Method Not Allowed" + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF;
    }

}
