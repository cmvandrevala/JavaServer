package http_action;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.RoutesTable;

import static junit.framework.TestCase.assertEquals;

public class DeleteActionTest {

    private RoutesTable routesTable;
    private DataTable dataTable;
    private RequestBuilder builder;

    @Before
    public void setup() {
        dataTable = new DataTable();
        routesTable = new RoutesTable();
        routesTable.addRoute("/", RoutesTable.Verb.DELETE,new DeleteAction());
        builder = new RequestBuilder();
    }

    @Test
    public void itDoesNothingIfThereIsNoDataStoredInARoute() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        dataTable.executeAction(request, routesTable);
        assertEquals("",dataTable.retrieveData("/","key"));
    }

    @Test
    public void itDeletesOnePieceOfDataStoredInARoute() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        dataTable.executeAction(request, routesTable);

        dataTable.addData("/","key","value");

        dataTable.executeAction(request, routesTable);
        assertEquals("",dataTable.retrieveData("/","key"));
    }

    @Test
    public void itDeletesAllOfTheDataStoredInARoute() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        dataTable.executeAction(request, routesTable);

        dataTable.addData("/","key1","value1");
        dataTable.addData("/","key2","value2");
        dataTable.addData("/","key3","value3");
        dataTable.addData("/","key4","value4");

        assertEquals("value1",dataTable.retrieveData("/","key1"));
        assertEquals("value2",dataTable.retrieveData("/","key2"));
        assertEquals("value3",dataTable.retrieveData("/","key3"));
        assertEquals("value4",dataTable.retrieveData("/","key4"));

        dataTable.executeAction(request, routesTable);

        assertEquals("",dataTable.retrieveData("/","key1"));
        assertEquals("",dataTable.retrieveData("/","key2"));
        assertEquals("",dataTable.retrieveData("/","key3"));
        assertEquals("",dataTable.retrieveData("/","key4"));

    }

    @Test
    public void itDoesNotTouchOtherRoutes() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        dataTable.executeAction(request, routesTable);

        dataTable.addData("/","key1","value1");
        dataTable.addData("/foo","key2","value2");

        dataTable.executeAction(request, routesTable);

        assertEquals("value2",dataTable.retrieveData("/foo","key2"));

    }
}
