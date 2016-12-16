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

        if(!response.location().equals("")) {
            responseString = responseString + "Location: " + response.location() + FormattedStrings.CRLF;
            return responseString;
        }

        if(!response.etag().equals("")) {
            responseString = responseString + "ETag: " + response.etag() + FormattedStrings.CRLF;
        }

        if(!response.contentType().equals("")) {
            responseString = responseString + "Content-Type: " + response.contentType() + FormattedStrings.CRLF;
        }

        if(!response.setCookie().equals("")) {
            responseString = responseString + "Set-Cookie: " + response.setCookie() + FormattedStrings.CRLF;
        }

        if(!response.contentLength().equals("")) {
            responseString = responseString + "Content-Length: " + response.contentLength() + FormattedStrings.CRLF;
        }

        if(!response.connection().equals("")) {
            responseString = responseString + "Connection: " + response.connection() + FormattedStrings.CRLF;
        }

        return responseString;
    }

}