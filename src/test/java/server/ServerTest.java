package server;

import http_request.Request;
import http_request.RequestParser;
import http_request.RequestReader;
import http_response.Response;
import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.Router;
import routing.RoutesTable;
import utilities.FormattedStrings;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private Router router;
    private RequestParser parser;

    @Before
    public void setup() {
        DataTable dataTable = new DataTable();
        RoutesTable routesTable = new RoutesTable();
        routesTable.addRoute("/", RoutesTable.Verb.GET);
        routesTable.addRoute("/", RoutesTable.Verb.PUT);

        router = new Router(routesTable, dataTable);
        parser = new RequestParser();
    }

    @Test
    public void responseCodeOf200ForGet() throws IOException {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals("200", response.statusCode());
    }

    @Test
    public void responseCodeOf400() throws IOException {
        String httpRequest = "GET HTTP/1.1" + FormattedStrings.CRLF + "Host:" + FormattedStrings.CRLF + "Content-Length: 63" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text." + FormattedStrings.CRLF + "And this is some more." + FormattedStrings.CRLF + "And even more!" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals("400", response.statusCode());
    }

    @Test
    public void responseCodeOf404() throws IOException {
        String httpRequest = "GET /foo HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Content-Length: 63" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text." + FormattedStrings.CRLF + "And this is some more." + FormattedStrings.CRLF + "And even more!" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals("404", response.statusCode());
    }

    @Test
    public void responseCodeOf411() throws IOException {
        String httpRequest = "PUT / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + FormattedStrings.CRLF + "data=foo" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals("411", response.statusCode());
    }

}