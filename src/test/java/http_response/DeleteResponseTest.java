package http_response;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;

public class DeleteResponseTest {

    private DeleteResponse response;

    @Before
    public void setup() {
        RequestBuilder builder = new RequestBuilder();
        Request request = builder.addUrl("/foo").build();
        response = new DeleteResponse(request);
    }

    @Test
    public void putResponse() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "";
        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void statusCode() throws Exception {
        assertEquals(200, response.statusCode());
    }

}