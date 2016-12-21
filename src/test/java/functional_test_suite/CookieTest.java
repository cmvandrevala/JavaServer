package functional_test_suite;

import http_action.UrlAcceptsCookieAction;
import http_action.UrlReturnsCookieAction;
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

import static junit.framework.TestCase.assertEquals;

public class CookieTest {

    private Router router;
    private RequestParser parser;

    @Before
    public void setup() {
        DataTable dataTable = new DataTable();
        RoutesTable routesTable = new RoutesTable();
        routesTable.addRoute("/", RoutesTable.Verb.GET);
        routesTable.addRoute("/cookie", RoutesTable.Verb.GET, new UrlReturnsCookieAction());
        routesTable.addRoute("/eat_cookie", RoutesTable.Verb.GET, new UrlAcceptsCookieAction());

        router = new Router(routesTable, dataTable);
        parser = new RequestParser();
    }

    @Test
    public void itDoesNothingWithACookieForSomeUrls() throws IOException {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Cookie: foo" + FormattedStrings.CRLF;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals(200, response.statusCode());
        assertEquals("OK", response.statusMessage());
        assertEquals("", response.body());
    }

    @Test
    public void itDoesNotReturnACookieIfThereAreNoParams() throws IOException {
        String httpRequest = "GET /cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals(200, response.statusCode());
        assertEquals("OK", response.statusMessage());
        assertEquals("<h1>Hello World!</h1>", response.body());
    }

    @Test
    public void itCanReturnACookieGivenSomeParams() throws IOException {
        String httpRequest = "GET /cookie?type=chocolate HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals(200, response.statusCode());
        assertEquals("OK", response.statusMessage());
        assertEquals("Eat", response.body());
        assertEquals("type=chocolate", response.setCookie());
    }

    @Test
    public void itReturnsAResponseIfThereAreNoCookiesProvided() throws IOException {
        String httpRequest = "GET /eat_cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals(200, response.statusCode());
        assertEquals("OK", response.statusMessage());
        assertEquals("<h1>Hello World!</h1>", response.body());
        assertEquals("", response.setCookie());
    }

    @Test
    public void itReturnsACustomResponseForAMalformedCookie() throws IOException {
        String httpRequest = "GET /eat_cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Cookie: Malformed" + FormattedStrings.CRLF;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals(200, response.statusCode());
        assertEquals("OK", response.statusMessage());
        assertEquals("Your cookie has no type...", response.body());
        assertEquals("", response.setCookie());
    }

    @Test
    public void itReturnsAMessageForAProperlyFormattedCookie() throws IOException {
        String httpRequest = "GET /eat_cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Cookie: type=oatmeal" + FormattedStrings.CRLF;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals(200, response.statusCode());
        assertEquals("OK", response.statusMessage());
        assertEquals("mmmm oatmeal", response.body());
        assertEquals("", response.setCookie());
    }

    @Test
    public void theResponseChangesForDifferentCookieValues() throws IOException {
        String httpRequest = "GET /eat_cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Cookie: type=foo" + FormattedStrings.CRLF;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        Response response = router.route(request);

        bufferedReader.close();

        assertEquals(200, response.statusCode());
        assertEquals("OK", response.statusMessage());
        assertEquals("mmmm foo", response.body());
        assertEquals("", response.setCookie());
    }

}
