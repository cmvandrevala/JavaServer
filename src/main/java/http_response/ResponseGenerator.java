package http_response;

import http_request.Request;
import routing.DataTable;
import routing.PathToUrlMapper;
import routing.RoutesTable;

public class ResponseGenerator {

    private RoutesTable routesTable;
    private DataTable dataTable;

    public ResponseGenerator(RoutesTable routesTable, DataTable dataTable) {
        this.routesTable = routesTable;
        this.dataTable = dataTable;
    }

    public Response generateResponse(Request request, PathToUrlMapper mapper) {
        if(!request.range().equals("")) {
            return rangeResponse(request, mapper);
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

    String partialContent(Request request, PathToUrlMapper mapper) {
        PartialResponse partialResponse = new PartialResponse(mapper);
        if(request.range().equals("")) {
            return dataTable.retrieveBody(request.url());
        } else if(partialResponse.lowerBound(request) > 0) {
            return dataTable.retrieveBody(request.url()).substring(partialResponse.lowerBound(request), partialResponse.upperBound(request));
        } else {
            return dataTable.retrieveBody(request.url()).substring(partialResponse.lowerBound(request), partialResponse.upperBound(request));
        }
    }

    private Response rangeResponse(Request request, PathToUrlMapper mapper) {
        PartialResponse partialResponse = new PartialResponse(mapper);
        return new ResponseBuilder().
            addProtocol("HTTP/1.1").
            addStatusCode(206).
            addStatusMessage("Partial Content").
            addContentType(contentType(request)).
            addBody(partialContent(request, mapper)).
            addContentRange(partialResponse.getContentRange(request, dataTable)).
            build();
    }

    private Response optionsResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addProtocol("HTTP/1.1").addStatusCode(200).addStatusMessage("OK");
        builder.addAllow(allowedVerbs(request));
        return builder.build();
    }

    private Response patchResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addProtocol("HTTP/1.1").addStatusCode(204).addStatusMessage("No Content");
        builder.addETag(dataTable.retrieveETag(request.url())).addContentLocation("/patch-content.txt");
        return builder.build();
    }

    private Response generalResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addProtocol("HTTP/1.1").addStatusCode(200).addStatusMessage("OK");
        builder.addSetCookie(dataTable.retrieveSetCookie(request.url())).addContentType(contentType(request));
        builder.addETag(dataTable.retrieveETag(request.url())).addBody(dataTable.retrieveBody(request.url()));
        return builder.build();
    }

    private Response redirectResponse(Request request) {
        ResponseBuilder builder = new ResponseBuilder();
        String location = dataTable.retrieveLocation(request.url());
        builder.addProtocol("HTTP/1.1").addStatusCode(302);
        builder.addStatusMessage("Found").addLocation(location);
        return builder.build();
    }

    private String allowedVerbs(Request request) {
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (String v : routesTable.formattedVerbsForUrl(request.url())) {
            sb.append(delimiter).append(v);
            delimiter = ",";
        }
        return sb.toString();
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
    
}
