package http_response;

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
}
