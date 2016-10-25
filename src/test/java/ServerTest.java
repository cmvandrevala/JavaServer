import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ServerTest {

    @Test
    public void responseWithHeaderAndNoBody() {
        Server server = new Server();
        String expectedResponse = "HTTP/1.1 200 OK\nContent-Type: text/plain\nContent-Length: 0\nConnection: close\n\n";
        assertEquals(expectedResponse, server.response(""));
    }

    @Test
    public void responseWithHeaderAndBody() {
        Server server = new Server();
        String expectedResponse = "HTTP/1.1 200 OK\nContent-Type: text/plain\nContent-Length: 12\nConnection: close\n\nHello World!";
        assertEquals(expectedResponse, server.response("Hello World!"));
    }

}