package routing;

import http_action.HTTPAction;
import http_action.NullAction;
import http_request.Request;
import http_request.RequestBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class RoutesTableTest {

    private class TestAction implements HTTPAction {
        public boolean actionExecuted = false;
        public void execute(Request request) {
            actionExecuted = true;
        }
    }

    private RoutesTable routesTable;
    private NullAction action = new NullAction();

    @Before
    public void setup() {
        this.routesTable = RoutesTable.getInstance();
    }

    @After
    public void teardown() {
        this.routesTable.clearRoutes();
    }

    @Test
    public void thereAreNoRoutesUponInitialization() {
        String[] expectedOutput = new String[0];
        assertArrayEquals(expectedOutput, routesTable.listVerbsForUrl("/"));
    }

    @Test
    public void theOptionsVerbIsIncludedByDefault() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/", RoutesTable.Verb.GET, action);

        assertArrayEquals(expectedOutput, routesTable.listVerbsForUrl("/"));
    }

    @Test
    public void multipleVerbsCanBeAddedToAUrl() {
        String[] expectedOutput = new String[3];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";
        expectedOutput[2] = "PUT";

        routesTable.addRoute("/foo", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/foo", RoutesTable.Verb.PUT, action);

        assertArrayEquals(expectedOutput, routesTable.listVerbsForUrl("/foo"));
    }

    @Test
    public void duplicateVerbsAreIgnored() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/bar", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/bar", RoutesTable.Verb.GET, action);

        assertArrayEquals(expectedOutput, routesTable.listVerbsForUrl("/bar"));
    }

    @Test
    public void theRouterIdentifiesAVerbAssociatedWithAUrl() {
        routesTable.addRoute("/baz", RoutesTable.Verb.DELETE, action);
        assertTrue(routesTable.urlHasVerb("/baz", RoutesTable.Verb.DELETE));
    }

    @Test
    public void thereCanOnlyBeOneInstanceOfRoutingTable() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/foo", RoutesTable.Verb.GET, action);
        RoutesTable anotherTable = RoutesTable.getInstance();

        assertArrayEquals(expectedOutput, anotherTable.listVerbsForUrl("/foo"));
    }

    @Test
    public void itExecutesAnAction() {
        TestAction anotherAction = new TestAction();
        RequestBuilder builder = new RequestBuilder();
        Request request = builder.addUrl("/foo").addVerb("GET").build();
        routesTable.addRoute("/foo", RoutesTable.Verb.GET, anotherAction);
        routesTable.executeAction(request);
        assertTrue(anotherAction.actionExecuted);
    }

    @Test
    public void itCanAddDataToARoute() {
        routesTable.addData("/", "data", "foo");
        assertEquals("foo", routesTable.retrieveData("/", "data"));
    }

    @Test
    public void itCanAddDataWithADifferentKeyAndValueToARoute() {
        routesTable.addData("/", "anotherKey", "anotherValue");
        assertEquals("anotherValue", routesTable.retrieveData("/", "anotherKey"));
    }

    @Test
    public void itCanAddDataToOneRouteMultipleTimes() {
        routesTable.addData("/foo","a","b");
        routesTable.addData("/foo","c","d");
        routesTable.addData("/foo","e","f");
        assertEquals("b", routesTable.retrieveData("/foo", "a"));
    }

    @Test
    public void itCanAddDataToDifferentRoutes() {
        routesTable.addData("/foo","a","b");
        routesTable.addData("/bar","c","d");
        routesTable.addData("/baz","e","f");
        assertEquals("d", routesTable.retrieveData("/bar", "c"));
    }

    @Test
    public void itReturnsNoDataIfAKeyIsNotDefined() {
        routesTable.addData("/baz","a","b");
        assertEquals("", routesTable.retrieveData("/baz", "c"));
    }

    public void itReturnsNoDataIfAUrlIsNotDefined() {
        assertEquals("",routesTable.retrieveData("invalid","should not return"));
    }

    @Test
    public void itCanRemoveAllOfTheDataFromARoute() {
        routesTable.addData("/foo","a","b");
        routesTable.addData("/foo","c","d");
        routesTable.removeAllData("/foo");
        assertEquals("", routesTable.retrieveData("/foo", "a"));
        assertEquals("", routesTable.retrieveData("/foo", "c"));
    }

    @Test
    public void nullActionsNeedNotBeSpecified() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/", RoutesTable.Verb.GET);

        assertArrayEquals(expectedOutput, routesTable.listVerbsForUrl("/"));
    }

}
