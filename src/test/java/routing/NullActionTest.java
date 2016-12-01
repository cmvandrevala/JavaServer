package routing;

import http_request.Request;
import org.junit.Test;

import java.util.Hashtable;

public class NullActionTest {

    @Test
    public void itHasOneMethodThatDoesNothing() {
        Request request = new Request(new Hashtable<String,String>());
        NullAction action = new NullAction();
        action.execute(request);
        assert(true);
    }

}
