import java.io.IOException;

public class HTTPHeader {

    public String statusCode = "HTTP/1.1 200 OK\n";
    public String contentType = "Content-Type: text/plain\n";
    public String connection = "Connection: close\n";
    public String spaceBetweenHeaderAndContent = "\n";

    public String contentLength(String content) {
        int contentLength = 0;
        try {
            contentLength = content.getBytes("UTF-8").length;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "Content-Length: " + contentLength + "\n";
    }
}
