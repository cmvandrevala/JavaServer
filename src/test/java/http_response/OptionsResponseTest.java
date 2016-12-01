package http_response;

import http_request.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import routing.NullAction;
import routing.RoutingTable;
import utilities.FormattedStrings;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;

public class OptionsResponseTest {

    private OptionsResponse response;
    private RoutingTable routingTable = RoutingTable.getInstance();

    @Before
    public void setup() {
        NullAction action = new NullAction();
        routingTable.addRoute("/foo", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/foo", RoutingTable.Verb.HEAD, action);
        routingTable.addRoute("/foo", RoutingTable.Verb.POST, action);
        Hashtable<String,String> params = new Hashtable<String,String>();
        params.put("URL", "/foo");
        Request request = new Request(params);
        response = new OptionsResponse(request);
    }

    @After
    public void teardown() {
        routingTable.clearData();
    }

    @Test
    public void notFoundResponseForNoParams() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Allow: OPTIONS,GET,HEAD,POST" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF;
        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itReturnsTheStatusCode() throws Exception {
        assertEquals(200, response.statusCode());
    }

}