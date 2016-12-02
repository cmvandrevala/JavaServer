package http_action;

import http_request.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import routing.RoutesTable;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;

public class DeleteActionTest {

    private RoutesTable routesTable = RoutesTable.getInstance();

    @Before
    public void setup() {
        routesTable.addRoute("/", RoutesTable.Verb.DELETE,new DeleteAction());
    }

    @After
    public void teardown() {
        routesTable.clearRoutes();
    }

    @Test
    public void itDoesNothingIfThereIsNoDataStoredInARoute() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "DELETE");
        params.put("URL","/");

        routesTable.executeAction(new Request(params));
        assertEquals("",routesTable.retrieveData("/","key"));
    }

    @Test
    public void itDeletesOnePieceOfDataStoredInARoute() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "DELETE");
        params.put("URL","/");

        routesTable.addData("/","key","value");

        routesTable.executeAction(new Request(params));
        assertEquals("",routesTable.retrieveData("/","key"));
    }

    @Test
    public void itDeletesAllOfTheDataStoredInARoute() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "DELETE");
        params.put("URL","/");

        routesTable.addData("/","key1","value1");
        routesTable.addData("/","key2","value2");
        routesTable.addData("/","key3","value3");
        routesTable.addData("/","key4","value4");

        assertEquals("value1",routesTable.retrieveData("/","key1"));
        assertEquals("value2",routesTable.retrieveData("/","key2"));
        assertEquals("value3",routesTable.retrieveData("/","key3"));
        assertEquals("value4",routesTable.retrieveData("/","key4"));

        routesTable.executeAction(new Request(params));

        assertEquals("",routesTable.retrieveData("/","key1"));
        assertEquals("",routesTable.retrieveData("/","key2"));
        assertEquals("",routesTable.retrieveData("/","key3"));
        assertEquals("",routesTable.retrieveData("/","key4"));

    }

    @Test
    public void itDoesNotTouchOtherRoutes() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "DELETE");
        params.put("URL","/");

        routesTable.addData("/","key1","value1");
        routesTable.addData("/foo","key2","value2");

        routesTable.executeAction(new Request(params));

        assertEquals("value2",routesTable.retrieveData("/foo","key2"));

    }
}
