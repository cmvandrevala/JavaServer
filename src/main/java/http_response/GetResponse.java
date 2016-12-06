package http_response;

import http_request.Request;
import routing.RoutesTable;
import utilities.FormattedStrings;

import java.io.UnsupportedEncodingException;

public class GetResponse implements HTTPResponse {

    private Request request;

    private RoutesTable routesTable = RoutesTable.getInstance();

    public GetResponse(Request request) {
        this.request = request;
    }

    public int statusCode() {
        return 200;
    }

    public String responseString() {
        return "HTTP/1.1 200 OK" + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: " + contentLength(responseBody()) + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF +
                responseBody() + FormattedStrings.CRLF;
    }

    private String responseBody() {
        if(requestContainsBody()) {
            return formattedRequestBody();
        } else if(requestContainsQueryParams()) {
            return formattedQueryParams();
        } else if(requestContainsRoutingData()) {
            return formattedRoutingData();
        } else {
            return defaultResponseBody();
        }
    }

    private boolean requestContainsBody() {
        return !routesTable.retrieveData(request.url(),"body").equals("");
    }

    private String formattedRequestBody() {
        return routesTable.retrieveData(request.url(),"body");
    }

    private boolean requestContainsQueryParams() {
        return !request.queryParamsString().equals("");
    }

    private String formattedQueryParams() {
        return request.queryParamsString().replace("1=O", "1 = O").replace("2=s", "2 = s");
    }

    private boolean requestContainsRoutingData() {
        return !routesTable.retrieveData(request.url(),"data").equals("");
    }

    private String formattedRoutingData() {
        return "data=" + routesTable.retrieveData(request.url(),"data");
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
