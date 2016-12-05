package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertTrue;

public class NullActionTest {

    @Test
    public void itHasOneMethodThatDoesNothing() {
        Request request = new RequestBuilder().build();
        NullAction action = new NullAction();
        action.execute(request);
        assertTrue(true);
    }

}
