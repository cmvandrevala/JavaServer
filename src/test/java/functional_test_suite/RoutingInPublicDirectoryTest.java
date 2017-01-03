package functional_test_suite;

import http_action.ReadFromTextFileAction;
import http_request.Request;
import http_request.RequestBuilder;
import http_response.Response;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.PathToUrlMapper;
import routing.Router;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class RoutingInPublicDirectoryTest {

    private PathToUrlMapper mapper;
    private RoutesTable routesTable;
    private DataTable dataTable;

    @Before
    public void setup() {
        this.mapper = new PathToUrlMapper("public/");
        this.dataTable = new DataTable();
        this.routesTable = new RoutesTable();
    }

    @Test
    public void noRouteNoFile() {
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/missing.jpeg").addProtocol("HTTP/1.1").build();
        Response response = router.route(request);

        assertEquals(404, response.statusCode());
    }

    @Test
    public void routeButNoJPEGFile() {
        routesTable.addRoute("/missing2.jpeg", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/missing2.jpeg").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void routeButNoPNGFile() {
        routesTable.addRoute("/missing3.png", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/missing3.png").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void routeButNoGIFFile() {
        routesTable.addRoute("/missing3.gif", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/missing3.gif").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void fileButNoRoute() {
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/image.png").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void routeAndFile() {
        routesTable.addRoute("/image.gif", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/image.gif").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void retrievePNGImageData() {
        routesTable.addRoute("/image.png", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/image.png").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertTrue(response.body().contains("PNG"));
    }

    @Test
    public void JPEGImageDataHasNonZeroContentLength() {
        routesTable.addRoute("/image.jpeg", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/image.jpeg").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertTrue(Integer.parseInt(response.contentLength()) > 0);
    }

    @Test
    public void GIFImageDataHasNonZeroContentLength() {
        routesTable.addRoute("/image.gif", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/image.gif").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertTrue(Integer.parseInt(response.contentLength()) > 0);
    }

    @Test
    public void textFilesHaveNonZeroContentLength() {
        routesTable.addRoute("/file1", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/file1").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(14,Integer.parseInt(response.contentLength()));
    }

}
