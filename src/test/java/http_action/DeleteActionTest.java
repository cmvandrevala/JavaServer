package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;

public class DeleteActionTest {

    private RoutesTable routesTable;
    private RequestBuilder builder;

    @Before
    public void setup() {
        routesTable = new RoutesTable();
        routesTable.addRoute("/", RoutesTable.Verb.DELETE,new DeleteAction());
        builder = new RequestBuilder();
    }

    @Test
    public void itDoesNothingIfThereIsNoDataStoredInARoute() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        routesTable.executeAction(request);
        assertEquals("",routesTable.retrieveData("/","key"));
    }

    @Test
    public void itDeletesOnePieceOfDataStoredInARoute() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        routesTable.executeAction(request);

        routesTable.addData("/","key","value");

        routesTable.executeAction(request);
        assertEquals("",routesTable.retrieveData("/","key"));
    }

    @Test
    public void itDeletesAllOfTheDataStoredInARoute() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        routesTable.executeAction(request);

        routesTable.addData("/","key1","value1");
        routesTable.addData("/","key2","value2");
        routesTable.addData("/","key3","value3");
        routesTable.addData("/","key4","value4");

        assertEquals("value1",routesTable.retrieveData("/","key1"));
        assertEquals("value2",routesTable.retrieveData("/","key2"));
        assertEquals("value3",routesTable.retrieveData("/","key3"));
        assertEquals("value4",routesTable.retrieveData("/","key4"));

        routesTable.executeAction(request);

        assertEquals("",routesTable.retrieveData("/","key1"));
        assertEquals("",routesTable.retrieveData("/","key2"));
        assertEquals("",routesTable.retrieveData("/","key3"));
        assertEquals("",routesTable.retrieveData("/","key4"));

    }

    @Test
    public void itDoesNotTouchOtherRoutes() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        routesTable.executeAction(request);

        routesTable.addData("/","key1","value1");
        routesTable.addData("/foo","key2","value2");

        routesTable.executeAction(request);

        assertEquals("value2",routesTable.retrieveData("/foo","key2"));

    }
}
