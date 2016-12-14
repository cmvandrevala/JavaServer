package http_response;

import http_request.Request;
import routing.DataTable;
import routing.RoutesTable;
import utilities.FormattedStrings;

import java.io.UnsupportedEncodingException;

public class GetResponse implements HTTPResponse {

    private Request request;
    private RoutesTable routesTable;
    private DataTable dataTable;

    public GetResponse(Request request, RoutesTable routesTable, DataTable dataTable) {
        this.request = request;
        this.routesTable = routesTable;
        this.dataTable = dataTable;
    }

    public int statusCode() {
        return 200;
    }

    public String responseString() {
        if(responseReturnsCookie()) {
            return "HTTP/1.1 200 OK" + FormattedStrings.CRLF +
                    "Content-Type: " + contentType(request) + FormattedStrings.CRLF +
                    "Set-Cookie: " + request.queryParamsString() + FormattedStrings.CRLF +
                    "Content-Length: " + contentLength(responseBody()) + FormattedStrings.CRLF +
                    "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF +
                    responseBody();
        } else {
            return "HTTP/1.1 200 OK" + FormattedStrings.CRLF +
                    "Content-Type: " + contentType(request) + FormattedStrings.CRLF +
                    "Content-Length: " + contentLength(responseBody()) + FormattedStrings.CRLF +
                    "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF +
                    responseBody();
        }
    }

    private String responseBody() {
        if (responseReturnsCookie() && !requestContainsCookie()) {
            return "Eat";
        } else if(requestContainsCookie() && urlAcceptsCookie()) {
            return cookieBody();
        } else if(requestContainsBody()){
            return formattedRequestBody();
        } else if(requestContainsQueryParams()) {
            return formattedQueryParams();
        } else if(requestContainsRoutingData()) {
            return formattedRoutingData();
        } else {
            return defaultResponseBody();
        }
    }

    private String contentType(Request request) {
        String[] contentAndExtension = request.url().split("\\.");

        if(contentAndExtension.length < 2) {
            return "text/html";
        }

        switch(contentAndExtension[1]) {
            case "txt":
                return "text/plain";
            case "jpeg":
                return "image/jpeg";
            case "jpg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            default:
                return "text/html";
        }
    }

    private boolean responseReturnsCookie() {
        return this.dataTable.retrieveData(request.url(), "Returns-Cookie").equals("true") && !request.queryParamsString().equals("");
    }

    private boolean urlAcceptsCookie() {
        return this.dataTable.retrieveData(request.url(),"Accepts-Cookie").equals("true");
    }

    private boolean requestContainsCookie() {
        return !this.request.cookie().equals("");
    }

    private String cookieBody() {
        String[] splitCookieString = request.cookie().split("type=");
        if(splitCookieString.length == 2) {
            return "mmmm " + splitCookieString[1];
        } else {
            return "Your cookie has no type...";
        }
    }

    private boolean requestContainsBody() {
        return !dataTable.retrieveData(request.url(),"body").equals("");
    }

    private String formattedRequestBody() {
        return dataTable.retrieveData(request.url(),"body");
    }

    private boolean requestContainsQueryParams() {
        return !request.queryParamsString().equals("");
    }

    private String formattedQueryParams() {
        return request.queryParamsString().replace("1=O", "1 = O").replace("2=s", "2 = s");
    }

    private boolean requestContainsRoutingData() {
        return !dataTable.retrieveData(request.url(),"data").equals("");
    }

    private String formattedRoutingData() {
        return "data=" + dataTable.retrieveData(request.url(),"data");
    }

    private String defaultResponseBody() {
        return "<h1>Hello World!</h1>";
    }

    private String contentLength(String content) {
        int contentLength = 0;
        try {
            contentLength = content.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Integer.toString(contentLength);
    }

}
