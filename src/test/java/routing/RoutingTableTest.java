package routing;

import http_action.HTTPAction;
import http_action.NullAction;
import http_request.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

class TestAction implements HTTPAction {
    public boolean actionExecuted = false;
    public void execute(Request request) {
        actionExecuted = true;
    }
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
    public void itExecutesAnAction() {
        TestAction anotherAction = new TestAction();
        Hashtable<String,String> params = new Hashtable<>();
        params.put("URL", "/foo");
        params.put("Verb", "GET");
        Request request = new Request(params);
        routingTable.addRoute("/foo", RoutingTable.Verb.GET, anotherAction);
        routingTable.executeAction(request);
        assertTrue(anotherAction.actionExecuted);
    }

}
