import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LoggerTest {

    @Test
    public void serverHasBeenStartedMessage() throws Exception {
        Date date = new Date();
        Logger logger = new Logger();
        String expectedResponse = "[ " + date + "] The server has been started on 127.168.241.121:5880";
        assertEquals(expectedResponse, logger.serverHasBeenStartedMessage(date, "127.168.241.121", 5880));
    }

    @Test
    public void serverHasBeenStoppedMessage() throws Exception {
        Date date = new Date();
        Logger logger = new Logger();
        String expectedResponse = "[ " + date + "] The server running on 127.168.241.121:5880 has been stopped";
        assertEquals(expectedResponse, logger.serverHasBeenStoppedMessage(date, "127.168.241.121", 5880));
    }

    @Test
    public void resourceRequestedMessage() throws Exception {
        Date date = new Date();
        Logger logger = new Logger();
        String expectedResponse = "[ " + date + "] The user sent a GET request to /foo";
        assertEquals(expectedResponse, logger.resourceRequestedMessage(date, "GET", "/foo"));
    }

    @Test
    public void resourceDeliveredMessage() throws Exception {
        Date date = new Date();
        Logger logger = new Logger();
        String expectedResponse = "[ " + date + "] The server replied to the GET request to /foo with a status code of 200";
        assertEquals(expectedResponse, logger.resourceDeliveredMessage(date, "GET", "/foo", 200));
    }

}