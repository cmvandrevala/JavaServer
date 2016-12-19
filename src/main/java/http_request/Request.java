package http_request;

import java.util.Hashtable;

public class Request {

    private final Hashtable<String, String> request;

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

    String userAgent() {
        return request.get("User-Agent");
    }

    public String accept() {
        return request.get("Accept");
    }

    String acceptLanguage() {
        return request.get("Accept-Language");
    }

    String acceptEncoding() {
        return request.get("Accept-Encoding");
    }

    String acceptCharset() {
        return request.get("Accept-Charset");
    }

    String keepAlive() {
        return request.get("Keep-Alive");
    }

    public String connection() {
        return request.get("Connection");
    }

    public String cookie() {
        return request.get("Cookie");
    }

    String pragma() {
        return request.get("Pragma");
    }

    String cacheControl() {
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
        return request.get("Verb").equals("");
    }

    public String ifNoneMatch() {
        return request.get("If-None-Match");
    }

    //
    public String ifMatch() {
        return request.get("If-Match");
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
        emptyHashtable.put("If-None-Match", "");
        emptyHashtable.put("If-Match", "");
        return emptyHashtable;
    }
}
