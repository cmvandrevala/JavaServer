package http_request;

import java.util.Hashtable;

public class HTTPRequestBuilder {

    public Hashtable<String,String> tokenizeRequest(String httpRequest) {

        Hashtable<String, String> tokenizedOutput = new Hashtable<String, String>();
        String[] httpRequestLines = httpRequest.split("\r\n");
        boolean isFirstLine = true;

        for (String line : httpRequestLines) {
            if (isFirstLine && validInput(httpRequest)) {
                extractInformationFromFirstLine(line, tokenizedOutput);
                isFirstLine = false;
            } else if (isFirstLine && !validInput(httpRequest)) {
                populateBlanksForFirstLine(tokenizedOutput);
                isFirstLine = false;
            } else if (!isFirstLine && validInput(httpRequest)) {
                extractInformationFromLine(line, tokenizedOutput);
            } else {
                populateBlanksForLine(line, tokenizedOutput);
            }

            if(tokenizedOutput.get("Verb").equals("PUT")) {
                try {
                    String[] splitRequest = httpRequest.split("\r\n\r\n");
                    tokenizedOutput.put("Body", splitRequest[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

        }

        return tokenizedOutput;
    }

    private boolean validInput(String httpRequest) {
        String[] splitLines = httpRequest.split("\r\n");
        if(httpRequest.equals("")) { return false; }
        if(splitLines.length == 0) { return false; }
        if(splitLines[0].split(" ").length < 3) { return false; }
        return true;
    }

     private void extractInformationFromFirstLine(String firstLine, Hashtable<String,String> request) {
        String[] splitLine = firstLine.split(" ");
        request.put("Verb", splitLine[0]);
        request.put("URL", splitLine[1]);
        request.put("Protocol", splitLine[2]);
    }

    private void extractInformationFromLine(String line, Hashtable<String,String> request) {
        String[] splitLine = line.split(": ");
        if(splitLine.length == 2) {
            String requestKey = splitLine[0];
            String requestValue = splitLine[1];
            request.put(requestKey, requestValue);
        }
    }

    private void populateBlanksForFirstLine(Hashtable<String,String> request) {
        request.put("Verb", "");
        request.put("URL", "");
        request.put("Protocol", "");
    }

    private void populateBlanksForLine(String line, Hashtable<String,String> request) {
        String[] splitLine = line.split(": ");
        String requestKey = splitLine[0];
        String requestValue = "";
        request.put(requestKey, requestValue);
    }

}
