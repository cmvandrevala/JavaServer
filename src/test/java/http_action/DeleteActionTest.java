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
        assertEquals("",dataTable.retrieveCustomData("/","key"));
    }

    @Test
    public void itDeletesOnePieceOfDataStoredInARoute() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        dataTable.executeAction(request, routesTable);

        dataTable.addCustomData("/","key","value");

        dataTable.executeAction(request, routesTable);
        assertEquals("",dataTable.retrieveCustomData("/","key"));
    }

    @Test
    public void itDeletesAllOfTheDataStoredInARoute() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        dataTable.executeAction(request, routesTable);

        dataTable.addBody("/","value1");
        dataTable.addETag("/","value2");
        dataTable.addLocation("/","value3");

        assertEquals("value1", dataTable.retrieveBody("/"));
        assertEquals("value2", dataTable.retrieveETag("/"));
        assertEquals("value3", dataTable.retrieveLocation("/"));

        dataTable.executeAction(request, routesTable);

        assertEquals("", dataTable.retrieveBody("/"));
        assertEquals("", dataTable.retrieveETag("/"));
        assertEquals("", dataTable.retrieveLocation("/"));

    }

    @Test
    public void itDoesNotTouchOtherRoutes() {
        Request request = builder.addVerb("DELETE").addUrl("/").build();
        dataTable.executeAction(request, routesTable);

        dataTable.addCustomData("/","key1","value1");
        dataTable.addCustomData("/foo","key2","value2");

        dataTable.executeAction(request, routesTable);

        assertEquals("value2",dataTable.retrieveCustomData("/foo","key2"));

    }
}
