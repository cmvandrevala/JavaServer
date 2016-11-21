package routing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RoutingTableTest {

    private RoutingTable routingTable;

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

        routingTable.addRoute("/", "GET");

        assertArrayEquals(expectedOutput, routingTable.listRoutesForUrl("/"));
    }

    @Test
    public void multipleVerbsCanBeAddedToAUrl() {
        String[] expectedOutput = new String[4];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";
        expectedOutput[2] = "PUT";
        expectedOutput[3] = "Random Route";

        routingTable.addRoute("/foo", "GET");
        routingTable.addRoute("/foo", "PUT");
        routingTable.addRoute("/foo", "Random Route");

        assertArrayEquals(expectedOutput, routingTable.listRoutesForUrl("/foo"));
    }

    @Test
    public void duplicateVerbsAreIgnored() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/bar", "GET");
        routingTable.addRoute("/bar", "GET");

        assertArrayEquals(expectedOutput, routingTable.listRoutesForUrl("/bar"));
    }

    @Test
    public void theRouterIdentifiesAVerbAssociatedWithAUrl() {
        routingTable.addRoute("/baz", "DELETE");
        assertEquals(true, routingTable.urlHasVerb("/baz", "DELETE"));
    }

    @Test
    public void thereCanOnlyBeOneInstanceOfRoutingTable() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/foo", "GET");
        RoutingTable anotherTable = RoutingTable.getInstance();

        assertArrayEquals(expectedOutput, anotherTable.listRoutesForUrl("/foo"));
    }

}
