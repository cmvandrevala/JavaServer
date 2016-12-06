package http_request;

import java.util.Hashtable;

public class Request {

    private Hashtable<String, String> request;
    private boolean badRequest = false;

    public Request(Hashtable<String,String> params) {
        this.request = emptyRequest();
        this.request.putAll(params);
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

    public String body() { return request.get("Body"); }

    public String contentLength() {
        return request.get("Content-Length");
    }

    public String queryParamsString() {
        return request.get("Query-Params-String");
    }

    public boolean isBadRequest() {
        return this.badRequest;
    }

    public void setAsBadRequest() {
        this.badRequest = true;
    }

    private Hashtable<String, String> emptyRequest() {
        Hashtable<String, String> emptyHashtable = new Hashtable<>();
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
        emptyHashtable.put("Content-Length", "");
        emptyHashtable.put("Body", "");
        emptyHashtable.put("Query-Params-String", "");
        return emptyHashtable;
    }

}
