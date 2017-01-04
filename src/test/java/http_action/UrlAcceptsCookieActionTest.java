package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;

import static junit.framework.TestCase.assertEquals;

public class UrlAcceptsCookieActionTest {

    private DataTable dataTable;
    private UrlAcceptsCookieAction action;

    @Before
    public void setup() {
        this.dataTable = new DataTable();
        this.action = new UrlAcceptsCookieAction();
    }

    @Test
    public void itReturnsHelloWorldIfThereIsNoCookie() {
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").build();
        action.execute(request, this.dataTable);
        assertEquals("<h1>Hello World!</h1>", this.dataTable.retrieveBody("/"));
    }

    @Test
    public void itReturnsTheCookieTypeIfItIsFormattedCorrectly() {
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").addCookie("type=chocolate").build();
        action.execute(request, this.dataTable);
        assertEquals("mmmm chocolate", this.dataTable.retrieveBody("/"));
    }

    @Test
    public void itTellsTheUserIfTheCookieIsNotFormattedCorrectly() {
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").addCookie("wrong format!").build();
        action.execute(request, this.dataTable);
        assertEquals("Your cookie has no type...", this.dataTable.retrieveBody("/"));
    }


}
