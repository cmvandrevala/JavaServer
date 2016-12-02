package http_action;

import http_request.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import routing.RoutesTable;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;

public class PostActionTest {

    private RoutesTable routesTable = RoutesTable.getInstance();

    @Before
    public void setup() {
        routesTable.addRoute("/", RoutesTable.Verb.POST, new PostAction());
    }

    @After
    public void teardown() {
        routesTable.clearRoutes();
    }

    @Test
    public void itAddsOnePieceOfDataToTheRoute() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "POST");
        params.put("URL","/");
        params.put("Content-Length", "3");
        params.put("Body", "a=1");

        routesTable.executeAction(new Request(params));
        assertEquals("1",routesTable.retrieveData("/","a"));
    }

    @Test
    public void itAddsMultiplePiecesOfDataInDifferentRequestsToTheRoute() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "POST");
        params.put("URL","/");
        params.put("Content-Length", "5");
        params.put("Body", "ab=cd");

        routesTable.executeAction(new Request(params));

        params = new Hashtable<>();
        params.put("Verb", "POST");
        params.put("URL","/");
        params.put("Content-Length", "22");
        params.put("Body", "data=this is some data");

        routesTable.executeAction(new Request(params));

        assertEquals("cd",routesTable.retrieveData("/","ab"));
        assertEquals("this is some data",routesTable.retrieveData("/","data"));
    }

    @Test
    public void itIsIdempotent() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "POST");
        params.put("URL","/");
        params.put("Content-Length", "7");
        params.put("Body", "a=cdefg");

        routesTable.executeAction(new Request(params));
        routesTable.executeAction(new Request(params));

        assertEquals("cdefg",routesTable.retrieveData("/","a"));
    }

    @Test
    public void itDoesNotTouchOtherRoutes() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "POST");
        params.put("URL","/");
        params.put("Content-Length", "3");
        params.put("Body", "v=x");

        routesTable.addRoute("/bar", RoutesTable.Verb.GET, new NullAction());

        routesTable.executeAction(new Request(params));

        assertEquals("",routesTable.retrieveData("/bar","v"));
    }

    @Test
    public void itCreatesAKeyIfThereIsNoEqualsSign() {
        Hashtable<String,String> params = new Hashtable<>();
        params.put("Verb", "POST");
        params.put("URL","/");
        params.put("Content-Length", "3");
        params.put("Body", "No equals sign here!");

        routesTable.executeAction(new Request(params));

        assertEquals("No equals sign here!",routesTable.retrieveData("/","body"));
    }
}
