package http_response;

import http_request.Request;
import routing.DataTable;
import routing.PathToUrlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class PartialResponse {

    private PathToUrlMapper mapper;

    PartialResponse(PathToUrlMapper mapper) {
        this.mapper = mapper;
    }

    String getContentRange(Request request, DataTable dataTable) {
        return "bytes " + parseLowerBound(request) + "-" + parseUpperBound(request) + "/" + dataTable.retrieveBody(request.url()).length();
    }

    String partialContent(Request request) {
        if (request.range().isEmpty()) {
            return getContent(request);
        }
        return new PartialContent(getContent(request), parseLowerBound(request), parseUpperBound(request)).body();

    }

    private String getContent(Request request) {
        try {
            return readTextFile(mapper.fileCorrespondingToUrl(request.url()).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private Integer parseUpperBound(Request request) {
        String rangeString = request.range().split("=")[1];
        if (rangeString.endsWith("-")) {
            return null;
        } else {
            return Integer.valueOf(rangeString.split("-")[1]);
        }
    }

    private Integer parseLowerBound(Request request) {
        String rangeString = request.range().split("=")[1];
        if (rangeString.startsWith("-")) {
            return null;
        } else {
            return Integer.valueOf(rangeString.split("-")[0]);
        }
    }

    private String readTextFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }

}
