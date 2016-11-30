package http_response;

import utilities.FormattedStrings;

public class Response411 implements HTTPResponse {

    public int statusCode() {
        return 411;
    }

    public String responseString() {
        return "HTTP/1.1 411 Length Required" + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF;
    }


}
