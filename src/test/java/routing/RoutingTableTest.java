package routing;

import http_request.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

class AnotherAction implements HTTPAction {
    public void execute(Request request) {}
}

public class RoutingTableTest {

    private RoutingTable routingTable;
    private NullAction action = new NullAction();

    @Before
    public void setup() {
        this.routingTable = RoutingTable.getInstance();
    }

    @After
    public void teardown() {
        this.routingTable.clearData();
    }

    @Test
    public void thereAreNoRoutesUponInitialization() {
        String[] expectedOutput = new String[0];
        assertArrayEquals(expectedOutput, routingTable.listVerbsForUrl("/"));
    }

    @Test
    public void theOptionsVerbIsIncludedByDefault() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/", RoutingTable.Verb.GET, action);

        assertArrayEquals(expectedOutput, routingTable.listVerbsForUrl("/"));
    }

    @Test
    public void multipleVerbsCanBeAddedToAUrl() {
        String[] expectedOutput = new String[3];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";
        expectedOutput[2] = "PUT";

        routingTable.addRoute("/foo", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/foo", RoutingTable.Verb.PUT, action);

        assertArrayEquals(expectedOutput, routingTable.listVerbsForUrl("/foo"));
    }

    @Test
    public void duplicateVerbsAreIgnored() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/bar", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/bar", RoutingTable.Verb.GET, action);

        assertArrayEquals(expectedOutput, routingTable.listVerbsForUrl("/bar"));
    }

    @Test
    public void theRouterIdentifiesAVerbAssociatedWithAUrl() {
        routingTable.addRoute("/baz", RoutingTable.Verb.DELETE, action);
        assertTrue(routingTable.urlHasVerb("/baz", RoutingTable.Verb.DELETE));
    }

    @Test
    public void thereCanOnlyBeOneInstanceOfRoutingTable() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/foo", RoutingTable.Verb.GET, action);
        RoutingTable anotherTable = RoutingTable.getInstance();

        assertArrayEquals(expectedOutput, anotherTable.listVerbsForUrl("/foo"));
    }

    @Test
    public void itReturnsTheActionForAVerb() {
        routingTable.addRoute("/foo", RoutingTable.Verb.GET, action);
        HTTPAction action = routingTable.action("/foo", RoutingTable.Verb.GET);
        assertEquals(NullAction.class, action.getClass());
    }

    @Test
    public void itReturnsADifferentActionForAVerb() {
        routingTable.addRoute("/foo", RoutingTable.Verb.GET, new AnotherAction());
        HTTPAction action = routingTable.action("/foo", RoutingTable.Verb.GET);
        assertEquals(AnotherAction.class, action.getClass());
    }

}
