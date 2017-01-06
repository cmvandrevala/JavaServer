package http_response;

import http_request.Request;
import routing.DataTable;
import routing.PathToUrlMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    String getContentRange(Request request, DataTable dataTable) {
        String contentRange;
        if(containsUpperBound(request) && !containsLowerBound(request)) {
            int adjustedLowerBound = lowerBound(request) + 1;
            contentRange = "bytes " + adjustedLowerBound + "-" + upperBound(request) + "/" + dataTable.retrieveBody(request.url()).length();
        } else {
            contentRange = "bytes " + lowerBound(request) + "-" + upperBound(request) + "/" + dataTable.retrieveBody(request.url()).length();
        }
        return contentRange;
    }

    String partialContent(Request request) {
        if (request.range().isEmpty()) {
            return getContent(request);
        }
        String rangeString = request.range().split("=")[1];
        return new PartialContent(getContent(request), parseLowerBound(rangeString), parseUpperBound(rangeString)).body();

    }

    private String getContent(Request request) {
        try {
            return readTextFile(mapper.fileCorrespondingToUrl(request.url()).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private Integer parseUpperBound(String rangeString) {
        if (rangeString.endsWith("-")) {
            return null;
        } else {
            return Integer.valueOf(rangeString.split("-")[1]);
        }
    }

    private Integer parseLowerBound(String rangeString) {
        if (rangeString.startsWith("-")) {
            return null;
        } else {
            return Integer.valueOf(rangeString.split("-")[0]);
        }
    }

    private String readTextFile(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch(Exception ignored) {}
        return contentBuilder.toString();
    }

}
