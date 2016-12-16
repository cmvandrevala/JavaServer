package http_response;

import utilities.FormattedStrings;

public class ResponseWriter {

    public String writeHttpResponse(Response response) {
        if(responseReturnsOptions(response)) {
            return responseWithOptions(response);
        } else {
            return formattedResponse(response);
        }
    }

    private boolean responseRedirectsToAnotherUrl(Response response) {
        return !response.location().equals("");
    }

    private boolean responseReturnsCookie(Response response) {
        return !response.setCookie().equals("");
    }

    private boolean responseReturnsOptions(Response response) {
        return !response.allow().equals("");
    }

    private String formattedResponse(Response response) {
        if(response.body().equals("")) {
            return defaultResponseHeader(response);
        } else {
            return defaultResponseHeader(response) + FormattedStrings.CRLF + response.body();
        }
    }

    private String responseWithOptions(Response response) {
        return "HTTP/1.1 200 OK" + FormattedStrings.CRLF +
                "Allow: " + response.allow() + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF;
    }

    private String defaultResponseHeader(Response response) {
        String responseString = response.protocol() + " " + response.statusCode() + " " + response.statusMessage() + FormattedStrings.CRLF;

        if(responseRedirectsToAnotherUrl(response)) {
            responseString = responseString + "Location: " + response.location() + FormattedStrings.CRLF;
            return responseString;
        }

        responseString = responseString + "Content-Type: " + response.contentType() + FormattedStrings.CRLF;

        if(responseReturnsCookie(response)) {
            responseString = responseString + "Set-Cookie: " + response.setCookie() + FormattedStrings.CRLF;
        }

        responseString = responseString + "Content-Length: " + response.contentLength() + FormattedStrings.CRLF;
        responseString = responseString + "Connection: " + response.connection() + FormattedStrings.CRLF;

        return responseString;
    }

}