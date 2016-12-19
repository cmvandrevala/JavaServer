package http_response;

import utilities.FormattedStrings;

import java.util.Hashtable;

public class ResponseBuilder {

    private Hashtable<String,String> params = new Hashtable<>();

    public Response build() {
        return new Response(this.params);
    }

    public ResponseBuilder addProtocol(String protocol) {
        this.params.put("Protocol", protocol);
        return this;
    }

    public static Response default400Response() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Protocol", "HTTP/1.1");
        params.put("Status-Code", "400");
        params.put("Status-Message", "Bad Request");
        params.put("Connection", "close");
        return new Response(params);
    }

    public static Response default404Response() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Protocol", "HTTP/1.1");
        params.put("Status-Code", "404");
        params.put("Status-Message", "Not Found");
        params.put("Connection", "close");
        return new Response(params);
    }

    public static Response default405Response() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Protocol", "HTTP/1.1");
        params.put("Status-Code", "405");
        params.put("Status-Message", "Method Not Allowed");
        params.put("Connection", "close");
        return new Response(params);
    }

    public static Response default411Response() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Protocol", "HTTP/1.1");
        params.put("Status-Code", "411");
        params.put("Status-Message", "Length Required");
        params.put("Connection", "close");
        return new Response(params);
    }

    // Image credit to Hayley Jane Wakenshaw at http://www.ascii-code.com/ascii-art/food-and-drinks/coffee-and-tea.php
    public static Response default418Response() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Protocol", "HTTP/1.1");
        params.put("Status-Code", "418");
        params.put("Status-Message", "I'm a teapot");
        params.put("Connection", "close");
        params.put("Body",
                "I'm a teapot" + FormattedStrings.CRLF +
                "             ;,'" + FormattedStrings.CRLF +
                "     _o_    ;:;'" + FormattedStrings.CRLF +
                " ,-.'---`.__ ;" + FormattedStrings.CRLF +
                "((j`=====',-'" + FormattedStrings.CRLF +
                " `-\\     /" + FormattedStrings.CRLF +
                "    `-=-'     hjw");
        return new Response(params);
    }

    public ResponseBuilder addStatusCode(String statusCode) {
        this.params.put("Status-Code", statusCode);
        return this;
    }

    public ResponseBuilder addStatusMessage(String statusMessage) {
        this.params.put("Status-Message", statusMessage);
        return this;
    }

    public ResponseBuilder addContentType(String contentType) {
        this.params.put("Content-Type", contentType);
        return this;
    }

    public ResponseBuilder addConnection(String connection) {
        this.params.put("Connection", connection);
        return this;
    }

    public ResponseBuilder addBody(String body) {
        this.params.put("Body", body);
        return this;
    }

    public ResponseBuilder addSetCookie(String cookie) {
        this.params.put("Set-Cookie", cookie);
        return this;
    }

    public ResponseBuilder addLocation(String location) {
        this.params.put("Location", location);
        return this;
    }

    public ResponseBuilder addAllow(String options) {
        this.params.put("Allow", options);
        return this;
    }

    public ResponseBuilder addETag(String etag) {
        this.params.put("ETag", etag);
        return this;
    }

    public ResponseBuilder addContentLocation(String contentLocation) {
        this.params.put("Content-Location", contentLocation);
        return this;
    }
}
