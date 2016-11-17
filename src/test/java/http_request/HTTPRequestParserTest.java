package http_request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HTTPRequestParserTest {

    private HTTPRequestParser builder;

    @Before
    public void setup() {
        builder = new HTTPRequestParser();
    }

    @Test
    public void emptyRequestHasNoVerb() {
        assertEquals("", builder.build("").verb());
    }

    @Test
    public void emptyRequestHasNoProtocol() {
        assertEquals("", builder.build("").protocol());
    }

    @Test
    public void emptyRequestHasNoUrl() {
        assertEquals("", builder.build("").url());
    }

    @Test
    public void emptyRequestIsLabeledAsABadRequest() {
        assertEquals(true, builder.build("").isBadRequest());
    }

    @Test
    public void oneLineRequestHasAVerb() {
        String inputString = "GET /foo HTTP/1.1";
        assertEquals("GET", builder.build(inputString).verb());
    }

    @Test
    public void oneLineRequestHasAProtocol() {
        String inputString = "GET /foo HTTP/1.1";
        assertEquals("HTTP/1.1", builder.build(inputString).protocol());
    }

    @Test
    public void oneLineRequestHasAUrl() {
        String inputString = "GET /foo HTTP/1.1";
        assertEquals("/foo", builder.build(inputString).url());
    }

    @Test
    public void oneLineRequestIsNotLabeledAsABadRequest() {
        String inputString = "GET /foo HTTP/1.1";
        assertEquals(false, builder.build(inputString).isBadRequest());
    }

    @Test
    public void twoLineRequestHasAVerb() {
        String inputString = "HEAD /foo HTTP/2.0\r\nHost: localhost:5000";
        assertEquals("HEAD", builder.build(inputString).verb());
    }

    @Test
    public void twoLineRequestHasAProtocol() {
        String inputString = "HEAD /foo HTTP/2.0\r\nHost: localhost:5000";
        assertEquals("HTTP/2.0", builder.build(inputString).protocol());
    }

    @Test
    public void twoLineRequestHasAUrl() {
        String inputString = "HEAD /foo HTTP/2.0\r\nHost: localhost:5000";
        assertEquals("/foo", builder.build(inputString).url());
    }

    @Test
    public void twoLineRequestHasAHost() {
        String inputString = "HEAD /foo HTTP/2.0\r\nHost: localhost:5000";
        assertEquals("localhost:5000", builder.build(inputString).host());
    }

    @Test
    public void twoLineRequestIsNotLabeledAsABadRequest() {
        String inputString = "HEAD /foo HTTP/2.0\r\nHost: localhost:5000";
        assertEquals(false, builder.build(inputString).isBadRequest());
    }

    @Test
    public void threeLineRequestHasAVerb() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent";
        assertEquals("HEAD", builder.build(inputString).verb());
    }

    @Test
    public void threeLineRequestHasAUrl() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent";
        assertEquals("/bar", builder.build(inputString).url());
    }

    @Test
    public void threeLineRequestHasAProtocol() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent";
        assertEquals("HTTP/1.1", builder.build(inputString).protocol());
    }

    @Test
    public void threeLineRequestHasAHost() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent";
        assertEquals("google.com", builder.build(inputString).host());
    }

    @Test
    public void threeLineRequestHasAUserAgent() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent";
        assertEquals("Some Agent", builder.build(inputString).userAgent());
    }

    @Test
    public void threeLineRequestIsNotLabeledAsABadRequest() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent";
        assertEquals(false, builder.build(inputString).isBadRequest());
    }

    @Test
    public void manyLineRequestHasAVerb() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345\r\nAccept-Encoding: true";
        assertEquals("HEAD", builder.build(inputString).verb());
    }

    @Test
    public void manyLineRequestHasAProtocol() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345\r\nAccept-Encoding: true";
        assertEquals("HTTP/1.1", builder.build(inputString).protocol());
    }

    @Test
    public void manyLineRequestHasAUrl() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345\r\nAccept-Encoding: true";
        assertEquals("/bar", builder.build(inputString).url());
    }

    @Test
    public void manyLineRequestHasAHost() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345\r\nAccept-Encoding: true";
        assertEquals("google.com", builder.build(inputString).host());
    }

    @Test
    public void manyLineRequestHasAUserAgent() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345\r\nAccept-Encoding: true";
        assertEquals("Some Agent", builder.build(inputString).userAgent());
    }

    @Test
    public void manyLineRequestHasAKeepAlive() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345\r\nAccept-Encoding: true";
        assertEquals("12345", builder.build(inputString).keepAlive());
    }

    @Test
    public void manyLineRequestHasAcceptEncoding() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345\r\nAccept-Encoding: true";
        assertEquals("true", builder.build(inputString).acceptEncoding());
    }

    @Test
    public void manyLineRequestIsNotLabeledAsABadRequest() {
        String inputString = "HEAD /bar HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345\r\nAccept-Encoding: true";
        assertEquals(false, builder.build(inputString).isBadRequest());
    }

    @Test
    public void builderGracefullyHandlesCommas() {
        String inputString = "HEAD /baz HTTP/1.1\r\nHost: google.com\r\nUser-Agent: Some, Agent, and More\r\nKeep-Alive: 12345";
        assertEquals("Some, Agent, and More", builder.build(inputString).userAgent());
        assertEquals(false, builder.build(inputString).isBadRequest());
    }

    @Test
    public void itGracefullyHandlesSemicolons() {
        String inputString = "HEAD /baz HTTP/1.1\r\nHost: google;com\r\nUser-Agent: Some Agent\r\nKeep-Alive: 12345";
        assertEquals("google;com", builder.build(inputString).host());
        assertEquals(false, builder.build(inputString).isBadRequest());
    }

    @Test
    public void itDoesNotReturnMoreFieldsThanGiven() {
        String inputString = "HEAD /baz HTML/1.1\r\nHost: Some Host\r\nUser-Agent: Stuff";
        assertEquals("", builder.build(inputString).keepAlive());
    }

    @Test
    public void itReturnsBadRequestForAMalformedFirstLine() {
        String inputString = "HEAD /baz\r\nHost: Some Host\r\nUser-Agent: Stuff";
        assertEquals(true, builder.build(inputString).isBadRequest());
    }

    @Test
    public void itReturnsBadRequestForAMalformedRequestWithMissingColons() {
        String inputString = "HEAD /foo HTTP/2.0\r\nHost: Some Host\r\nUser-Agent = Stuff";
        assertEquals(true, builder.build(inputString).isBadRequest());
    }

    @Test
    public void itReturnsBadRequestForAMalformedRequestWithTooManyColons() {
        String inputString = "HEAD /foo HTTP/2.0\r\nHost: Some Host\r\nUser-Agent: : :: :Stuff";
        assertEquals(true, builder.build(inputString).isBadRequest());
    }

    @Test
    public void manyLineRequestWithABodyReturnsTheBody() {
        String inputString = "PUT /bar HTTP/1.1\r\nUser-Agent: Some Agent\r\nAccept-Encoding: true\r\n\r\nThis is some body text.";
        assertEquals("This is some body text.", builder.build(inputString).body());
    }

    @Test
    public void manyLineRequestWithManyLineBodyReturnsTheBody() {
        String inputString = "PUT /bar HTTP/1.1\r\nUser-Agent: Some Agent\r\nAccept-Encoding: true\r\n\r\nThis is some body text.\r\nAnd this is a second line of text.";
        assertEquals("This is some body text.\r\nAnd this is a second line of text.", builder.build(inputString).body());
    }

}
