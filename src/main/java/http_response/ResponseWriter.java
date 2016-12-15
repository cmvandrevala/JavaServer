package http_response;

import utilities.FormattedStrings;

public class ResponseWriter {

    public String writeHttpResponse(Response response) {
        if(responseIsMissingStatusCode(response)) {
            return default400Response;
        } else if(responseRedirectsToAnotherUrl(response)) {
            return redirectResponse(response);
        } else if(responseReturnsCookie(response)) {
            return responseWithCookieHeader(response);
        } else if(responseReturnsOptions(response)) {
            return responseWithOptions(response);
        } else {
            return formattedResponse(response);
        }
    }

    private boolean responseIsMissingStatusCode(Response response) {
        return response.statusCode().equals("");
    }

    private String default400Response = "HTTP/1.1 400 Bad Request" + FormattedStrings.CRLF +
                                        "Content-Type: text/html" + FormattedStrings.CRLF +
                                        "Content-Length: 0" + FormattedStrings.CRLF +
                                        "Connection: close" + FormattedStrings.CRLF;

    private boolean responseRedirectsToAnotherUrl(Response response) {
        return !response.location().equals("");
    }

    private String redirectResponse(Response response) {
        return "HTTP/1.1 302 Found" + FormattedStrings.CRLF + "Location: " + response.location();
    }

    private boolean responseReturnsCookie(Response response) {
        return !response.setCookie().equals("");
    }

    private String responseWithCookieHeader(Response response) {
        if(response.body().equals("")) {
            return cookieResponseHeader(response);
        } else {
            return cookieResponseHeader(response) + FormattedStrings.CRLF + response.body();
        }
    }

    private boolean responseReturnsOptions(Response response) {
        return !response.allow().equals("");
    }

    private String responseWithOptions(Response response) {
        return "HTTP/1.1 200 OK" + FormattedStrings.CRLF +
                "Allow: " + response.allow() + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF;
    }

    private String formattedResponse(Response response) {
        if(response.body().equals("")) {
            return defaultResponseHeader(response);
        } else {
            return defaultResponseHeader(response) + FormattedStrings.CRLF + response.body();
        }
    }

    private String defaultResponseHeader(Response response) {
        return response.protocol() + " " + response.statusCode() + " " + response.statusMessage() + FormattedStrings.CRLF +
                "Content-Type: " + response.contentType() + FormattedStrings.CRLF +
                "Content-Length: " + response.contentLength() + FormattedStrings.CRLF +
                "Connection: " + response.connection() + FormattedStrings.CRLF;
    }

    private String cookieResponseHeader(Response response) {
        return response.protocol() + " " + response.statusCode() + " " + response.statusMessage() + FormattedStrings.CRLF +
                "Content-Type: " + response.contentType() + FormattedStrings.CRLF +
                "Set-Cookie: " + response.setCookie() + FormattedStrings.CRLF +
                "Content-Length: " + response.contentLength() + FormattedStrings.CRLF +
                "Connection: " + response.connection() + FormattedStrings.CRLF;
    }
}