package http_request;

import utilities.FormattedStrings;

import java.util.Hashtable;

public class HTTPRequestParser {

    private Hashtable<String, String> requestParameters = new Hashtable<String, String>();

    public HTTPRequest parse(String httpRequest) {
        if(invalidInput(httpRequest)) { return badHTTPRequest(); }
        extractParametersFromRequest(httpRequest);
        if(requestHasBody()) { getBodyOfRequest(httpRequest); }
        return new HTTPRequest(requestParameters);
    }

    private HTTPRequest badHTTPRequest() {
        HTTPRequest request = new HTTPRequest(requestParameters);
        request.setAsBadRequest();
        return request;
    }

    private void extractParametersFromRequest(String httpRequest) {
        boolean isFirstLine = true;
        for (String line : httpRequest.split(FormattedStrings.newline)) {
            if (isFirstLine) {
                extractInformationFromFirstLine(line, requestParameters);
                isFirstLine = false;
            } else {
                extractInformationFromAttributeLine(line, requestParameters);
            }
        }
    }

    private boolean requestHasBody() {
        return requestParameters.get("Verb").equals("PUT");
    }

    private void getBodyOfRequest(String httpRequest) {
        String[] splitRequest = httpRequest.split(FormattedStrings.newline + FormattedStrings.newline);
        try {
            requestParameters.put("Body", splitRequest[1]);
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private boolean invalidInput(String httpRequest) {
        boolean isFirstLine = true;
        for(String line : httpRequest.split(FormattedStrings.newline)) {
            if(isFirstLine) {
                isFirstLine = false;
                continue;
            }
            if(line.equals("") || line.equals(FormattedStrings.newline)) {
                break;
            }
            if(line.split(": ").length != 2) {
                return true;
            }
        }
        String[] splitLines = httpRequest.split(FormattedStrings.newline);
        if(httpRequest.equals("")) { return true; }
        if(splitLines[0].split(" ").length < 3) { return true; }
        return false;
    }

     private void extractInformationFromFirstLine(String firstLine, Hashtable<String,String> request) {
        String[] splitLine = firstLine.split(" ");
        request.put("Verb", splitLine[0]);
        request.put("URL", splitLine[1]);
        request.put("Protocol", splitLine[2]);
    }

    private void extractInformationFromAttributeLine(String line, Hashtable<String,String> request) {
        String[] splitLine = line.split(": ");
        if(splitLine.length == 2) {
            request.put(splitLine[0], splitLine[1]);
        }
    }

}