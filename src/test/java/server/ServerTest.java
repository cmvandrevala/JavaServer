package server;

import http_request.Request;
import http_request.RequestParser;
import http_request.RequestReader;
import http_response.HTTPResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import http_action.NullAction;
import routing.Router;
import routing.RoutingTable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {
    
    private RoutingTable routingTable = RoutingTable.getInstance();
    private Router router;
    private RequestParser parser;

    @Before
    public void setup() {
        NullAction action = new NullAction();
        routingTable.addRoute("/", RoutingTable.Verb.GET, action);
        routingTable.addRoute("/", RoutingTable.Verb.PUT, action);

        router = new Router();
        parser = new RequestParser();
    }

    @After
    public void teardown() {
        this.routingTable.clearData();
        router = null;
        parser = null;
    }

    @Test
    public void incomingConnectionsFailWhenTheServerIsOff() {
        try {
            new Socket("localhost", 5000);
        } catch (Exception e) {
            assertEquals("Connection refused", e.getMessage().trim());
        }
    }

    @Test
    public void responseCodeOf200ForGet() throws IOException {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\nKeep Alive: 6000\r\nContent-Length: 0\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(200, response.statusCode());
    }

    @Test
    public void responseCodeOf400() throws IOException {
        String httpRequest = "GET HTTP/1.1\r\nHost:\r\nContent-Length: 63\r\nKeep Alive: 6000\r\n\r\nThis is some body text.\r\nAnd this is some more.\r\nAnd even more!\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(400, response.statusCode());
    }

    @Test
    public void responseCodeOf404() throws IOException {
        String httpRequest = "GET /foo HTTP/1.1\r\nHost: Some Localhost\r\nContent-Length: 63\r\nKeep Alive: 6000\r\n\r\nThis is some body text.\r\nAnd this is some more.\r\nAnd even more!\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(404, response.statusCode());
    }

    @Test
    public void responseCodeOf411() throws IOException {
        String httpRequest = "PUT / HTTP/1.1\r\nHost: Some Localhost\r\n\r\ndata=foo\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = RequestReader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(411, response.statusCode());
    }

}