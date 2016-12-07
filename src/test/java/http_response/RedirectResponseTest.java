package http_response;

import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import static junit.framework.TestCase.assertEquals;

public class RedirectResponseTest {

    private RedirectResponse response;

    @Before
    public void setup() {
        response = new RedirectResponse();
    }

    @Test
    public void statusCode() {
        assertEquals(302, response.statusCode());
    }

    @Test
    public void responseString() {
        String expectedResponse = "HTTP/1.1 302 Found" + FormattedStrings.CRLF + "Location: localhost:5000" + FormattedStrings.CRLF;
        assertEquals(expectedResponse, response.responseString());
    }
}
