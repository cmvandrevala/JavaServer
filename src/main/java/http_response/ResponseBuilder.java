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

    ResponseBuilder addStatusCode(int statusCode) {
        this.params.put("Status-Code", Integer.toString(statusCode));
        return this;
    }

    ResponseBuilder addStatusMessage(String statusMessage) {
        this.params.put("Status-Message", statusMessage);
        return this;
    }

    ResponseBuilder addContentType(String contentType) {
        this.params.put("Content-Type", contentType);
        return this;
    }

    ResponseBuilder addConnection(String connection) {
        this.params.put("Connection", connection);
        return this;
    }

    public ResponseBuilder addBody(String body) {
        this.params.put("Body", body);
        return this;
    }

    ResponseBuilder addSetCookie(String cookie) {
        this.params.put("Set-Cookie", cookie);
        return this;
    }

    ResponseBuilder addLocation(String location) {
        this.params.put("Location", location);
        return this;
    }

    ResponseBuilder addAllow(String options) {
        this.params.put("Allow", options);
        return this;
    }

    ResponseBuilder addETag(String etag) {
        this.params.put("ETag", etag);
        return this;
    }

    ResponseBuilder addContentLocation(String contentLocation) {
        this.params.put("Content-Location", contentLocation);
        return this;
    }

    ResponseBuilder addContentRange(String contentRange) {
        this.params.put("Content-Range", contentRange);
        return this;
    }

    ResponseBuilder addWWWAuthenticate(String wwwAuthenticate) {
        this.params.put("WWW-Authenticate", wwwAuthenticate);
        return this;
    }

    public static Response default400Response() {
        return new ResponseBuilder().
                addProtocol("HTTP/1.1").
                addStatusCode(400).
                addStatusMessage("Bad Request").
                addConnection("close").
                build();
    }

    public static Response default401Response(String realm) {
        return new ResponseBuilder().
                addProtocol("HTTP/1.1").
                addStatusCode(401).
                addStatusMessage("Unauthorized").
                addConnection("close").
                addWWWAuthenticate("Basic realm=" + realm).
                build();
    }

    public static Response default404Response() {
        return new ResponseBuilder().
                addProtocol("HTTP/1.1").
                addStatusCode(404).
                addStatusMessage("Not Found").
                addConnection("close").
                build();
    }

    public static Response default405Response() {
        return new ResponseBuilder().
                addProtocol("HTTP/1.1").
                addStatusCode(405).
                addStatusMessage("Method Not Allowed").
                addConnection("close").
                build();
    }

    public static Response default411Response() {
        return new ResponseBuilder().
                addProtocol("HTTP/1.1").
                addStatusCode(411).
                addStatusMessage("Length Required").
                addConnection("close").
                build();
    }

    // Image credit to Hayley Jane Wakenshaw at http://www.ascii-code.com/ascii-art/food-and-drinks/coffee-and-tea.php
    public static Response default418Response() {
        return new ResponseBuilder().
                addProtocol("HTTP/1.1").
                addStatusCode(418).
                addStatusMessage("I'm a teapot").
                addConnection("close").
                addBody("I'm a teapot" + FormattedStrings.CRLF +
                        "             ;,'" + FormattedStrings.CRLF +
                        "     _o_    ;:;'" + FormattedStrings.CRLF +
                        " ,-.'---`.__ ;" + FormattedStrings.CRLF +
                        "((j`=====',-'" + FormattedStrings.CRLF +
                        " `-\\     /" + FormattedStrings.CRLF +
                        "    `-=-'     hjw").
                build();

    }

}
