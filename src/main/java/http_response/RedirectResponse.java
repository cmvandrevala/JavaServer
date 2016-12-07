package http_response;

import utilities.FormattedStrings;

public class RedirectResponse implements HTTPResponse {

    public String responseString() {
        return "HTTP/1.1 302 Found" + FormattedStrings.CRLF + "Location: localhost:5000" + FormattedStrings.CRLF;
    }

    public int statusCode() {
        return 302;
    }
}
