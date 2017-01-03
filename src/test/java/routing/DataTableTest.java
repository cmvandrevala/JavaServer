package routing;

import http_action.HTTPAction;
import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataTableTest {

    private class TestAction implements HTTPAction {
        boolean actionExecuted = false;
        public void execute(Request request, DataTable dataTable) {
            actionExecuted = true;
        }
    }

    private RoutesTable routesTable;
    private DataTable dataTable;

    @Before
    public void setup() {
        this.routesTable = new RoutesTable();
        this.dataTable = new DataTable();
    }

    @Test
    public void itExecutesAnAction() {
        TestAction anotherAction = new TestAction();
        RequestBuilder builder = new RequestBuilder();
        Request request = builder.addUrl("/foo").addVerb("GET").build();
        routesTable.addRoute("/foo", RoutesTable.Verb.GET, anotherAction);
        dataTable.executeAction(request, routesTable);
        assertTrue(anotherAction.actionExecuted);
    }

    @Test
    public void itCanAddBodyTextToARoute() {
        dataTable.addBody("/", "foo");
        assertEquals("foo", this.dataTable.retrieveBody("/"));
    }

    @Test
    public void itCanAddAnETagToARoute() {
        dataTable.addETag("/", "bar");
        assertEquals("bar", this.dataTable.retrieveETag("/"));
    }

    @Test
    public void itCanAddALocationToARoute() {
        dataTable.addLocation("/foo", "location");
        assertEquals("location", this.dataTable.retrieveLocation("/foo"));
    }

    @Test
    public void itCanAddASetCookieToARoute() {
        dataTable.addSetCookie("/bar", "yummy");
        assertEquals("yummy", this.dataTable.retrieveSetCookie("/bar"));
    }

    @Test
    public void itCanAddACustomKeyValuePairToARoute() {
        dataTable.addCustomData("/bar", "key", "value");
        assertEquals("value", this.dataTable.retrieveCustomData("/bar", "key"));
    }

    @Test
    public void itReturnsNoBodyByDefault() {
        assertEquals("", this.dataTable.retrieveBody("/baz"));
    }

    @Test
    public void itReturnsNoDataIfAUrlIsNotDefined() {
        assertEquals("", dataTable.retrieveCustomData("invalid","should not return"));
    }

    @Test
    public void itCanRemoveAllOfTheDataFromARoute() {
        dataTable.addBody("/foo","b");
        dataTable.addETag("/foo","c");
        dataTable.removeAllData("/foo");
        assertEquals("", this.dataTable.retrieveBody("/foo"));
        assertEquals("", this.dataTable.retrieveETag("/foo"));
    }

}
