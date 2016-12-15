package http_response;

import org.junit.Before;
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
    public void itReturnsA400StringForAnEmptyResponse() {
        String expectedResponse = "HTTP/1.1 400 Bad Request" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsA404StringForANotFoundResponse() {
        String expectedResponse = "HTTP/1.1 404 Not Found" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode("404").addProtocol("HTTP/1.1").addStatusMessage("Not Found").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsA405StringForAMethodNotAllowedResponse() {
        String expectedResponse = "HTTP/1.1 405 Method Not Allowed" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode("405").addProtocol("HTTP/1.1").addStatusMessage("Method Not Allowed").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

    @Test
    public void itReturnsA411StringForALengthRequiredResponse() {
        String expectedResponse = "HTTP/2.0 411 Length Required" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode("411").addProtocol("HTTP/2.0").addStatusMessage("Length Required").build();
        assertEquals(expectedResponse, writer.writeHttpResponse(response));
    }

}
