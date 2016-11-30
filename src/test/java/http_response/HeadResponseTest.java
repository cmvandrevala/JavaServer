package http_response;

import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import static junit.framework.TestCase.assertEquals;

public class HeadResponseTest {

    private HeadResponse response;

    @Before
    public void setup() {
        response = new HeadResponse();
    }

    @Test
    public void notFoundResponseForNoParams() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "";
        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itReturnsTheStatusCode() throws Exception {
        assertEquals(200, response.statusCode());
    }

}