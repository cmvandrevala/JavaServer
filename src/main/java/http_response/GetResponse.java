package http_response;

import http_request.Request;
import routing.PathToUrlMapper;
import utilities.FormattedStrings;

import java.io.*;

public class GetResponse implements HTTPResponse {

    private Request request;

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
        File file = new PathToUrlMapper().fileCorrespondingToUrl(request.url());
        if(file.exists()) {
            try {
                return readFile(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
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

    private String readFile(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String str;
        while ((str = in.readLine()) != null) {
            contentBuilder.append(str);
        }
        in.close();
        return contentBuilder.toString();
    }


}
