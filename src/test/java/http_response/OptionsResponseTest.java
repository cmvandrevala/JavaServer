package http_response;

import http_request.Request;
import http_request.RequestBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import http_action.NullAction;
import routing.RoutesTable;
import utilities.FormattedStrings;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;

public class OptionsResponseTest {

    private OptionsResponse response;
    private RoutesTable routesTable = RoutesTable.getInstance();

    @Before
    public void setup() {
        routesTable.addRoute("/foo", RoutesTable.Verb.GET);
        routesTable.addRoute("/foo", RoutesTable.Verb.HEAD);
        routesTable.addRoute("/foo", RoutesTable.Verb.POST);

        RequestBuilder builder = new RequestBuilder();
        Request request = builder.addUrl("/foo").build();
        response = new OptionsResponse(request);
    }

    @After
    public void teardown() {
        routesTable.clearRoutes();
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