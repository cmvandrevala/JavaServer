package http_response;

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

    public String contentLength() { return response.get("Content-Length"); }

    private Hashtable<String, String> emptyResponse() {
        Hashtable<String, String> emptyHashtable = new Hashtable<>();
        emptyHashtable.put("Protocol", "");
        emptyHashtable.put("Status-Code", "");
        emptyHashtable.put("Status-Message", "");
        emptyHashtable.put("Content-Type", "");
        emptyHashtable.put("Connection", "");
        emptyHashtable.put("Body", "");
        emptyHashtable.put("Content-Length", "0");
        return emptyHashtable;
    }

}