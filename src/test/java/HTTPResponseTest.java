import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class HTTPResponseTest {

    private HTTPResponse httpResponse;

    @Before
    public void setup() throws IOException {
        httpResponse = new HTTPResponse();
    }

    @Test
    public void responseWithHeaderAndNoBody() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 0\r\nConnection: close\r\n";
        assertEquals(expectedResponse, httpResponse.successNoBodyResponse());
    }

    @Test
    public void notFoundResponse() throws Exception {
        String expectedResponse = "HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\nContent-Length: 0\r\nConnection: close\r\n";
        assertEquals(expectedResponse, httpResponse.notFoundResponse());
    }

    @Test
    public void responseWithHeaderAndBody() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 12\r\nConnection: close\r\n\r\nHello World!";
        assertEquals(expectedResponse, httpResponse.response("Hello World!"));
    }

    @Test
    public void responseWithDifferentHeaderAndBody() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 11\r\nConnection: close\r\n\r\nTiny String";
        assertEquals(expectedResponse, httpResponse.response("Tiny String"));
    }

    @Test
    public void htmlResponseWithOnlyTags() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 26\r\nConnection: close\r\n\r\n<html><body></body></html>";
        assertEquals(expectedResponse, httpResponse.response("<html><body></body></html>"));
    }

    @Test
    public void htmlResponseWithTagsAndContent() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 45\r\nConnection: close\r\n\r\n<html><body><p>Content here</p></body></html>";
        assertEquals(expectedResponse, httpResponse.response("<html><body><p>Content here</p></body></html>"));
    }

}