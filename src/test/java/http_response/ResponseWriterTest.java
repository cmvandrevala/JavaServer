package http_response;

import http_action.ReadFromFileAction;
import http_request.Request;
import http_request.RequestBuilder;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.PathToUrlMapper;
import routing.Router;
import routing.RoutesTable;
import utilities.FormattedStrings;

import static org.junit.Assert.assertTrue;

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
        String firstLine = "HTTP/1.1 400 Bad Method";
        String headersWithoutDate = "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode(400).addProtocol("HTTP/1.1").addStatusMessage("Bad Method").addConnection("close").addContentType("text/html").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsA401StringForAnUnauthorizedResponse() {
        String firstLine = "HTTP/1.1 401 Unauthorized";
        String headersWithoutDate = "Content-Type: text/html" + FormattedStrings.CRLF + "WWW-Authenticate: Basic realm=\"some-realm\"" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode(401).addProtocol("HTTP/1.1").addStatusMessage("Unauthorized").addConnection("close").addContentType("text/html").addWWWAuthenticate("Basic realm=\"some-realm\"").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsRedirectResponse() {
        String firstLine = "HTTP/1.1 302 Found";
        String headersWithoutDate = "Location: tuple" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode(302).addProtocol("HTTP/1.1").addStatusMessage("Found").addLocation("tuple").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsA404StringForANotFoundResponse() {
        String firstLine = "HTTP/1.1 404 Not Found";
        String headersWithoutDate = "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode(404).addProtocol("HTTP/1.1").addStatusMessage("Not Found").addConnection("close").addContentType("text/html").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsA405StringForAMethodNotAllowedResponse() {
        String firstLine = "HTTP/1.1 405 Method Not Allowed";
        String headersWithoutDate = "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode(405).addProtocol("HTTP/1.1").addStatusMessage("Method Not Allowed").addConnection("close").addContentType("text/html").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsA411StringForALengthRequiredResponse() {
        String firstLine = "HTTP/2.0 411 Length Required";
        String headersWithoutDate = "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode(411).addProtocol("HTTP/2.0").addStatusMessage("Length Required").addConnection("close").addContentType("text/html").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsAResponseWithABody() {
        String firstLine = "HTTP/2.0 200 OK";
        String headersWithoutDate = "Content-Type: text/jpeg" + FormattedStrings.CRLF + "Content-Length: 10" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "aaaaaaaaaa";
        Response response = builder.addStatusCode(200).addProtocol("HTTP/2.0").addStatusMessage("OK").addConnection("close").addContentType("text/jpeg").addContentType("text/jpeg").addBody("aaaaaaaaaa").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsAResponseWithACookieHeader() {
        String firstLine = "HTTP/1.1 200 OK";
        String headersWithoutDate = "Content-Type: text/html" + FormattedStrings.CRLF + "Set-Cookie: plound" + FormattedStrings.CRLF + "Content-Length: 6" + FormattedStrings.CRLF + "Connection: keep-alive" + FormattedStrings.CRLF + FormattedStrings.CRLF + "yogurt";
        Response response = builder.addStatusCode(200).addProtocol("HTTP/1.1").addStatusMessage("OK").addConnection("keep-alive").addContentType("text/html").addSetCookie("plound").addBody("yogurt").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsAResponseWithAnEtag() {
        String firstLine = "HTTP/1.1 200 OK";
        String headersWithoutDate = "ETag: abc123" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Set-Cookie: Cookie=foo" + FormattedStrings.CRLF + "Content-Length: 8" + FormattedStrings.CRLF + "Connection: keep-alive" + FormattedStrings.CRLF + FormattedStrings.CRLF + "sandwich";
        Response response = builder.addStatusCode(200).addProtocol("HTTP/1.1").addStatusMessage("OK").addConnection("keep-alive").addContentType("text/html").addSetCookie("Cookie=foo").addBody("sandwich").addETag("abc123").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsASuccessful204Response() {
        String firstLine = "HTTP/1.1 204 No Content";
        String headersWithoutDate = "Content-Location: /file.txt" + FormattedStrings.CRLF + "ETag: abc123" + FormattedStrings.CRLF;
        Response response = builder.addStatusCode(204).addProtocol("HTTP/1.1").addStatusMessage("No Content").addContentType("text/html").addETag("abc123").addContentLocation("/file.txt").build();
        assertTrue(writer.writeHttpResponse(response).contains(firstLine));
        assertTrue(writer.writeHttpResponse(response).contains("Date: "));
        assertTrue(writer.writeHttpResponse(response).contains(headersWithoutDate));
    }

    @Test
    public void itReturnsAContentRangeResponse() {
        Response response = builder.addStatusCode(206).addProtocol("HTTP/1.1").addStatusMessage("Partial Content").addContentType("text/html").addContentRange("bytes 0-4").build();
        assertTrue(writer.writeHttpResponse(response).contains("Content-Range: bytes 0-4"));
    }

    @Test
    public void itReturnsAGIFImageInAResponse() {
        RoutesTable routesTable = new RoutesTable();
        DataTable dataTable = new DataTable();
        PathToUrlMapper mapper = new PathToUrlMapper("public/");
        routesTable.addRoute("/image.gif", RoutesTable.Verb.GET, new ReadFromFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/image.gif").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        TestCase.assertTrue(writer.writeHttpResponse(response).contains("Content-Type: image/gif"));
        TestCase.assertFalse(writer.writeHttpResponse(response).contains("Content-Length: 0"));
    }

}