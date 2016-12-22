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
    public void RouteButNoJPEGFile() {
        routesTable.addRoute("/missing2.jpeg", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/missing2.jpeg").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void RouteButNoPNGFile() {
        routesTable.addRoute("/missing3.png", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/missing3.png").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void RouteButNoGIFFile() {
        routesTable.addRoute("/missing3.gif", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/missing3.gif").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void FileButNoRoute() {
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/image.png").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }

    @Test
    public void RouteAndFile() {
        routesTable.addRoute("/image.gif", RoutesTable.Verb.GET, new ReadFromTextFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        Request request = new RequestBuilder().addVerb("GET").addUrl("/image.gif").addProtocol("HTTP/1.1").addHost("localhost:5000").build();
        Response response = router.route(request);

        assertEquals(200, response.statusCode());
    }



}
