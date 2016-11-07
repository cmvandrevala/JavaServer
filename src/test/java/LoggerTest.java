import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LoggerTest {

    private Date date;
    private Logger logger;

    @Before
    public void setup() throws IOException {
        date = new Date();
        logger = new Logger();
    }

    @Test
    public void serverHasBeenStartedMessage() throws Exception {
        String expectedResponse = "[ " + date + "] The server has been started on 127.168.241.121:5880";
        assertEquals(expectedResponse, logger.serverHasBeenStartedMessage(date, "127.168.241.121", 5880));
    }

    @Test
    public void serverHasBeenStoppedMessage() throws Exception {
        String expectedResponse = "[ " + date + "] The server running on 127.168.241.121:5880 has been stopped";
        assertEquals(expectedResponse, logger.serverHasBeenStoppedMessage(date, "127.168.241.121", 5880));
    }

    @Test
    public void clientHasConnectedMessage() throws Exception {
        String expectedResponse = "[ " + date + "] The client has connected on 127.168.241.121:5880";
        assertEquals(expectedResponse, logger.clientHasConnectedMessage(date, "127.168.241.121:5880"));
    }

    @Test
    public void clientHasDisconnectedMessage() throws Exception {
        String expectedResponse = "[ " + date + "] The client at 127.168.241.121:5880 has disconnected";
        assertEquals(expectedResponse, logger.clientHasDisconnectedMessage(date, "127.168.241.121:5880"));
    }

    @Test
    public void resourceRequestedMessage() throws Exception {
        String expectedResponse = "[ " + date + "] The client sent a GET request to /foo";
        assertEquals(expectedResponse, logger.resourceRequestedMessage(date, "GET", "/foo"));
    }

    @Test
    public void resourceDeliveredMessage() throws Exception {
        String expectedResponse = "[ " + date + "] The server replied to the GET request to /foo with a status code of 200";
        assertEquals(expectedResponse, logger.resourceDeliveredMessage(date, "GET", "/foo", 200));
    }

}