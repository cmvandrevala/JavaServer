package http_response;

import http_request.Request;
import routing.PathToUrlMapper;
import utilities.FormattedStrings;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ResponseWriter {

    public String writeHttpResponse(Response response) {
        if(response.body().equals("")) {
            return responseHeader(response);
        } else {
            return responseHeader(response) + FormattedStrings.CRLF + response.body();
        }
    }

    private String responseHeader(Response response) {
        String responseString = response.protocol() + " " + response.statusCode() + " " + response.statusMessage() + FormattedStrings.CRLF;
        responseString = responseString + "Date: " + getServerTime() + FormattedStrings.CRLF;

        if(!response.location().equals("")) {
            responseString = responseString + "Location: " + response.location() + FormattedStrings.CRLF;
            return responseString;
        }

        if(!response.allow().equals("")) {
            responseString = responseString + "Allow: " + response.allow() + FormattedStrings.CRLF;
            responseString = responseString + "Content-Type: text/html" + FormattedStrings.CRLF;
            responseString = responseString + "Content-Length: 0" + FormattedStrings.CRLF;
            responseString = responseString + "Connection: close" + FormattedStrings.CRLF;
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

    String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

}