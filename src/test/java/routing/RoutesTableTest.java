package routing;

import http_action.NullAction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class RoutesTableTest {

    private RoutesTable routesTable;
    private final NullAction action = new NullAction();

    @Before
    public void setup() {
        this.routesTable = new RoutesTable();
    }

    @Test
    public void thereAreNoRoutesUponInitialization() {
        String[] expectedOutput = new String[0];
        assertArrayEquals(expectedOutput, this.routesTable.formattedVerbsForUrl("/"));
    }

    @Test
    public void theOptionsVerbIsIncludedByDefault() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/", RoutesTable.Verb.GET, action);

        assertArrayEquals(expectedOutput, this.routesTable.formattedVerbsForUrl("/"));
    }

    @Test
    public void multipleVerbsCanBeAddedToAUrl() {
        String[] expectedOutput = new String[3];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";
        expectedOutput[2] = "PUT";

        routesTable.addRoute("/foo", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/foo", RoutesTable.Verb.PUT, action);

        assertArrayEquals(expectedOutput, this.routesTable.formattedVerbsForUrl("/foo"));
    }

    @Test
    public void duplicateVerbsAreIgnored() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/bar", RoutesTable.Verb.GET, action);
        routesTable.addRoute("/bar", RoutesTable.Verb.GET, action);

        assertArrayEquals(expectedOutput, this.routesTable.formattedVerbsForUrl("/bar"));
    }

    @Test
    public void theRouterIdentifiesAVerbAssociatedWithAUrl() {
        routesTable.addRoute("/baz", RoutesTable.Verb.DELETE, action);
        assertTrue(routesTable.urlHasVerb("/baz", RoutesTable.Verb.DELETE));
    }

    @Test
    public void nullActionsNeedNotBeSpecified() {
        String[] expectedOutput = new String[2];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";

        routesTable.addRoute("/", RoutesTable.Verb.GET);

        assertArrayEquals(expectedOutput, this.routesTable.formattedVerbsForUrl("/"));
    }

    @Test
    public void itRespondsToRESTVerbs() {
        String[] expectedOutput = new String[7];
        expectedOutput[0] = "OPTIONS";
        expectedOutput[1] = "GET";
        expectedOutput[2] = "HEAD";
        expectedOutput[3] = "POST";
        expectedOutput[4] = "PUT";
        expectedOutput[5] = "DELETE";
        expectedOutput[6] = "PATCH";

        routesTable.addRoute("/", RoutesTable.Verb.GET);
        routesTable.addRoute("/", RoutesTable.Verb.HEAD);
        routesTable.addRoute("/", RoutesTable.Verb.POST);
        routesTable.addRoute("/", RoutesTable.Verb.PUT);
        routesTable.addRoute("/", RoutesTable.Verb.DELETE);
        routesTable.addRoute("/", RoutesTable.Verb.PATCH);

        assertArrayEquals(expectedOutput, this.routesTable.formattedVerbsForUrl("/"));
    }

}
