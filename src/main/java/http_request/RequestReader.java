package http_request;

import utilities.FormattedStrings;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestReader {

    public static String readHttpRequest(BufferedReader bufferedReader) throws IOException {

        int contentLength = 0;
        String input;
        String contentLengthStr = "Content-Length: ";
        StringBuilder requestBody = new StringBuilder();

        while(true) {
            input = bufferedReader.readLine();
            if(input == null || input.equals("")) {
                break;
            } else {
                requestBody.append(input + FormattedStrings.CRLF);
            }
            if(input.contains("Content-Length: ")) {
                contentLength = Integer.parseInt(input.substring(contentLengthStr.length()));
            }
        }

        if(contentLength > 0) {
            final char[] content = new char[contentLength];
            bufferedReader.read(content);
            requestBody.append(FormattedStrings.CRLF);
            requestBody.append(content);
            requestBody.append(FormattedStrings.CRLF);
        }

        return requestBody.toString();
    }

}
