package logging;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CobSpecMessagesTest {

    private Date date;
    private CobSpecMessages messages;

    @Before
    public void setup() throws IOException {
        date = new Date();
        messages = new CobSpecMessages();
    }

    @Test
    public void serverHasBeenStartedMessage() throws Exception {
        String expectedResponse = "Server Started";
        assertEquals(expectedResponse, messages.serverHasBeenStartedMessage(date, "127.168.241.121", 5880));
    }

    @Test
    public void serverHasBeenStoppedMessage() throws Exception {
        String expectedResponse = "Server Stopped";
        assertEquals(expectedResponse, messages.serverHasBeenStoppedMessage(date, "127.168.241.121", 5880));
    }

    @Test
    public void clientHasConnectedMessage() throws Exception {
        String expectedResponse = "Client Connected";
        assertEquals(expectedResponse, messages.clientHasConnectedMessage(date, "127.168.241.121:5880"));
    }

    @Test
    public void clientHasDisconnectedMessage() throws Exception {
        String expectedResponse = "Client Disconnected";
        assertEquals(expectedResponse, messages.clientHasDisconnectedMessage(date, "127.168.241.121:5880"));
    }

    @Test
    public void resourceRequestedMessage() throws Exception {
        String expectedResponse = "Request: GET /foo HTTP/1.1";
        assertEquals(expectedResponse, messages.resourceRequestedMessage(date, "GET", "/foo"));
    }

    @Test
    public void resourceDeliveredMessage() throws Exception {
        String expectedResponse = "Response: GET /foo HTTP/1.1";
        assertEquals(expectedResponse, messages.resourceDeliveredMessage(date, "GET", "/foo", 200));
    }
}
