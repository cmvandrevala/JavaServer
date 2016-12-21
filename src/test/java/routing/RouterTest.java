package routing;

import http_action.RedirectAction;
import http_request.Request;
import http_request.RequestBuilder;
import http_response.Response;
import http_response.Response;
import org.junit.Before;
import org.junit.Ignore;
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
        DataTable dataTable = new DataTable();
        RoutesTable routesTable = new RoutesTable();
        routesTable.addRoute("/method_options", RoutesTable.Verb.GET);
        routesTable.addRoute("/method_options", RoutesTable.Verb.HEAD);
        routesTable.addRoute("/method_options", RoutesTable.Verb.POST);
        routesTable.addRoute("/method_options", RoutesTable.Verb.PUT);
        routesTable.addRoute("/method_options", RoutesTable.Verb.DELETE);
        routesTable.addRoute("/method_options", RoutesTable.Verb.PATCH);
        routesTable.addRoute("/redirect", RoutesTable.Verb.GET, new RedirectAction("foo"));
        this.router = new Router(routesTable, dataTable);
    }

    @Test
    public void missingPageGetRequestYields404StatusCode() throws IOException {
        Request request = builder.addVerb("GET").addUrl("/missing").addProtocol("HTTP/2.0").build();
        Response response = router.route(request);
        assertEquals(404, response.statusCode());
    }

    @Test
    public void missingPageOptionRequestYields404StatusCode() throws IOException {
        Request request = builder.addVerb("OPTION").addUrl("/missing").addProtocol("HTTP/2.0").build();
        Response response = router.route(request);
        assertEquals(404, response.statusCode());
    }

    @Test
    public void missingPageHeadRequestYields404StatusCode() throws IOException {
        Request request = builder.addVerb("HEAD").addUrl("/missing").addProtocol("HTTP/2.0").build();
        Response response = router.route(request);
        assertEquals(404, response.statusCode());
    }

    @Test
    public void methodOptionsRequestYieldsTheCorrectHeaderValuesOtherThanAllow() throws IOException {
        Request request = builder.addVerb("OPTIONS").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals(200, response.statusCode());
        assertEquals("OK", response.statusMessage());
        assertEquals("0", response.contentLength());
    }

    @Test
    public void methodOptionsRequestYieldsTheCorrectHeaderForAllow() throws IOException {
        Request request = builder.addVerb("OPTIONS").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals("OPTIONS,GET,HEAD,POST,PUT,DELETE,PATCH", response.allow());
    }

    @Test
    public void getReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("GET").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void headReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("HEAD").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void postReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("POST").addUrl("/method_options").addProtocol("HTTP/1.1").addContentLength(0).build();
        Response response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void putReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("PUT").addUrl("/method_options").addProtocol("HTTP/1.1").addContentLength(0).build();
        Response response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void deleteReturnsAStatusCodeOf200() throws IOException {
        Request request = builder.addVerb("DELETE").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void putReturnsAStatusCodeOf411IfNoContentLengthIsSpecified() throws IOException {
        Request request = builder.addVerb("PUT").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals(411, response.statusCode());
    }

    @Test
    public void postReturnsAStatusCodeOf411IfNoContentLengthIsSpecified() throws IOException {
        Request request = builder.addVerb("POST").addUrl("/method_options").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals(411, response.statusCode());
    }

    @Test
    public void itReturns302ForARedirect() throws IOException {
        Request request = builder.addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals(302, response.statusCode());
    }

    @Test
    public void itRedirectsToTheCorrectUrl() throws IOException {
        Request request = builder.addVerb("GET").addUrl("/redirect").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);
        assertEquals("foo", response.location());
    }

    @Test
    public void patchRequestWithNoHeaderYieldsA400Response() throws IOException {
        Request request = builder.addVerb("PATCH").addUrl("/method_options").addProtocol("HTTP/1.1").addBody("should not appear").build();
        Response response = router.route(request);
        assertEquals(400, response.statusCode());
    }

}
