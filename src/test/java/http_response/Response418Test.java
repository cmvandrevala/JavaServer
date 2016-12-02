package http_response;

import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import static junit.framework.TestCase.assertEquals;

public class Response418Test {

    private Response418 response;

    @Before
    public void setup() {
        response = new Response418();
    }

    @Test
    public void theResponseIncludesAnAdorableTeapot() throws Exception {
        String expectedResponse = "HTTP/1.1 418 I'm a teapot" + FormattedStrings.CRLF +
                "Content-Type: text/html" + FormattedStrings.CRLF +
                "Content-Length: 107" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF +
                "I'm a teapot" + FormattedStrings.CRLF +
                "             ;,'" + FormattedStrings.CRLF +
                "     _o_    ;:;'" + FormattedStrings.CRLF +
                " ,-.'---`.__ ;" + FormattedStrings.CRLF +
                "((j`=====',-'" + FormattedStrings.CRLF +
                " `-\\     /" + FormattedStrings.CRLF +
                "    `-=-'     hjw" + FormattedStrings.CRLF;
        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itReturnsTheStatusCode() throws Exception {
        assertEquals(418, response.statusCode());
    }

}