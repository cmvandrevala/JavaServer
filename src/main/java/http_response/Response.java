package http_response;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class Response {

    private final Hashtable<String, String> params;

    public Response(Hashtable<String,String> params) {
        this.params = emptyResponse();
        this.params.putAll(params);
    }

    public String protocol() {
        return params.get("Protocol");
    }

    public int statusCode() {
        return Integer.parseInt(params.get("Status-Code"));
    }

    public String statusMessage() {
        return params.get("Status-Message");
    }

    public String contentType() {
        return params.get("Content-Type");
    }

    public String connection() {
        return params.get("Connection");
    }

    public String body() {
        return params.get("Body");
    }

    public String contentLength() { return contentLength(params.get("Body")); }

    public String setCookie() {
        return params.get("Set-Cookie");
    }

    public String location() {
        return params.get("Location");
    }

    public String allow() { return params.get("Allow"); }

    public String etag() {
        return params.get("ETag");
    }

    public String contentLocation() { return params.get("Content-Location"); }

    private Hashtable<String, String> emptyResponse() {
        Hashtable<String, String> emptyHashtable = new Hashtable<>();
        emptyHashtable.put("Protocol", "");
        emptyHashtable.put("Status-Code", "");
        emptyHashtable.put("Status-Message", "");
        emptyHashtable.put("Content-Type", "");
        emptyHashtable.put("Connection", "");
        emptyHashtable.put("Body", "");
        emptyHashtable.put("Set-Cookie", "");
        emptyHashtable.put("Location", "");
        emptyHashtable.put("Allow", "");
        emptyHashtable.put("ETag", "");
        emptyHashtable.put("Content-Location", "");
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