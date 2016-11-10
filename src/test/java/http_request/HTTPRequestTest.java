package http_request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPRequestTest {

    private HTTPRequest emptyRequest;
    private HTTPRequest request;
    private String getRequest = "GET / HTTP/1.1\n" +
                                "Host: localhost:5000\n" +
                                "User-Agent: curl/7.50.3\n" +
                                "Accept: */*";

    @Before
    public void setup() {
        emptyRequest = new HTTPRequest("");
        request = new HTTPRequest(getRequest);
    }

    @Test
    public void verbIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.verb);
    }

    @Test
    public void urlIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.url);
    }

    @Test
    public void protocolIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.protocol);
    }

    @Test
    public void hostIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.host);
    }

    @Test
    public void userAgentIsEmptyIfThereIsNoInput() {
        assertEquals("", emptyRequest.userAgent);
    }

    @Test
    public void verbIsGetForGetRequests() {
        assertEquals("GET", request.verb);
    }

    @Test
    public void urlIsGivenForGetRequests() {
        assertEquals("/", request.url);
    }

    @Test
    public void protocolIsGivenForGetRequest() {
        assertEquals("HTTP/1.1", request.protocol);
    }

    @Test
    public void hostIsGivenForGetRequest() {
        assertEquals("localhost:5000", request.host);
    }

    @Test
    public void userAgentIsGivenForGetRequests() {
        assertEquals("curl/7.50.3", request.userAgent);
    }

}
