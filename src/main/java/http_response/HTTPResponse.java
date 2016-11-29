package http_response;

import utilities.FormattedStrings;

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
                this.request.get("Message") + FormattedStrings.CRLF +
                "Content-Type: " + this.request.get("Content-Type") + FormattedStrings.CRLF +
                "Content-Length: 0" + FormattedStrings.CRLF +
                "Connection: " + this.request.get("Connection") + FormattedStrings.CRLF;
    }

    private String responseWithBody() {
        return this.request.get("Protocol") + " " +
                this.request.get("Status-Code") + " " +
                this.request.get("Message") + FormattedStrings.CRLF +
                "Content-Type: " + this.request.get("Content-Type") + FormattedStrings.CRLF +
                "Content-Length: " + contentLength(this.request.get("Body")) + FormattedStrings.CRLF +
                "Connection: " + this.request.get("Connection") + FormattedStrings.CRLF + FormattedStrings.CRLF +
                this.request.get("Body");
    }

    private String optionsResponseSpecialCase() {
        return this.request.get("Protocol") + " " +
                this.request.get("Status-Code") + " " +
                this.request.get("Message") + FormattedStrings.CRLF +
                "Allow: " + this.request.get("Allow") + FormattedStrings.CRLF +
                "Server: My Java Server" + FormattedStrings.CRLF +
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
