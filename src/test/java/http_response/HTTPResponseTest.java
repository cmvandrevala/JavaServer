package http_response;

import org.junit.Before;
import org.junit.Test;

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
        String expectedResponse = "HTTP/1.1 400 Bad Request\r\nContent-Type: text/html\r\nContent-Length: 0\r\nConnection: close\r\n";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void responseWithHeaderAndNoBody() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 0\r\nConnection: close\r\n";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void notFoundResponseForNoParams() throws Exception {
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\nContent-Length: 0\r\nConnection: close\r\n";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void responseWithHeaderAndBody() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Body", "Hello World!");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 12\r\nConnection: close\r\n\r\nHello World!";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void responseWithDifferentHeaderAndBody() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Body", "Tiny String");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 11\r\nConnection: close\r\n\r\nTiny String";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void htmlResponseWithOnlyTags() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Body", "<html><body></body></html>");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 26\r\nConnection: close\r\n\r\n<html><body></body></html>";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void htmlResponseWithTagsAndContent() throws Exception {
        params.put("Status-Code", "200");
        params.put("Message", "OK");
        params.put("Body", "<html><body><p>Content here</p></body></html>");
        HTTPResponse httpResponse = new HTTPResponse(params);
        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 45\r\nConnection: close\r\n\r\n<html><body><p>Content here</p></body></html>";
        assertEquals(expectedResponse, httpResponse.responseString());
    }

    @Test
    public void itReturnsTheStatusCode() throws Exception {
        HTTPResponse httpResponse = new HTTPResponse(params);
        assertEquals(404, httpResponse.statusCode());
    }

}