package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;

import static junit.framework.TestCase.assertEquals;

public class UrlReturnsCookieActionTest {

    private DataTable dataTable;
    private UrlReturnsCookieAction action;

    @Before
    public void setup() {
        this.dataTable = new DataTable();
        this.action = new UrlReturnsCookieAction();
    }

    @Test
    public void itReturnsADefaultBodyIfThereAreNoQueryParameters() {
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").build();
        action.execute(request, this.dataTable);
        assertEquals("<h1>Hello World!</h1>", this.dataTable.retrieveBody("/"));
    }

    @Test
    public void itReturnsNoCookieIfThereAreNoQueryParameters() {
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").build();
        action.execute(request, this.dataTable);
        assertEquals("", this.dataTable.retrieveSetCookie("/"));
    }

    @Test
    public void itReturnsEatInTheBodyIfThereIsACookie() {
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").addQueryParams("type=oatmeal").build();
        action.execute(request, this.dataTable);
        assertEquals("Eat", this.dataTable.retrieveBody("/"));
    }

    @Test
    public void itSetsACookieAccordingToTheQueryParameters() {
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").addQueryParams("type=fudge").build();
        action.execute(request, this.dataTable);
        assertEquals("type=fudge", this.dataTable.retrieveSetCookie("/"));
    }

}
