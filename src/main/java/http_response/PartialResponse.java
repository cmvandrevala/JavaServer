package http_response;

import http_request.Request;
import routing.PathToUrlMapper;

class PartialResponse {

    private PathToUrlMapper mapper;

    PartialResponse(PathToUrlMapper mapper) {
        this.mapper = mapper;
    }

    boolean containsLowerBound(Request request) {
        String[] boundaries = partialContentBoundaries(request);
        return !boundaries[0].equals("");
    }

    boolean containsUpperBound(Request request) {
        String[] boundaries = partialContentBoundaries(request);
        return boundaries.length > 1;
    }

    int lowerBound(Request request) {
        String[] boundaries = partialContentBoundaries(request);
        if(boundaries[0].equals("")) {
            return (int) mapper.fileCorrespondingToUrl("/partial_content.txt").length() - Integer.valueOf(boundaries[1]);
        } else {
            return Integer.valueOf(boundaries[0]);
        }
    }

    int upperBound(Request request) {
        String[] boundaries = partialContentBoundaries(request);
        if(boundaries.length == 2 && !boundaries[0].equals("")) {
            return Integer.valueOf(boundaries[1]);
        } else {
            return (int) mapper.fileCorrespondingToUrl("/partial_content.txt").length() - 1;
        }
    }

    private String[] partialContentBoundaries(Request request) {
        String[] range = request.range().split("=");
        return range[1].split("-");
    }

}
