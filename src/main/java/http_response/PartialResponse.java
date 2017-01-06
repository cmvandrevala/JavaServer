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
