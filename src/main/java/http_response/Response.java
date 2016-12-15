package http_response;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class Response {

    private final Hashtable<String, String> response;

    public Response(Hashtable<String,String> params) {
        this.response = emptyResponse();
        this.response.putAll(params);
    }

    public String protocol() {
        return response.get("Protocol");
    }

    public String statusCode() {
        return response.get("Status-Code");
    }

    public String statusMessage() {
        return response.get("Status-Message");
    }

    public String contentType() {
        return response.get("Content-Type");
    }

    public String connection() {
        return response.get("Connection");
    }

    public String body() {
        return response.get("Body");
    }

    public String contentLength() { return contentLength(response.get("Body")); }

    private Hashtable<String, String> emptyResponse() {
        Hashtable<String, String> emptyHashtable = new Hashtable<>();
        emptyHashtable.put("Protocol", "");
        emptyHashtable.put("Status-Code", "");
        emptyHashtable.put("Status-Message", "");
        emptyHashtable.put("Content-Type", "");
        emptyHashtable.put("Connection", "");
        emptyHashtable.put("Body", "");
        return emptyHashtable;
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

}