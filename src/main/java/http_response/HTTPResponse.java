package http_response;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class HTTPResponse {

    private Hashtable<String, String> request;

    public HTTPResponse(Hashtable<String,String> params) {
        this.request = emptyRequest();
        this.request.putAll(params);
    }

    public String responseString() {
        if(responseHasNoBody()) {
            return responseWithNoBody();
        } else if(responseHasBody()) {
            return responseWithBody();
        } else {
            return optionsResponseSpecialCase();
        }
    }

    public int statusCode() {
        return Integer.parseInt(this.request.get("Status-Code"));
    }

    private String responseWithNoBody() {
        return this.request.get("Protocol") + " " +
                this.request.get("Status-Code") + " " +
                this.request.get("Message") + "\r\n" +
                "Content-Type: " + this.request.get("Content-Type") + "\r\n" +
                "Content-Length: 0\r\n" +
                "Connection: " + this.request.get("Connection") + "\r\n";
    }

    private String responseWithBody() {
        return this.request.get("Protocol") + " " +
                this.request.get("Status-Code") + " " +
                this.request.get("Message") + "\r\n" +
                "Content-Type: " + this.request.get("Content-Type") + "\r\n" +
                "Content-Length: " + contentLength(this.request.get("Body")) + "\r\n" +
                "Connection: " + this.request.get("Connection") + "\r\n\r\n" +
                this.request.get("Body");
    }

    private String optionsResponseSpecialCase() {
        return this.request.get("Protocol") + " " +
                this.request.get("Status-Code") + " " +
                this.request.get("Message") + "\r\n" +
                "Allow: " + this.request.get("Allow") + "\r\n" +
                "Server: My Java Server" + "\r\n" +
                "Content-Length: 0";
    }

    private boolean responseHasNoBody() {
        return this.request.get("Body").equals("") && this.request.get("Allow").equals("");
    }

    private boolean responseHasBody() {
        return !this.request.get("Body").equals("") && this.request.get("Allow").equals("");
    }

    private Hashtable<String,String> emptyRequest() {
        Hashtable<String, String> emptyHashtable = new Hashtable<String, String>();
        emptyHashtable.put("Protocol", "HTTP/1.1");
        emptyHashtable.put("Status-Code", "404");
        emptyHashtable.put("Message", "Not Found");
        emptyHashtable.put("Content-Type", "text/html");
        emptyHashtable.put("Connection", "close");
        emptyHashtable.put("Body", "");
        emptyHashtable.put("Allow", "");
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
