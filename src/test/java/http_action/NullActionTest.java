package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Test;
import routing.DataTable;

import static junit.framework.TestCase.assertEquals;

public class NullActionTest {

    @Test
    public void itHasOneMethodThatDoesNothing() {
        DataTable dataTable = new DataTable();
        dataTable.addBody("/", "foo");
        Request request = new RequestBuilder().build();
        NullAction action = new NullAction();
        action.execute(request, dataTable);
        assertEquals("foo", dataTable.retrieveBody("/"));
    }

}
