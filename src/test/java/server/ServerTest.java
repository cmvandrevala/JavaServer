package server;

import http_request.Request;
import http_request.RequestParser;
import http_request.RequestReader;
import http_response.HTTPResponse;
import org.junit.Ignore;
import org.junit.Test;
import routing.Router;
import routing.RoutingTable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {

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
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\nContent-Length: 63\r\nKeep Alive: 6000\r\n\r\nThis is some body text.\r\nAnd this is some more.\r\nAnd even more!\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        RoutingTable table = RoutingTable.getInstance();
        table.addRoute("/", "GET");

        RequestReader reader = new RequestReader();
        RequestParser parser = new RequestParser();
        Router router = new Router(table);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = reader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void responseCodeOf400() throws IOException {
        String httpRequest = "GET HTTP/1.1\r\nHost:\r\nContent-Length: 63\r\nKeep Alive: 6000\r\n\r\nThis is some body text.\r\nAnd this is some more.\r\nAnd even more!\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        RoutingTable table = RoutingTable.getInstance();
        table.addRoute("/", "GET");

        RequestReader reader = new RequestReader();
        RequestParser parser = new RequestParser();
        Router router = new Router(table);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = reader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(response.statusCode(), 400);
    }

    @Test
    public void responseCodeOf404() throws IOException {
        String httpRequest = "GET /foo HTTP/1.1\r\nHost: Some Localhost\r\nContent-Length: 63\r\nKeep Alive: 6000\r\n\r\nThis is some body text.\r\nAnd this is some more.\r\nAnd even more!\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        RoutingTable table = RoutingTable.getInstance();
        table.addRoute("/", "GET");

        RequestReader reader = new RequestReader();
        RequestParser parser = new RequestParser();
        Router router = new Router(table);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = reader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(response.statusCode(), 404);
    }

    @Ignore
    public void responseCodeOf411() throws IOException {
        String httpRequest = "PUT / HTTP/1.1\r\nHost: Some Localhost\r\n\r\ndata=foo\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));

        RoutingTable table = RoutingTable.getInstance();
        table.addRoute("/", "PUT");

        RequestReader reader = new RequestReader();
        RequestParser parser = new RequestParser();
        Router router = new Router(table);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String incomingRequest = reader.readHttpRequest(bufferedReader);

        Request request = parser.parse(incomingRequest);

        HTTPResponse response = router.route(request);

        bufferedReader.close();

        assertEquals(response.statusCode(), 411);
    }



}