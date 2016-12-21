package http_response;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;

public class ResponseTest {

    private Response response;
    private Response emptyResponse;
    private Hashtable<String,String> params = new Hashtable<>();

    @Before
    public void setup() {
        params.put("Protocol", "HTTP/1.1");
        params.put("Status-Code", "200");
        params.put("Status-Message", "OK");
        params.put("Content-Type", "text/html");
        params.put("Connection", "close");
        params.put("Body", "some body");
        params.put("Set-Cookie", "cookie");
        params.put("Location", "loc");
        params.put("Allow", "OPTIONS,GET,PUT");
        params.put("ETag", "12345");
        params.put("Content-Location", "/foo.txt");
        response = new Response(params);
        emptyResponse = new Response(new Hashtable<>());
    }

    @Test
    public void protocolIsEmptyByDefault() {
        assertEquals("", emptyResponse.protocol());
    }

    @Test
    public void itHasAProtocol() {
        assertEquals("HTTP/1.1", response.protocol());
    }

    @Test
    public void itHasAStatusCode() {
        assertEquals(200, response.statusCode());
    }

    @Test
    public void statusMessageIsEmptyByDefault() {
        assertEquals("", emptyResponse.statusMessage());
    }

    @Test
    public void itHasAStatusMessage() {
        assertEquals("OK", response.statusMessage());
    }

    @Test
    public void contentTypeIsEmptyByDefault() {
        assertEquals("", emptyResponse.contentType());
    }

    @Test
    public void itHasAContentType() {
        assertEquals("text/html", response.contentType());
    }

    @Test
    public void connectionIsEmptyByDefault() {
        assertEquals("", emptyResponse.connection());
    }

    @Test
    public void itHasAConnection() {
        assertEquals("close", response.connection());
    }

    @Test
    public void bodyIsEmptyByDefault() {
        assertEquals("", emptyResponse.body());
    }

    @Test
    public void contentLengthIsZeroIfTheBodyIsEmpty() {
        assertEquals("0", emptyResponse.contentLength());
    }

    @Test
    public void itHasABody() {
        assertEquals("some body", response.body());
    }

    @Test
    public void contentLengthIsCalculatedFromTheBody() {
        assertEquals("9", response.contentLength());
    }

    @Test
    public void setCookieIsEmptyByDefault() {
        assertEquals("", emptyResponse.setCookie());
    }

    @Test
    public void itHasASetCookie() {
        assertEquals("cookie", response.setCookie());
    }

    @Test
    public void locationIsEmptyByDefault() {
        assertEquals("", emptyResponse.location());
    }

    @Test
    public void itHasALocation() {
        assertEquals("loc", response.location());
    }

    @Test
    public void allowIsEmptyByDefault() {
        assertEquals("", emptyResponse.allow());
    }

    @Test
    public void itHasAnAllowField() {
        assertEquals("OPTIONS,GET,PUT", response.allow());
    }

    @Test
    public void eTagFieldIsEmptyByDefault() {
        assertEquals("", emptyResponse.etag());
    }

    @Test
    public void eTagCanBeSetToACode() {
        assertEquals("12345", response.etag());
    }

    @Test
    public void contentLocationIsEmptyByDefault() {
        assertEquals("", emptyResponse.contentLocation());
    }

    @Test
    public void contentLocationCanBeSetToAUrl() {
        assertEquals("/foo.txt", response.contentLocation());
    }

}
