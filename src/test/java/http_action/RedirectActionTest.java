package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Test;
import routing.DataTable;

import static junit.framework.TestCase.assertEquals;

public class RedirectActionTest {

    @Test
    public void itAddsAUrlToWhichTheUserWillBeRedirected() {
        DataTable dataTable = new DataTable();
        RedirectAction action = new RedirectAction("my-url.com");
        Request request = new RequestBuilder().addUrl("/").addVerb("GET").build();
        action.execute(request, dataTable);
        assertEquals("my-url.com", dataTable.retrieveLocation("/"));
    }
}
