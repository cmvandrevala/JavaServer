package http_response;

import utilities.FormattedStrings;

public class ResponseWriter {

    public String writeHttpResponse(Response response) {
        if(response.statusCode().equals("")) {
            return default400Response;
        } else {
            return formattedResponse(response);
        }
    }

    private String default400Response = "HTTP/1.1 400 Bad Request" + FormattedStrings.CRLF +
                                        "Content-Type: text/html" + FormattedStrings.CRLF +
                                        "Content-Length: 0" + FormattedStrings.CRLF +
                                        "Connection: close" + FormattedStrings.CRLF;

    private String formattedResponse(Response response) {
        if(response.body().equals("")) {
            return responseHeader(response);
        } else {
            return responseHeader(response) + FormattedStrings.CRLF + response.body();
        }
    }

    private String responseHeader(Response response) {
        return response.protocol() + " " + response.statusCode() + " " + response.statusMessage() + FormattedStrings.CRLF +
                "Content-Type: " + response.contentType() + FormattedStrings.CRLF +
                "Content-Length: " + response.contentLength() + FormattedStrings.CRLF +
                "Connection: " + response.connection() + FormattedStrings.CRLF;
    }
}


