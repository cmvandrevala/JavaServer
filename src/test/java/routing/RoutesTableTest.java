package routing;

import http_action.HTTPAction;
import http_action.NullAction;
import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class RoutesTableTest {

    private class TestAction implements HTTPAction {
        boolean actionExecuted = false;
        public void execute(Request request, RoutesTable routesTable) {
            actionExecuted = true;
        }
    }

    private RoutesTable routesTable;
    private NullAction action = new NullAction();

    @Before
    public void setup() {
        this.routesTable = new RoutesTable();
    }

    @Test
    public void thereAreNoRoutesUponInitialization() {
        String[] expectedOutput = new String[0];
        assertArrayEquals(expectedOutput, this.routesTable.listVerbsForUrl("/"));
    }

    @Test
    public void theOptionsVerbIsIncludedByDefault() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/", RoutesTable.Verb.GET, action);

        assertArrayEquals(expectedOutput, this.routesTable.listVerbsForUrl("/"));
    }

    @Test
    public void multipleVerbsCanBeAddedToAUrl() {
        String[] expectedOutput = new String[3];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";
        expectedOutput[2] = "PUT";

        routesTable.addRoute("/foo", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/foo", RoutesTable.Verb.PUT, action);

        assertArrayEquals(expectedOutput, this.routesTable.listVerbsForUrl("/foo"));
    }

    @Test
    public void duplicateVerbsAreIgnored() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/bar", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/bar", RoutesTable.Verb.GET, action);

        assertArrayEquals(expectedOutput, this.routesTable.listVerbsForUrl("/bar"));
    }

    @Test
    public void theRouterIdentifiesAVerbAssociatedWithAUrl() {
        routesTable.addRoute("/baz", RoutesTable.Verb.DELETE, action);
        assertTrue(routesTable.urlHasVerb("/baz", RoutesTable.Verb.DELETE));
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
        assertEquals("foo", this.routesTable.retrieveData("/", "data"));
    }

    @Test
    public void itCanAddDataWithADifferentKeyAndValueToARoute() {
        routesTable.addData("/", "anotherKey", "anotherValue");
        assertEquals("anotherValue", this.routesTable.retrieveData("/", "anotherKey"));
    }

    @Test
    public void itCanAddDataToOneRouteMultipleTimes() {
        routesTable.addData("/foo","a","b");
        routesTable.addData("/foo","c","d");
        routesTable.addData("/foo","e","f");
        assertEquals("b", this.routesTable.retrieveData("/foo", "a"));
    }

    @Test
    public void itCanAddDataToDifferentRoutes() {
        routesTable.addData("/foo","a","b");
        routesTable.addData("/bar","c","d");
        routesTable.addData("/baz","e","f");
        assertEquals("d", this.routesTable.retrieveData("/bar", "c"));
    }

    @Test
    public void itReturnsNoDataIfAKeyIsNotDefined() {
        routesTable.addData("/baz","a","b");
        assertEquals("", this.routesTable.retrieveData("/baz", "c"));
    }

    public void itReturnsNoDataIfAUrlIsNotDefined() {
        assertEquals("",routesTable.retrieveData("invalid","should not return"));
    }

    @Test
    public void itCanRemoveAllOfTheDataFromARoute() {
        routesTable.addData("/foo","a","b");
        routesTable.addData("/foo","c","d");
        routesTable.removeAllData("/foo");
        assertEquals("", this.routesTable.retrieveData("/foo", "a"));
        assertEquals("", this.routesTable.retrieveData("/foo", "c"));
    }

    @Test
    public void nullActionsNeedNotBeSpecified() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/", RoutesTable.Verb.GET);

        assertArrayEquals(expectedOutput, this.routesTable.listVerbsForUrl("/"));
    }

}
