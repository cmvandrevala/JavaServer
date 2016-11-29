package http_request;

import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class RequestReaderTest {

    private RequestReader reader;

    @Before
    public void setup() {
        this.reader = new RequestReader();
    }

    @Test
    public void readOneLineHttpRequest() throws Exception {
        String httpRequest = "GET / HTTP/1.1";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void readOneLineHttpRequestWithNewLine() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void readTwoLineHttpRequest() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void readTwoLineHttpRequestWithNewline() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void readThreeLineHttpRequest() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\nKeep Alive: 5000";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void readThreeLineHttpRequestWithNewline() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\nKeep Alive: 6000\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void httpRequestWithBody() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\nContent-Length: 23\r\nKeep Alive: 6000\r\n\r\nThis is some body text.";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void httpRequestWithBodyAndNewline() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\nContent-Length: 23\r\nKeep Alive: 6000\r\n\r\nThis is some body text.\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void httpRequestWithMultiLineBody() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\nContent-Length: 63\r\nKeep Alive: 6000\r\n\r\nThis is some body text.\r\nAnd this is some more.\r\nAnd even more!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void httpRequestWithMultiLineBodyAndTrailingNewLine() throws Exception {
        String httpRequest = "GET / HTTP/1.1\r\nHost: Some Localhost\r\nContent-Length: 63\r\nKeep Alive: 6000\r\n\r\nThis is some body text.\r\nAnd this is some more.\r\nAnd even more!\r\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = reader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

}
