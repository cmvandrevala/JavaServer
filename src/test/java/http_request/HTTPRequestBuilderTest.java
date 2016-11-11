package http_request;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class HTTPRequestBuilderTest {

    private HTTPRequestBuilder builder;

    @Before
    public void setup() {
        builder = new HTTPRequestBuilder();
    }

    @Test
    public void emptyRequest() {
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "");
        expectedOutput.put("URL", "");
        expectedOutput.put("Protocol", "");

        assertEquals(expectedOutput, builder.tokenizeRequest(""));
    }

    @Test
    public void oneLineRequest() {
        String inputString = "GET /foo HTTP/1.1";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "GET");
        expectedOutput.put("URL", "/foo");
        expectedOutput.put("Protocol", "HTTP/1.1");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

    @Test
    public void twoLineRequest() {
        String inputString = "HEAD /foo HTTP/2.0\nHost: localhost:5000";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "HEAD");
        expectedOutput.put("URL", "/foo");
        expectedOutput.put("Protocol", "HTTP/2.0");
        expectedOutput.put("Host", "localhost:5000");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

    @Test
    public void threeLineRequest() {
        String inputString = "HEAD /bar HTTP/1.1\nHost: google.com\nUser-Agent: Some Agent";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "HEAD");
        expectedOutput.put("URL", "/bar");
        expectedOutput.put("Protocol", "HTTP/1.1");
        expectedOutput.put("Host", "google.com");
        expectedOutput.put("User-Agent", "Some Agent");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

    @Test
    public void manyLineRequest() {
        String inputString = "HEAD /bar HTTP/1.1\nHost: google.com\nUser-Agent: Some Agent\nKeep-Alive: 12345\nAccept-Encoding: true";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "HEAD");
        expectedOutput.put("URL", "/bar");
        expectedOutput.put("Protocol", "HTTP/1.1");
        expectedOutput.put("Host", "google.com");
        expectedOutput.put("User-Agent", "Some Agent");
        expectedOutput.put("Keep-Alive", "12345");
        expectedOutput.put("Accept-Encoding", "true");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

    @Test
    public void itGracefullyHandlesCommas() {
        String inputString = "HEAD /baz HTTP/1.1\nHost: google,com\nUser-Agent: Some, Agent, and More\nKeep-Alive: 12345";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "HEAD");
        expectedOutput.put("URL", "/baz");
        expectedOutput.put("Protocol", "HTTP/1.1");
        expectedOutput.put("Host", "google,com");
        expectedOutput.put("User-Agent", "Some, Agent, and More");
        expectedOutput.put("Keep-Alive", "12345");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

    @Test
    public void itGracefullyHandlesSemicolons() {
        String inputString = "HEAD /baz HTTP/1.1\nHost: google;com\nUser-Agent: Som;e Agent\nKeep-Alive: 123;45";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "HEAD");
        expectedOutput.put("URL", "/baz");
        expectedOutput.put("Protocol", "HTTP/1.1");
        expectedOutput.put("Host", "google;com");
        expectedOutput.put("User-Agent", "Som;e Agent");
        expectedOutput.put("Keep-Alive", "123;45");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

    @Test
    public void itDoesNotReturnMoreFieldsThanGiven() {
        String inputString = "HEAD /baz HTML/1.1\nHost: Some Host\nUser-Agent: Stuff";
        Hashtable<String, String> actualOutput = builder.tokenizeRequest(inputString);
        assertEquals(null, actualOutput.get("Some Missing Token"));
    }

    @Test
    public void itReturnsAnEmptySetOfTokensForMalformedFirstLine() {
        String inputString = "HEAD /baz\nHost: Some Host\nUser-Agent: Stuff";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "");
        expectedOutput.put("URL", "");
        expectedOutput.put("Protocol", "");
        expectedOutput.put("Host", "");
        expectedOutput.put("User-Agent", "");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

    @Test
    public void itReturnsAnEmptyValueForAMalformedLineWithNoColons() {
        String inputString = "HEAD /foo HTTP/2.0\nHost: Some Host\nUser-Agent = Stuff";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "HEAD");
        expectedOutput.put("URL", "/foo");
        expectedOutput.put("Protocol", "HTTP/2.0");
        expectedOutput.put("Host", "Some Host");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

    @Test
    public void itReturnsAnEmptyValueForAMalformedLineWithTooManyColons() {
        String inputString = "HEAD /foo HTTP/2.0\nHost: Some Host\nUser-Agent: : :: :Stuff";
        Hashtable<String, String> expectedOutput = new Hashtable<String, String>();
        expectedOutput.put("Verb", "HEAD");
        expectedOutput.put("URL", "/foo");
        expectedOutput.put("Protocol", "HTTP/2.0");
        expectedOutput.put("Host", "Some Host");

        assertEquals(expectedOutput, builder.tokenizeRequest(inputString));
    }

}
