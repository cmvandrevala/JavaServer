package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;

public class PostActionTest {

    private RoutesTable routesTable = RoutesTable.getInstance();
    private RequestBuilder builder;

    @Before
    public void setup() {
        routesTable.addRoute("/", RoutesTable.Verb.POST, new PostAction());
        builder = new RequestBuilder();
    }

    @After
    public void teardown() {
        routesTable.clearRoutes();
    }

    @Test
    public void itAddsOnePieceOfDataToTheRoute() {
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("3").addBody("a=1").build();
        routesTable.executeAction(request);
        assertEquals("1",routesTable.retrieveData("/","a"));
    }

    @Test
    public void itAddsMultiplePiecesOfDataInDifferentRequestsToTheRoute() {
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("5").addBody("ab=cd").build();
        routesTable.executeAction(request);
        request = builder.addVerb("POST").addUrl("/").addContentLength("22").addBody("data=this is some data").build();
        routesTable.executeAction(request);
        assertEquals("cd",routesTable.retrieveData("/","ab"));
        assertEquals("this is some data",routesTable.retrieveData("/","data"));
    }

    @Test
    public void itIsIdempotent() {
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("7").addBody("a=cdefg").build();
        routesTable.executeAction(request);
        routesTable.executeAction(request);
        assertEquals("cdefg",routesTable.retrieveData("/","a"));
    }

    @Test
    public void itDoesNotTouchOtherRoutes() {
        routesTable.addRoute("/bar", RoutesTable.Verb.GET, new NullAction());
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("3").addBody("v=x").build();
        routesTable.executeAction(request);
        assertEquals("",routesTable.retrieveData("/bar","v"));
    }

    @Test
    public void itCreatesAKeyIfThereIsNoEqualsSign() {
        Request request = builder.addVerb("POST").addUrl("/").addContentLength("20").addBody("No equals sign here!").build();
        routesTable.executeAction(request);
        assertEquals("No equals sign here!",routesTable.retrieveData("/","body"));
    }
}
