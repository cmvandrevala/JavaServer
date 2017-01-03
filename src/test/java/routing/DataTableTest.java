package routing;

import http_action.HTTPAction;
import http_request.Request;
import http_request.RequestBuilder;
import org.junit.Before;
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
    public void itCanAddDataToARoute() {
        dataTable.addData("/", "data", "foo");
        assertEquals("foo", this.dataTable.retrieveData("/", "data"));
    }

    @Test
    public void itCanAddDataWithADifferentKeyAndValueToARoute() {
        dataTable.addData("/", "anotherKey", "anotherValue");
        assertEquals("anotherValue", this.dataTable.retrieveData("/", "anotherKey"));
    }

    @Test
    public void itCanAddDataToOneRouteMultipleTimes() {
        dataTable.addData("/foo","a","b");
        dataTable.addData("/foo","c","d");
        dataTable.addData("/foo","e","f");
        assertEquals("b", this.dataTable.retrieveData("/foo", "a"));
    }

    @Test
    public void itCanAddDataToDifferentRoutes() {
        dataTable.addData("/foo","a","b");
        dataTable.addData("/bar","c","d");
        dataTable.addData("/baz","e","f");
        assertEquals("d", this.dataTable.retrieveData("/bar", "c"));
    }

    @Test
    public void itReturnsNoDataIfAKeyIsNotDefined() {
        dataTable.addData("/baz","a","b");
        assertEquals("", this.dataTable.retrieveData("/baz", "c"));
    }

    @Test
    public void itReturnsNoDataIfAUrlIsNotDefined() {
        assertEquals("",dataTable.retrieveData("invalid","should not return"));
    }

    @Test
    public void itCanRemoveAllOfTheDataFromARoute() {
        dataTable.addData("/foo","a","b");
        dataTable.addData("/foo","c","d");
        dataTable.removeAllData("/foo");
        assertEquals("", this.dataTable.retrieveData("/foo", "a"));
        assertEquals("", this.dataTable.retrieveData("/foo", "c"));
    }

    @Test
    public void theBoundsDefaultToTheEntireBody() {
        dataTable.addData("/foo", "Body", "ABCDEFG");
        Request request = new RequestBuilder().addUrl("/foo").build();
        assertEquals("ABCDEFG", dataTable.partialContent(request));
    }

//    @Test
//    public void itReturnsAStringOverAGivenRange() {
//        dataTable.addData("/foo", "Body", "ABCDEFG");
//        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=0-4").build();
//        assertEquals("ABCDE", dataTable.partialContent(request));
//    }
//
//    @Test
//    public void itHandlesAMissingLowerBound() {
//        dataTable.addData("/foo", "Body", "ABCDEFG");
//        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=-5").build();
//        assertEquals("ABCDEF", dataTable.partialContent(request));
//    }
//
//    @Test
//    public void itHandlesAMissingUpperBound() {
//        dataTable.addData("/foo", "Body", "ABCDEFG");
//        Request request = new RequestBuilder().addUrl("/foo").addRange("bytes=2-").build();
//        assertEquals("CDEFG", dataTable.partialContent(request));
//    }

}
