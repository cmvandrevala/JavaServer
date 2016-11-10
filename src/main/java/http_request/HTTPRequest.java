package http_request;

import java.util.Hashtable;

public class HTTPRequest {

    private Hashtable<String, String> request;

    public HTTPRequest(String requestString) {
        request = emptyRequest();
        if(inputGiven(requestString)) {
            String[] httpRequestLines = requestString.split("\n");
            boolean isFirstLine = true;
            for (String line : httpRequestLines) {
                if (isFirstLine) {
                    extractInformationFromFirstLine(line);
                    isFirstLine = false;
                } else {
                    extractInformationFromLine(line);
                }
            }
        }
    }

    public String verb() {
        return request.get("Verb");
    }

    public String url() {
        return request.get("URL");
    }

    public String protocol() {
        return request.get("Protocol");
    }

    public String host() {
        return request.get("Host");
    }

    public String userAgent() {
        return request.get("User-Agent");
    }

    public String accept() {
        return request.get("Accept");
    }

    public String acceptLanguage() {
        return request.get("Accept-Language");
    }

    public String acceptEncoding() {
        return request.get("Accept-Encoding");
    }

    public String acceptCharset() {
        return request.get("Accept-Charset");
    }

    public String keepAlive() {
        return request.get("Keep-Alive");
    }

    public String connection() {
        return request.get("Connection");
    }

    public String cookie() {
        return request.get("Cookie");
    }

    public String pragma() {
        return request.get("Pragma");
    }

    public String cacheControl() {
        return request.get("Cache-Control");
    }

    private Hashtable<String, String> emptyRequest() {
        Hashtable<String, String> emptyHashtable = new Hashtable<String, String>();
        emptyHashtable.put("Verb", "");
        emptyHashtable.put("URL", "");
        emptyHashtable.put("Protocol", "");
        emptyHashtable.put("Host", "");
        emptyHashtable.put("User-Agent", "");
        emptyHashtable.put("Accept", "");
        emptyHashtable.put("Accept-Language", "");
        emptyHashtable.put("Accept-Encoding", "");
        emptyHashtable.put("Accept-Charset", "");
        emptyHashtable.put("Keep-Alive", "");
        emptyHashtable.put("Connection", "");
        emptyHashtable.put("Cookie", "");
        emptyHashtable.put("Pragma", "");
        emptyHashtable.put("Cache-Control", "");
        return emptyHashtable;
    }

    private boolean inputGiven(String request) {
        return request != "";
    }

    private void extractInformationFromFirstLine(String firstLine) {
        String[] splitLine = firstLine.split(" ");
        request.put("Verb", splitLine[0]);
        request.put("URL", splitLine[1]);
        request.put("Protocol", splitLine[2]);
    }

    private void extractInformationFromLine(String line) {
        String[] splitLine = line.split(": ");
        String requestKey = splitLine[0];
        String requestValue = splitLine[1];
        request.put(requestKey, requestValue);
    }

}
