package http_response;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import utilities.FormattedStrings;

import static junit.framework.TestCase.assertEquals;

public class ResponseWriterTest {

    private ResponseBuilder builder;
    private ResponseWriter writer;

    @Before
    public void setup() {
        builder = new ResponseBuilder();
        writer = new ResponseWriter();
    }

    @Test
    public void itReturnsA400StringForANotFoundResponse() {
        String expectedResponse = "HTTP/1.1 400 Bad Method" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode("400").addProtocol("HTTP/1.1").addStatusMessage("Bad Method").addConnection("close").addContentType("text/html").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsRedirectResponse() {
        String expectedResponse = "HTTP/1.1 302 Found" + FormattedStrings.CRLF + "Location: tuple" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode("302").addProtocol("HTTP/1.1").addStatusMessage("Found").addLocation("tuple").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsA404StringForANotFoundResponse() {
        String expectedResponse = "HTTP/1.1 404 Not Found" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode("404").addProtocol("HTTP/1.1").addStatusMessage("Not Found").addConnection("close").addContentType("text/html").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsA405StringForAMethodNotAllowedResponse() {
        String expectedResponse = "HTTP/1.1 405 Method Not Allowed" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode("405").addProtocol("HTTP/1.1").addStatusMessage("Method Not Allowed").addConnection("close").addContentType("text/html").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsA411StringForALengthRequiredResponse() {
        String expectedResponse = "HTTP/2.0 411 Length Required" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode("411").addProtocol("HTTP/2.0").addStatusMessage("Length Required").addConnection("close").addContentType("text/html").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsAResponseWithABody() {
        String expectedResponse = "HTTP/2.0 200 OK" + FormattedStrings.CRLF + "Content-Type: text/jpeg" + FormattedStrings.CRLF + "Content-Length: 10" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "aaaaaaaaaa";
        Response response = builder.addStatusCode("200").addProtocol("HTTP/2.0").addStatusMessage("OK").addConnection("close").addContentType("text/jpeg").addContentType("text/jpeg").addBody("aaaaaaaaaa").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsAResponseWithACookieHeader() {
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Set-Cookie: plound" + FormattedStrings.CRLF + "Content-Length: 6" + FormattedStrings.CRLF + "Connection: keep-alive" + FormattedStrings.CRLF + FormattedStrings.CRLF + "yogurt";
        Response response = builder.addStatusCode("200").addProtocol("HTTP/1.1").addStatusMessage("OK").addConnection("keep-alive").addContentType("text/html").addSetCookie("plound").addBody("yogurt").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

}
