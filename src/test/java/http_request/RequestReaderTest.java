package http_request;

import org.junit.Test;
import utilities.FormattedStrings;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class RequestReaderTest {

    @Test
    public void readOneLineHttpRequest() throws Exception {
        String httpRequest = "GET / HTTP/1.1";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void readOneLineHttpRequestWithNewLine() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void readTwoLineHttpRequest() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void readTwoLineHttpRequestWithNewline() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void readThreeLineHttpRequest() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 5000";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void readThreeLineHttpRequestWithNewline() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void httpRequestWithBody() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Content-Length: 23" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text.";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void httpRequestWithBodyAndNewline() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Content-Length: 23" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text." + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void httpRequestWithMultiLineBody() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Content-Length: 63" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text." + FormattedStrings.CRLF + "And this is some more." + FormattedStrings.CRLF + "And even more!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest + FormattedStrings.CRLF, outputString);
    }

    @Test
    public void httpRequestWithMultiLineBodyAndTrailingNewLine() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Content-Length: 63" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text." + FormattedStrings.CRLF + "And this is some more." + FormattedStrings.CRLF + "And even more!" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

    @Test
    public void itCorrectlyReadsAnAuthorization() throws Exception {
        String httpRequest = "GET / HTTP/1.1" + FormattedStrings.CRLF + "Host: Some Localhost" + FormattedStrings.CRLF + "Content-Length: 0" + FormattedStrings.CRLF + "Keep Alive: 6000" + FormattedStrings.CRLF + "Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==" + FormattedStrings.CRLF;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequest.getBytes("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String outputString = RequestReader.readHttpRequest(bufferedReader);
        assertEquals(httpRequest, outputString);
    }

}
