package http_response;

import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;

public class HTTPResponseTest {
    
    private Hashtable<String, String> params;

    @Before
    public void setup() {
        params = new Hashtable<String, String>();
    }

    @Test
    public void responseFromEmptyRequest() throws Exception {
        params.put("Status-Code", "400");
        params.put("Message", "Bad Request");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 400 Bad Request" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void responseWithHeaderAndNoBody() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void notFoundResponseForNoParams() throws Exception {
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 404 Not Found" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void responseWithHeaderAndBody() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Body", "Hello World!");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 12" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "" + FormattedStrings.CRLF + "Hello World!";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void responseWithDifferentHeaderAndBody() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Body", "Tiny String");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 11" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "" + FormattedStrings.CRLF + "Tiny String";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void htmlResponseWithOnlyTags() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Body", "<html><body></body></html>");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 26" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "" + FormattedStrings.CRLF + "<html><body></body></html>";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void htmlResponseWithTagsAndContent() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Body", "<html><body><p>Content here</p></body></html>");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 45" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + "" + FormattedStrings.CRLF + "<html><body><p>Content here</p></body></html>";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void itReturnsTheStatusCode() throws Exception {
        HTTPResponse httpResponse = new HTTPResponse(params);
        assertEquals(404, httpResponse.statusCode());
    }

}