package server;

import http_action.UrlAcceptsCookieAction;
import http_action.UrlRetunsCookieAction;
import http_request.Request;
import http_request.RequestParser;
import http_request.RequestReader;
import http_response.HTTPResponse;
import org.junit.Before;
import org.junit.Test;
import routing.Router;
import routing.RoutesTable;
import utilities.FormattedStrings;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class CookieTest {

    private Router router;
    private RequestParser parser;

    @Before
    public void setup() {
        RoutesTable routesTable = new RoutesTable();
        routesTable.addRoute("/", RoutesTable.Verb.GET);
        routesTable.addRoute("/cookie", RoutesTable.Verb.GET, new UrlRetunsCookieAction());
        routesTable.addRoute("/eat_cookie", RoutesTable.Verb.GET, new UrlAcceptsCookieAction());

        router = new Router(routesTable);
        parser = new RequestParser();
    }

    @Test
    public void itDoesNothingWithACookieForSomeUrls() throws IOException {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Cookie: foo" + FormattedStrings.CRLF;
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 21" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "<h1>Hello World!</h1>";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itDoesNotReturnACookieIfThereAreNoParams() throws IOException {
        String httpRequest = "GET /cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF;
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 21" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "<h1>Hello World!</h1>";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itCanReturnACookieGivenSomeParams() throws IOException {
        String httpRequest = "GET /cookie?type=chocolate HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF;
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Set-Cookie: type=chocolate" + FormattedStrings.CRLF + "Content-Length: 3" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "Eat";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itReturnsAResponseIfThereAreNoCookiesProvided() throws IOException {
        String httpRequest = "GET /eat_cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF;
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 21" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "<h1>Hello World!</h1>";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itReturnsACustomResponseForAMalformedCookie() throws IOException {
        String httpRequest = "GET /eat_cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Cookie: Malformed" + FormattedStrings.CRLF;
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 26" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "Your cookie has no type...";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void itReturnsAMessageForAProperlyFormattedCookie() throws IOException {
        String httpRequest = "GET /eat_cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Cookie: type=oatmeal" + FormattedStrings.CRLF;
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 12" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "mmmm oatmeal";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(expectedResponse, response.responseString());
    }

    @Test
    public void theResponseChangesForDifferentCookieValues() throws IOException {
        String httpRequest = "GET /eat_cookie HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Cookie: type=foo" + FormattedStrings.CRLF;
        String expectedResponse = "HTTP/1.1 200 OK" + FormattedStrings.CRLF + "Content-Type: text/html" + FormattedStrings.CRLF + "Content-Length: 8" + FormattedStrings.CRLF + "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF + "mmmm foo";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(expectedResponse, response.responseString());
    }

}
