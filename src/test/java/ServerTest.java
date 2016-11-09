import org.junit.After;
import org.junit.Before;
import server.Server;

import java.io.IOException;

public class ServerTest {

    Server defaultServer = null;

    @Before
    public void setup() throws IOException {
        defaultServer = new Server();
    }

    @After
    public void tearDown() {
        defaultServer.tearDown();
        defaultServer = null;
    }

}