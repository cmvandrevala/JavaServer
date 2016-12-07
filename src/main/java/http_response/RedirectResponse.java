package http_response;

import utilities.FormattedStrings;

public class RedirectResponse implements HTTPResponse {

    private String redirectURL;

    public RedirectResponse() {
        this.redirectURL = "http://localhost:5000/";
    }

    public RedirectResponse(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String responseString() {
        return "HTTP/1.1 302 Found" + FormattedStrings.CRLF + "Location: " + this.redirectURL + FormattedStrings.CRLF;
    }

    public int statusCode() {
        return 302;
    }
}
