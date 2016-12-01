package routing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class RoutingTableTest {

    private RoutingTable routingTable;
    private DummyAction action = new DummyAction();

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
        assertArrayEquals(expectedOutput, routingTable.listRoutesForUrl("/"));
    }

    @Test
    public void theOptionsVerbIsIncludedByDefault() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/", "GET", action);

        assertArrayEquals(expectedOutput, routingTable.listRoutesForUrl("/"));
    }

    @Test
    public void multipleVerbsCanBeAddedToAUrl() {
        String[] expectedOutput = new String[3];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";
        expectedOutput[2] = "PUT";

        routingTable.addRoute("/foo", "GET", action);
        routingTable.addRoute("/foo", "PUT", action);

        assertArrayEquals(expectedOutput, routingTable.listRoutesForUrl("/foo"));
    }

    @Test
    public void duplicateVerbsAreIgnored() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/bar", "GET", action);
        routingTable.addRoute("/bar", "GET", action);

        assertArrayEquals(expectedOutput, routingTable.listRoutesForUrl("/bar"));
    }

    @Test
    public void theRouterIdentifiesAVerbAssociatedWithAUrl() {
        routingTable.addRoute("/baz", "DELETE", action);
        assertTrue(routingTable.urlHasVerb("/baz", "DELETE"));
    }

    @Test
    public void theRouterDoesNotIdentifyAnInvalidVerb() {
        routingTable.addRoute("/baz", "Invalid", action);
        assertFalse(routingTable.urlHasVerb("/baz", "Invalid"));
    }

    @Test
    public void thereCanOnlyBeOneInstanceOfRoutingTable() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/foo", "GET", action);
        RoutingTable anotherTable = RoutingTable.getInstance();

        assertArrayEquals(expectedOutput, anotherTable.listRoutesForUrl("/foo"));
    }

    @Test
    public void itDoesNotAddARouteThatIsNotListedIntheAcceptableRoutes() {
        String[] expectedOutput = new String[0];
        routingTable.addRoute("/foo", "Invalid Route", action);
        assertArrayEquals(expectedOutput, routingTable.listRoutesForUrl("/foo"));
    }

}
