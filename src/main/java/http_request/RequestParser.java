package http_request;

import utilities.FormattedStrings;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Hashtable;

public class RequestParser {

    private Hashtable<String, String> requestParameters = new Hashtable<>();

    public Request parse(String request) {
        if(invalidInput(request)) {
            return new Request(new Hashtable<>());
        }
        extractParametersFromRequest(request);
        if(requestHasBody()) { getBodyOfRequest(request); }
        return new Request(requestParameters);
    }

    private void extractParametersFromRequest(String request) {
        extractInformationFromFirstLine(firstLineOfRequest(request));
        for (String line : requestMinusFirstLine(request)) {
            extractInformationFromAttributeLine(line);
        }
    }

    private boolean requestHasBody() {
        return requestParameters.get("Verb").equals("PUT") ||
                requestParameters.get("Verb").equals("POST") ||
                requestParameters.get("Verb").equals("PATCH");
    }

    private void getBodyOfRequest(String httpRequest) {
        if(httpRequest.contains(FormattedStrings.CRLF + FormattedStrings.CRLF)) {
            String[] splitRequest = httpRequest.split(FormattedStrings.CRLF + FormattedStrings.CRLF);
            requestParameters.put("Body", splitRequest[1]);
        }
    }

    private boolean invalidInput(String request) {
        if(firstLineOfRequest(request).split(" ").length != 3) { return true; }
        for(String line : requestMinusFirstLine(request)) {
            if(reachedEndOfHeaderLines(line)) { break; }
            if(malformedHeaderLine(line)) { return true; }
        }
        return false;
    }

    private boolean reachedEndOfHeaderLines(String line) {
        return line.equals("") || line.equals(FormattedStrings.CRLF);
    }

    private boolean malformedHeaderLine(String line) {
        return line.split(": ").length != 2;
    }

    private String firstLineOfRequest(String request) {
        String[] requestLines = request.split(FormattedStrings.CRLF);
        return requestLines[0];
    }

    private String[] requestMinusFirstLine(String request) {
        String[] requestLines = request.split(FormattedStrings.CRLF);
        return Arrays.copyOfRange(requestLines, 1, requestLines.length);
    }

     private void extractInformationFromFirstLine(String firstLine) {
        String[] splitLine = firstLine.split(" ");
        this.requestParameters.put("Verb", splitLine[0]);
        this.requestParameters.put("Protocol", splitLine[2]);
        extractInformationFromUrl(splitLine[1]);
    }

    private void extractInformationFromUrl(String url) {
        if(url.contains("?")) {
            String[] urlAndQueryStrings = url.split("\\?");
            this.requestParameters.put("URL", urlAndQueryStrings[0]);
            extractQueryParams(urlAndQueryStrings[1]);
        } else {
            this.requestParameters.put("URL", url);
        }
    }

    private void extractQueryParams(String queryParams) {
        ParameterDecoder decoder = new ParameterDecoder();
        String paramsWithNewlines = queryParams.replace("&", FormattedStrings.CRLF);
        try {
            this.requestParameters.put("Query-Params-String", decoder.decode(paramsWithNewlines));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void extractInformationFromAttributeLine(String line) {
        String[] splitLine = line.split(": ");
        if(splitLine.length == 2) {
            this.requestParameters.put(splitLine[0], splitLine[1]);
        }
    }

}
