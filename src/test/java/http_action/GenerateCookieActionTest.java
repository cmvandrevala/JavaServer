package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;

public class GenerateCookieActionTest {

    private RoutesTable routesTable;
    private RequestBuilder builder;

    @Before
    public void setup() {
        routesTable = new RoutesTable();
        routesTable.addRoute("/", RoutesTable.Verb.GET, new GenerateCookieAction());
        builder = new RequestBuilder();
    }

    @Test
    public void itDoesNothingIfThereIsNoQueryParamsData() {
        Request request = builder.addVerb("GET").addUrl("/").build();
        routesTable.executeAction(request);
        assertEquals("", routesTable.retrieveData("/","cookie"));
    }

    @Test
    public void itAddsACookie() {
        Request request = builder.addVerb("GET").addUrl("/").addQueryParams("some data").build();
        routesTable.executeAction(request);
        assertEquals("some data", routesTable.retrieveData("/","cookie"));
    }

    @Test
    public void itIsIdempotent() {
        Request request = builder.addVerb("GET").addUrl("/").addQueryParams("more data").build();
        routesTable.executeAction(request);
        routesTable.executeAction(request);
        assertEquals("more data", routesTable.retrieveData("/","cookie"));
    }

    @Test
    public void itCreatesDifferentCookiesForDifferentRequests() {
        Request request = builder.addVerb("GET").addUrl("/").addQueryParams("cookie1").build();
        routesTable.executeAction(request);
        request = builder.addVerb("GET").addUrl("/").addQueryParams("cookie2").build();
        routesTable.executeAction(request);
        assertEquals("cookie2", routesTable.retrieveData("/","cookie"));
    }

}
