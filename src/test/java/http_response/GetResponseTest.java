package http_response;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;

public class GetResponseTest {

    private GetResponse response;

    @Before
    public void setup() {
        RequestBuilder builder = new RequestBuilder();
        Request request = builder.addUrl("/").build();
        response = new GetResponse(request);
    }

    @Test
    public void getResponse() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 21" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "<h1>Hello World!</h1>" + FormattedStrings.CRLF;
        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itReturnsTheStatusCode() throws Exception {
        assertEquals(200, response.statusCode());
    }

}