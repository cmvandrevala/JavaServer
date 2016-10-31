import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class ServerTest {

    Server defaultServer = null;
    Server differentServer = null;

    @Before
    public void setup() throws IOException {
        defaultServer = new Server();
        differentServer = new Server(6000);
    }

    @After
    public void tearDown() {
        defaultServer.tearDown();
        differentServer.tearDown();
        defaultServer = null;
        differentServer = null;
    }

    @Test
    public void defaultPortIs5000() throws Exception {
        assertEquals(5000, defaultServer.port());
    }

    @Test
    public void thePortCanBeSetAtInitialization() throws Exception {
        assertEquals(6000, differentServer.port());
    }

    @Test
    public void theServerSocketListensOnTheDefaultPort() throws Exception {
        assertEquals(5000, defaultServer.serverSocket().getLocalPort());
    }

    @Test
    public void theServerSocketListensOnNonDefaultPort() throws Exception {
        assertEquals(6000, differentServer.serverSocket().getLocalPort());
    }

    @Test
    public void responseWithHeaderAndNoBody() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK\nContent-Type: text/html\nContent-Length: 0\nConnection: close\n";
        assertEquals(expectedResponse, defaultServer.successNoBodyResponse());
    }

    @Test
    public void responseWithHeaderAndBody() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK\nContent-Type: text/html\nContent-Length: 12\nConnection: close\n\nHello World!";
        assertEquals(expectedResponse, differentServer.response("Hello World!"));
    }

    @Test
    public void notFoundResponse() throws Exception {
        String expectedResponse = "HTTP/1.1 404 Not Found\nContent-Type: text/html\nContent-Length: 0\nConnection: close\n";
        assertEquals(expectedResponse, differentServer.notFoundResponse());
    }

}