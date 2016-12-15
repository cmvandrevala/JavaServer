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
    public void statusCodeIsEmptyByDefault() {
        assertEquals("", emptyResponse.statusCode());
    }

    @Test
    public void itHasAStatusCode() {
        assertEquals("200", response.statusCode());
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
}
