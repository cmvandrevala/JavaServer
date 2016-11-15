package routing;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoutingTableTest {

    private RoutingTable routingTable;

    @Before
    public void setup() {
        this.routingTable = new RoutingTable();
    }

    @Test
    public void thereAreNoRoutesUponInitialization() {
        String[] expectedOutput = new String[0];
        assertEquals(expectedOutput, routingTable.listRoutesForUrl("/"));
    }

    @Test
    public void theOptionsVerbIsIncludedByDefault() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/", "GET");

        assertEquals(expectedOutput, routingTable.listRoutesForUrl("/"));
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

        assertEquals(expectedOutput, routingTable.listRoutesForUrl("/foo"));
    }

    @Test
    public void duplicateVerbsAreIgnored() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routingTable.addRoute("/bar", "GET");
        routingTable.addRoute("/bar", "GET");

        assertEquals(expectedOutput, routingTable.listRoutesForUrl("/bar"));
    }

}
