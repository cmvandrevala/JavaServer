package routing;

import http_request.Request;
import http_request.RequestBuilder;
import http_response.HTTPResponse;
import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    private Router router;
    private RequestBuilder builder;

    @Before
    public void setup() {
        builder = new RequestBuilder();
        RoutesTable routesTable = new RoutesTable();
        routesTable.addRoute("/method_options", RoutesTable.Verb.GET);
        routesTable.addRoute("/method_options", RoutesTable.Verb.HEAD);
        routesTable.addRoute("/method_options", RoutesTable.Verb.POST);
        routesTable.addRoute("/method_options", RoutesTable.Verb.PUT);
        routesTable.addRoute("/method_options", RoutesTable.Verb.DELETE);
        routesTable.addRoute("/redirect", RoutesTable.Verb.GET);
        this.router = new Router(routesTable);
    }

    @Test
    public void emptyRequestYields400StatusCode() throws IOException {
        Request request = builder.build();
        request.setAsBadRequest();
        HTTPResponse response = router.route(request);
        assertEquals(400,response.statusCode());
    }

    @Test
    public void missingPageGetRequestYields404StatusCode() throws IOException {
        Request request = builder.addVerb("GET").addUrl("/missing").addProtocol("HTTP/2.0").build();
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void missingPageOptionRequestYields404StatusCode() throws IOException {
        Request request = builder.addVerb("OPTION").addUrl("/missing").addProtocol("HTTP/2.0").build();
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void missingPageHeadRequestYields404StatusCode() throws IOException {
        Request request = builder.addVerb("HEAD").addUrl("/missing").addProtocol("HTTP/2.0").build();
        HTTPResponse response = router.route(request);
        assertEquals(404,response.statusCode());
    }

    @Test
    public void methodOptionsRequestYieldsTheCorrectStringResponse() throws IOException {
        Request request = builder.addVerb("OPTIONS").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        HTTPResponse response = router.route(request);
        String expectedOutput = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Allow: OPTIONS,GET,HEAD,POST,PUT,DELETE" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        assertEquals(expectedOutput, response.responseString());
    }

    @Test
    public void getReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("GET").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void headReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("HEAD").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void postReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("POST").addUrl("/method_options").addProtocol("HTTP/1.1").addContentLength(0).build();
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void putReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("PUT").addUrl("/method_options").addProtocol("HTTP/1.1").addContentLength(0).build();
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void deleteReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("DELETE").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        HTTPResponse response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void putReturnsAStatusCodeOf411IfNoContentLengthIsSpecified() throws IOException {
        Request request = builder.addVerb("PUT").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        HTTPResponse response = router.route(request);
        assertEquals(411, response.statusCode());
    }

    @Test
    public void postReturnsAStatusCodeOf411IfNoContentLengthIsSpecified() throws IOException {
        Request request = builder.addVerb("POST").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        HTTPResponse response = router.route(request);
        assertEquals(411, response.statusCode());
    }

    @Test
    public void itRedirectsARequest() throws IOException {
        Request request = builder.addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        HTTPResponse response = router.route(request);
        assertEquals(302, response.statusCode());
    }

}
