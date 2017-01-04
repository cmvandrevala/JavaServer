package http_response;

import http_action.RedirectAction;
import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;

public class ResponseGeneratorTest {

    private DataTable dataTable;
    private RoutesTable routesTable;
    private ResponseGenerator responseGenerator;

    @Before
    public void setup() {
        this.routesTable = new RoutesTable();
        this.routesTable.addRoute("/redirect", RoutesTable.Verb.GET, new RedirectAction("new-url.com"));
        this.routesTable.addRoute("/options", RoutesTable.Verb.GET);
        this.routesTable.addRoute("/patch", RoutesTable.Verb.PATCH);
        this.dataTable = new DataTable();
        this.responseGenerator = new ResponseGenerator(this.routesTable, this.dataTable);
    }

    @Test
    public void theBoundsDefaultToTheEntireBody() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").build();
        assertEquals("ABCDEFG", responseGenerator.partialContent(request));
    }

    @Test
    public void itReturnsAStringOverAGivenRange() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=0-4").build();
        assertEquals("ABCD", responseGenerator.partialContent(request));
    }

    @Test
    public void itReturnsAByteRangeSpecFromTheEnd() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=-5").build();
        assertEquals("CDEFG", responseGenerator.partialContent(request));
    }

    @Test
    public void itReturnsAByteRangeSpecFromTheBeginning() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=2-").build();
        assertEquals("CDEFG", responseGenerator.partialContent(request));
    }

    @Test
    public void itReturnsARedirectResponseWithTheRightLocation() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals("new-url.com", response.location());
    }

    @Test
    public void itReturnsARedirectResponseWithTheRightStatusCode() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals(302, response.statusCode());
    }

    @Test
    public void itReturnsARedirectResponseWithTheRightStatusMessage() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals("Found", response.statusMessage());
    }

    @Test
    public void itReturnsARedirectResponseWithTheRightProtocol() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals("HTTP/1.1", response.protocol());
    }

    @Test
    public void itReturnsAGetResponseWithTheRightStatusCode() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void itReturnsAGetResponseWithTheRightStatusMessage() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals("OK", response.statusMessage());
    }

    @Test
    public void itReturnsAnOptionsResponseWithTheRightStatusCode() {
        Request request = new RequestBuilder().addVerb("OPTIONS").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void itReturnsAnOptionsResponseWithTheRightStatusMessage() {
        Request request = new RequestBuilder().addVerb("OPTIONS").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals("OK", response.statusMessage());
    }

    @Test
    public void itReturnsAnOptionsResponseWithTheRightAllowString() {
        Request request = new RequestBuilder().addVerb("OPTIONS").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals("OPTIONS,GET", response.allow());
    }

    @Test
    public void itReturnsAPatchResponseWithTheRightStatusCode() {
        Request request = new RequestBuilder().addVerb("PATCH").addUrl("/patch").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals(204, response.statusCode());
    }

    @Test
    public void itReturnsAPatchResponseWithTheRightStatusMessage() {
        Request request = new RequestBuilder().addVerb("PATCH").addUrl("/patch").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request);
        assertEquals("No Content", response.statusMessage());
    }

}
