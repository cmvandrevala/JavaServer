package http_response;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class HTTPResponse {

    private Hashtable<String, String> request;

    public HTTPResponse(Hashtable<String,String> tokenizedRequest) {
        this.request = emptyRequest();
        this.request.putAll(tokenizedRequest);
    }

    public String response() {
        if(request.get("Body").equals("")) {
            return this.request.get("Protocol") + " " +
                    this.request.get("Status-Code") + " " +
                    this.request.get("Message") + "\r\n" +
                    "Content-Type: " + this.request.get("Content-Type") + "\r\n" +
                    "Content-Length: 0\r\n" +
                    "Connection: " + this.request.get("Connection") + "\r\n";
        } else {
            return this.request.get("Protocol") + " " +
                    this.request.get("Status-Code") + " " +
                    this.request.get("Message") + "\r\n" +
                    "Content-Type: " + this.request.get("Content-Type") + "\r\n" +
                    "Content-Length: " + contentLength(this.request.get("Body")) + "\r\n" +
                    "Connection: " + this.request.get("Connection") + "\r\n\r\n" + this.request.get("Body");
        }
    }

    public int statusCode() {
        return Integer.parseInt(this.request.get("Status-Code"));
    }

    private Hashtable<String,String> emptyRequest() {
        Hashtable<String, String> emptyHashtable = new Hashtable<String, String>();
        emptyHashtable.put("Protocol", "HTTP/1.1");
        emptyHashtable.put("Status-Code", "404");
        emptyHashtable.put("Message", "Not Found");
        emptyHashtable.put("Content-Type", "text/html");
        emptyHashtable.put("Connection", "close");
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
