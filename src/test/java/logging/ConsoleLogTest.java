package logging;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ConsoleLogTest {

    private ConsoleLog consoleLog;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setup() throws Exception {
        System.setOut(new PrintStream(out));
        MockMessages mockMessages = new MockMessages();
        consoleLog = new ConsoleLog(mockMessages);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    @Test
    public void serverHasBeenStarted() throws Exception {
        String expectedResponse = "serverHasBeenStarted was called with 123 and 456\n";
        consoleLog.serverHasBeenStarted("123", 456);
        assertEquals(expectedResponse, out.toString());
    }

    @Test
    public void clientHasConnected() throws Exception {
        String expectedResponse = "clientHasConnected was called with xyz\n";
        consoleLog.clientHasConnected("xyz");
        assertEquals(expectedResponse, out.toString());
    }

    @Test
    public void clientHasDisconnected() throws Exception {
        String expectedResponse = "clientHasDisconnected was called with a1b2c3\n";
        consoleLog.clientHasDisconnected("a1b2c3");
        assertEquals(expectedResponse, out.toString());
    }

    @Test
    public void resourceRequested() throws Exception {
        String expectedResponse = "resourceRequested was called with GET and /foo\n";
        consoleLog.resourceRequested("GET", "/foo");
        assertEquals(expectedResponse, out.toString());
    }

    @Test
    public void resourceDelivered() throws Exception {
        String expectedResponse = "resourceDelivered was called with GET, /foo, and 501\n";
        consoleLog.resourceDelivered("GET", "/foo", 501);
        assertEquals(expectedResponse, out.toString());
    }
}
