package http_request;

import java.util.Hashtable;

public class RequestBuilder {

    private Hashtable<String,String> params = new Hashtable<>();

    public Request build() {
        return new Request(this.params);
    }

    public RequestBuilder addVerb(String verb) {
        this.params.put("Verb", verb);
        return this;
    }

    public RequestBuilder addUrl(String url) {
        this.params.put("URL", url);
        return this;
    }

    public RequestBuilder addProtocol(String protocol) {
        this.params.put("Protocol", protocol);
        return this;
    }

    public RequestBuilder addHost(String host) {
        this.params.put("Host", host);
        return this;
    }

    RequestBuilder addUserAgent(String agent) {
        this.params.put("User-Agent", agent);
        return this;
    }

    RequestBuilder addAccept(String accept) {
        this.params.put("Accept", accept);
        return this;
    }

    RequestBuilder addAcceptLanguage(String language) {
        this.params.put("Accept-Language", language);
        return this;
    }

    public RequestBuilder addContentLength(String contentLength) {
        this.params.put("Content-Length", contentLength);
        return this;
    }

    public RequestBuilder addContentLength(int contentLength) {
        this.params.put("Content-Length", Integer.toString(contentLength));
        return this;
    }

    public RequestBuilder addBody(String body) {
        this.params.put("Body", body);
        return this;
    }

    RequestBuilder addAcceptEncoding(String encoding) {
        this.params.put("Accept-Encoding", encoding);
        return this;
    }

    RequestBuilder addCookie(String cookie) {
        this.params.put("Cookie", cookie);
        return this;
    }

    RequestBuilder addKeepAlive(String keepAlive) {
        this.params.put("Keep-Alive", keepAlive);
        return this;
    }

    RequestBuilder addConnection(String connection) {
        this.params.put("Connection", connection);
        return this;
    }

    RequestBuilder addPragma(String pragma) {
        this.params.put("Pragma", pragma);
        return this;
    }

    RequestBuilder addCacheControl(String cacheControl) {
        this.params.put("Cache-Control", cacheControl);
        return this;
    }

    RequestBuilder addAcceptCharset(String charset) {
        this.params.put("Accept-Charset", charset);
        return this;
    }

    RequestBuilder addQueryParams(String params) {
        this.params.put("Query-Params-String", params);
        return this;
    }

    public RequestBuilder addIfNoneMatch(String eTag) {
        this.params.put("If-None-Match", eTag);
        return this;
    }

    public RequestBuilder addIfMatch(String eTag) {
        this.params.put("If-Match", eTag);
        return this;
    }

    public RequestBuilder addRange(String range) {
        this.params.put("Range", range);
        return this;
    }
}


