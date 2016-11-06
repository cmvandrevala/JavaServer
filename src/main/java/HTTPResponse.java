import java.io.IOException;

public class HTTPResponse {

    public String notFoundResponse() {
        HTTPResponse header = new HTTPResponse();
        return  header.notFound404StatusCode +
                header.contentType +
                header.contentLength("") +
                header.connection;
    }

    public String successNoBodyResponse() {
        HTTPResponse header = new HTTPResponse();
        return  header.success200StatusCode +
                header.contentType +
                header.contentLength("") +
                header.connection;
    }

    public String response(String content) {
        HTTPResponse header = new HTTPResponse();
        return  header.success200StatusCode +
                header.contentType +
                header.contentLength(content) +
                header.connection +
                header.spaceBetweenHeaderAndContent +
                content;
    }

    private String success200StatusCode = "HTTP/1.1 200 OK\r\n";
    private String notFound404StatusCode = "HTTP/1.1 404 Not Found\r\n";

    private String contentType = "Content-Type: text/html\r\n";
    private String connection = "Connection: close\r\n";
    private String spaceBetweenHeaderAndContent = "\r\n";

    private String contentLength(String content) {
        int contentLength = 0;
        try {
            contentLength = content.getBytes("UTF-8").length;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "Content-Length: " + contentLength + "\r\n";
    }
}
