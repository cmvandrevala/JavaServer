package http_response;

import http_request.Request;
import routing.DataTable;
import routing.RoutesTable;

public class ResponseGenerator {

    private RoutesTable routesTable;
    private DataTable dataTable;

    public ResponseGenerator(RoutesTable routesTable, DataTable dataTable) {
        this.routesTable = routesTable;
        this.dataTable = dataTable;
    }

    public Response generateResponse(Request request) {
        if(!request.range().equals("")) {
            return rangeResponse(request);
        } else if(request.verb().equals("OPTIONS")) {
            return optionsResponse(request);
        } else if(request.verb().equals("PATCH")) {
            return patchResponse(request);
        } else if(dataTable.retrieveLocation(request.url()).equals("")) {
            return generalResponse(request);
        } else {
            return redirectResponse(request);
        }
    }

    String partialContent(Request request) {
        if(request.range().equals("")) {
            return fullBody(request.url());
        } else if(bounds(request)[0] > 0) {
            return fullBody(request.url()).substring(bounds(request)[0], bounds(request)[1]);
        } else {
            return fullBody(request.url()).substring(bounds(request)[0], bounds(request)[1]);
        }
    }

    private Response rangeResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addProtocol("HTTP/1.1").addStatusCode(206).addStatusMessage("Partial Content");
        builder.addContentType(contentType(request)).addBody(partialContent(request));
        if(containsUpperBound(request) && !containsLowerBound(request)) {
            int adjustedLowerBound = bounds(request)[0] + 1;
            builder.addContentRange("bytes " + adjustedLowerBound + "-" + bounds(request)[1] + "/" + fullBody(request.url()).length());
        } else {
            builder.addContentRange("bytes " + bounds(request)[0] + "-" + bounds(request)[1] + "/" + fullBody(request.url()).length());
        }
        return builder.build();
    }

    private Response optionsResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (String v : routesTable.formattedVerbsForUrl(request.url())) {
            sb.append(delimiter).append(v);
            delimiter = ",";
        }
        builder.addProtocol("HTTP/1.1").addStatusCode(200).addStatusMessage("OK").addAllow(sb.toString());
        return builder.build();
    }

    private Response patchResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addProtocol("HTTP/1.1").addStatusCode(204).addStatusMessage("No Content");
        builder.addETag(eTag(request.url())).addContentLocation("/patch-content.txt");
        return builder.build();
    }

    private Response generalResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        String setCookie = dataTable.retrieveSetCookie(request.url());
        builder.addProtocol("HTTP/1.1").addStatusCode(200).addStatusMessage("OK");
        builder.addSetCookie(setCookie).addContentType(contentType(request));
        builder.addETag(eTag(request.url())).addBody(fullBody(request.url()));
        return builder.build();
    }

    private Response redirectResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        String location = dataTable.retrieveLocation(request.url());
        builder.addProtocol("HTTP/1.1").addStatusCode(302);
        builder.addStatusMessage("Found").addLocation(location);
        return builder.build();
    }

    private int[] bounds(Request request) {
        String[] range = request.range().split("=");
        String[] limits = range[1].split("-");
        int lowerBound = 0;
        int upperBound = fullBody(request.url()).length();

        if(containsLowerBound(request) && containsUpperBound(request)) {
            lowerBound = Integer.parseInt(limits[0]);
            upperBound = Integer.parseInt(limits[1]);
        } else if(containsLowerBound(request) && !containsUpperBound(request)) {
            lowerBound = Integer.parseInt(limits[0]);
        } else {
            lowerBound = upperBound - Integer.parseInt(limits[1]);
        }

        return new int[]{lowerBound, upperBound};
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

    private boolean containsLowerBound(Request request) {
        String[] range = request.range().split("=");
        String[] limits = range[1].split("-");
        return !limits[0].equals("");
    }

    private boolean containsUpperBound(Request request) {
        String[] range = request.range().split("=");
        String[] limits = range[1].split("-");
        return limits.length > 1;
    }

    private String fullBody(String url) {
        return dataTable.retrieveBody(url);
    }

    private String eTag(String url) {
        return dataTable.retrieveETag(url);
    }
    
}
