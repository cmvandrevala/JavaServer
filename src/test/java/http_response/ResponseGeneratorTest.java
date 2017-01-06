package http_response;

import http_action.RedirectAction;
import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import routing.DataTable;
import routing.PathToUrlMapper;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;

public class ResponseGeneratorTest {

    private DataTable dataTable;
    private RoutesTable routesTable;
    private ResponseGenerator responseGenerator;
    private PathToUrlMapper mapper;

    @Before
    public void setup() {
        this.routesTable = new RoutesTable();
        this.routesTable.addRoute("/redirect", RoutesTable.Verb.GET, new RedirectAction("new-url.com"));
        this.routesTable.addRoute("/options", RoutesTable.Verb.GET);
        this.routesTable.addRoute("/patch", RoutesTable.Verb.PATCH);
        this.dataTable = new DataTable();
        this.mapper = new PathToUrlMapper("public/");
        this.responseGenerator = new ResponseGenerator(this.routesTable, this.dataTable);
    }

    @Test
    public void theBoundsDefaultToTheEntireBody() {
        dataTable.addBody("/partial_content.txt", "This is a file that contains text to read part of in order to fulfill a 206.");
        Request request = new RequestBuilder().addUrl("/partial_content.txt").build();
        assertEquals("This is a file that contains text to read part of in order to fulfill a 206.", responseGenerator.partialContent(request, mapper));
    }

    @Ignore
    public void itReturnsAStringOverAGivenRange() {
        dataTable.addBody("/foo", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=0-4").build();
        assertEquals("ABCD", responseGenerator.partialContent(request, mapper));
    }

    @Test
    public void itReturnsARedirectResponseWithTheRightLocation() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals("new-url.com", response.location());
    }

    @Test
    public void itReturnsARedirectResponseWithTheRightStatusCode() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals(302, response.statusCode());
    }

    @Test
    public void itReturnsARedirectResponseWithTheRightStatusMessage() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals("Found", response.statusMessage());
    }

    @Test
    public void itReturnsARedirectResponseWithTheRightProtocol() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals("HTTP/1.1", response.protocol());
    }

    @Test
    public void itReturnsAGetResponseWithTheRightStatusCode() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void itReturnsAGetResponseWithTheRightStatusMessage() {
        Request request = new RequestBuilder().addVerb("GET").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals("OK", response.statusMessage());
    }

    @Test
    public void itReturnsAnOptionsResponseWithTheRightStatusCode() {
        Request request = new RequestBuilder().addVerb("OPTIONS").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void itReturnsAnOptionsResponseWithTheRightStatusMessage() {
        Request request = new RequestBuilder().addVerb("OPTIONS").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals("OK", response.statusMessage());
    }

    @Test
    public void itReturnsAnOptionsResponseWithTheRightAllowString() {
        Request request = new RequestBuilder().addVerb("OPTIONS").addUrl("/options").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals("OPTIONS,GET", response.allow());
    }

    @Test
    public void itReturnsAPatchResponseWithTheRightStatusCode() {
        Request request = new RequestBuilder().addVerb("PATCH").addUrl("/patch").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals(204, response.statusCode());
    }

    @Test
    public void itReturnsAPatchResponseWithTheRightStatusMessage() {
        Request request = new RequestBuilder().addVerb("PATCH").addUrl("/patch").addProtocol("HTTP/1.1").build();
        dataTable.executeAction(request, this.routesTable);
        Response response = responseGenerator.generateResponse(request, mapper);
        assertEquals("No Content", response.statusMessage());
    }

}
