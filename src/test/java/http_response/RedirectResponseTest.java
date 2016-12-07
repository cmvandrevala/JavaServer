package http_response;

import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import static junit.framework.TestCase.assertEquals;

public class RedirectResponseTest {

    private RedirectResponse responseWithDefaltURL;
    private RedirectResponse responseWithCustomURL;

    @Before
    public void setup() {
        this.responseWithCustomURL = new RedirectResponse("my.url.com");
        this.responseWithDefaltURL = new RedirectResponse();
    }

    @Test
    public void statusCodeForDefaultURL() {
        assertEquals(302, responseWithDefaltURL.statusCode());
    }

    @Test
    public void responseStringForDefaultURL() {
        String expectedResponse = "HTTP/1.1 302 Found" + FormattedStrings.CRLF + "Location: http://localhost:5000/" + FormattedStrings.CRLF;
        assertEquals(expectedResponse, responseWithDefaltURL.responseString());
    }

    @Test
    public void statusCodeForCustomURL() {
        assertEquals(302, responseWithCustomURL.statusCode());
    }

    @Test
    public void responseStringForCustomtURL() {
        String expectedResponse = "HTTP/1.1 302 Found" + FormattedStrings.CRLF + "Location: my.url.com" + FormattedStrings.CRLF;
        assertEquals(expectedResponse, responseWithCustomURL.responseString());
    }

}
