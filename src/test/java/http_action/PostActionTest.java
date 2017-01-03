package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;

public class PostActionTest {

    private RoutesTable routesTable;
    private DataTable dataTable;
    private RequestBuilder builder;

    @Before
    public void setup() {
        routesTable = new RoutesTable();
        dataTable = new DataTable();
        routesTable.addRoute("/", RoutesTable.Verb.POST, new PostAction());
        builder = new RequestBuilder();
    }

    @Test
    public void itAddsOnePieceOfDataToTheRoute() {
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("3").addBody("a=1").build();
        dataTable.executeAction(request, routesTable);
        assertEquals("1",dataTable.retrieveCustomData("/","a"));
    }

    @Test
    public void itAddsMultiplePiecesOfDataInDifferentRequestsToTheRoute() {
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("5").addBody("ab=cd").build();
        dataTable.executeAction(request, routesTable);
        request = builder.addVerb("POST").addUrl("/").addContentLength("22").addBody("data=this is some data").build();
        dataTable.executeAction(request, routesTable);
        assertEquals("cd",dataTable.retrieveCustomData("/","ab"));
        assertEquals("this is some data",dataTable.retrieveCustomData("/","data"));
    }

    @Test
    public void itIsIdempotent() {
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("7").addBody("a=cdefg").build();
        dataTable.executeAction(request, routesTable);
        dataTable.executeAction(request, routesTable);
        assertEquals("cdefg",dataTable.retrieveCustomData("/","a"));
    }

    @Test
    public void itDoesNotTouchOtherRoutes() {
        routesTable.addRoute("/bar", RoutesTable.Verb.GET);
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("3").addBody("v=x").build();
        dataTable.executeAction(request, routesTable);
        assertEquals("",dataTable.retrieveCustomData("/bar","v"));
    }

    @Test
    public void itCreatesAKeyIfThereIsNoEqualsSign() {
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("20").addBody("No equals sign here!").build();
        dataTable.executeAction(request, routesTable);
        assertEquals("No equals sign here!",dataTable.retrieveBody("/"));
    }
}
