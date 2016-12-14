package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Test;
import routing.DataTable;
import routing.RoutesTable;

import static org.junit.Assert.assertTrue;

public class NullActionTest {

    @Test
    public void itHasOneMethodThatDoesNothing() {
        Request request = new RequestBuilder().build();
        NullAction action = new NullAction();
        action.execute(request, new RoutesTable(), new DataTable());
        assertTrue(true);
    }

}
