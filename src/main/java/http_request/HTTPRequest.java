package http_request;

public class HTTPRequest {

    public String verb = "";
    public String url = "";
    public String protocol = "";
    public String host = "";
    public String userAgent = "";

    public HTTPRequest(String requestString) {
        String[] httpRequestLines = requestString.split("\n");
        if(httpRequestLines.length > 1) {
            String[] firstLine = httpRequestLines[0].split(" ");
            String[] secondLine = httpRequestLines[1].split(" ");
            String[] thirdLine = httpRequestLines[2].split(" ");
            this.verb = firstLine[0];
            this.url = firstLine[1];
            this.protocol = firstLine[2];
            this.host = secondLine[1];
            this.userAgent = thirdLine[1];
        }
    }

}
