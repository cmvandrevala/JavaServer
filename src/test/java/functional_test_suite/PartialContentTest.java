package functional_test_suite;

import http_action.ReadFromFileAction;
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

public class PartialContentTest {

    private RoutesTable routesTable;
    private PathToUrlMapper mapper;
    private DataTable dataTable;
    private RequestBuilder builder;

    @Before
    public void setup() {
        this.routesTable = new RoutesTable();
        this.mapper = new PathToUrlMapper("public/");
        this.dataTable = new DataTable();
        this.builder = new RequestBuilder();
    }

    @Test
    public void itReturnsA206Code() {
        routesTable.addRoute("/partial_content.txt", RoutesTable.Verb.GET, new ReadFromFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        builder.addVerb("GET").addUrl("/partial_content.txt").addProtocol("HTTP/1.1");
        builder.addHost("localhost:5000").addRange("bytes=0-30");
        Request request = builder.build();
        Response response = router.route(request);

        assertEquals(206, response.statusCode());
    }

    @Test
    public void itReturnsTheCorrectTextForTwoBounds() {
        routesTable.addRoute("/partial_content.txt", RoutesTable.Verb.GET, new ReadFromFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        builder.addVerb("GET").addUrl("/partial_content.txt").addProtocol("HTTP/1.1");
        builder.addHost("localhost:5000").addRange("bytes=0-1");
        Request request = builder.build();
        Response response = router.route(request);

        assertEquals("T", response.body());
    }

    @Test
    public void itReturnsTheCorrectTextForOneUpperBound() {
        routesTable.addRoute("/partial_content.txt", RoutesTable.Verb.GET, new ReadFromFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        builder.addVerb("GET").addUrl("/partial_content.txt").addProtocol("HTTP/1.1");
        builder.addHost("localhost:5000").addRange("bytes=-6");
        Request request = builder.build();
        Response response = router.route(request);

        assertEquals(" 206.", response.body());
    }

    @Test
    public void itReturnsTheCorrectTextForOneLowerBound() {
        routesTable.addRoute("/partial_content.txt", RoutesTable.Verb.GET, new ReadFromFileAction(mapper));
        Router router = new Router(mapper, routesTable, dataTable);
        builder.addVerb("GET").addUrl("/partial_content.txt").addProtocol("HTTP/1.1");
        builder.addHost("localhost:5000").addRange("bytes=4-");
        Request request = builder.build();
        Response response = router.route(request);

        assertEquals(" is a file that contains text to read part of in order to fulfill a 206.", response.body());
    }
}
