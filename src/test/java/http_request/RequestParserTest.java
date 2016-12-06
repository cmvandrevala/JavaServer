package http_request;

import org.junit.Before;
import org.junit.Test;
import utilities.FormattedStrings;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {
    
    private RequestParser builder;

    @Before
    public void setup() {
        builder = new RequestParser();
    }

    @Test
    public void emptyRequestHasNoVerb() {
        assertEquals("", builder.parse("").verb());
    }

    @Test
    public void emptyRequestHasNoProtocol() {
        assertEquals("", builder.parse("").protocol());
    }

    @Test
    public void emptyRequestHasNoUrl() {
        assertEquals("", builder.parse("").url());
    }

    @Test
    public void emptyRequestIsLabeledAsABadRequest() {
        assertEquals(true, builder.parse("").isBadRequest());
    }

    @Test
    public void oneLineRequestHasAVerb() {
        String inputString = "GET /foo HTTP/1.1";
        assertEquals("GET", builder.parse(inputString).verb());
    }

    @Test
    public void oneLineRequestHasAProtocol() {
        String inputString = "GET /foo HTTP/1.1";
        assertEquals("HTTP/1.1", builder.parse(inputString).protocol());
    }

    @Test
    public void oneLineRequestHasAUrl() {
        String inputString = "GET /foo HTTP/1.1";
        assertEquals("/foo", builder.parse(inputString).url());
    }

    @Test
    public void oneLineRequestIsNotLabeledAsABadRequest() {
        String inputString = "GET /foo HTTP/1.1";
        assertEquals(false, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void twoLineRequestHasAVerb() {
        String inputString = "HEAD /foo HTTP/2.0" + FormattedStrings.CRLF + "Host: localhost:5000";
        assertEquals("HEAD", builder.parse(inputString).verb());
    }

    @Test
    public void twoLineRequestHasAProtocol() {
        String inputString = "HEAD /foo HTTP/2.0" + FormattedStrings.CRLF + "Host: localhost:5000";
        assertEquals("HTTP/2.0", builder.parse(inputString).protocol());
    }

    @Test
    public void twoLineRequestHasAUrl() {
        String inputString = "HEAD /foo HTTP/2.0" + FormattedStrings.CRLF + "Host: localhost:5000";
        assertEquals("/foo", builder.parse(inputString).url());
    }

    @Test
    public void twoLineRequestHasAHost() {
        String inputString = "HEAD /foo HTTP/2.0" + FormattedStrings.CRLF + "Host: localhost:5000";
        assertEquals("localhost:5000", builder.parse(inputString).host());
    }

    @Test
    public void twoLineRequestIsNotLabeledAsABadRequest() {
        String inputString = "HEAD /foo HTTP/2.0" + FormattedStrings.CRLF + "Host: localhost:5000";
        assertEquals(false, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void threeLineRequestHasAVerb() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent";
        assertEquals("HEAD", builder.parse(inputString).verb());
    }

    @Test
    public void threeLineRequestHasAUrl() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent";
        assertEquals("/bar", builder.parse(inputString).url());
    }

    @Test
    public void threeLineRequestHasAProtocol() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent";
        assertEquals("HTTP/1.1", builder.parse(inputString).protocol());
    }

    @Test
    public void threeLineRequestHasAHost() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent";
        assertEquals("google.com", builder.parse(inputString).host());
    }

    @Test
    public void threeLineRequestHasAUserAgent() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent";
        assertEquals("Some Agent", builder.parse(inputString).userAgent());
    }

    @Test
    public void threeLineRequestIsNotLabeledAsABadRequest() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent";
        assertEquals(false, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void manyLineRequestHasAVerb() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345" + FormattedStrings.CRLF + "Accept-Encoding: true";
        assertEquals("HEAD", builder.parse(inputString).verb());
    }

    @Test
    public void manyLineRequestHasAProtocol() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345" + FormattedStrings.CRLF + "Accept-Encoding: true";
        assertEquals("HTTP/1.1", builder.parse(inputString).protocol());
    }

    @Test
    public void manyLineRequestHasAUrl() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345" + FormattedStrings.CRLF + "Accept-Encoding: true";
        assertEquals("/bar", builder.parse(inputString).url());
    }

    @Test
    public void manyLineRequestHasAHost() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345" + FormattedStrings.CRLF + "Accept-Encoding: true";
        assertEquals("google.com", builder.parse(inputString).host());
    }

    @Test
    public void manyLineRequestHasAUserAgent() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345" + FormattedStrings.CRLF + "Accept-Encoding: true";
        assertEquals("Some Agent", builder.parse(inputString).userAgent());
    }

    @Test
    public void manyLineRequestHasAKeepAlive() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345" + FormattedStrings.CRLF + "Accept-Encoding: true";
        assertEquals("12345", builder.parse(inputString).keepAlive());
    }

    @Test
    public void manyLineRequestHasAcceptEncoding() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345" + FormattedStrings.CRLF + "Accept-Encoding: true";
        assertEquals("true", builder.parse(inputString).acceptEncoding());
    }

    @Test
    public void manyLineRequestIsNotLabeledAsABadRequest() {
        String inputString = "HEAD /bar HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345" + FormattedStrings.CRLF + "Accept-Encoding: true";
        assertEquals(false, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void builderGracefullyHandlesCommas() {
        String inputString = "HEAD /baz HTTP/1.1" + FormattedStrings.CRLF + "Host: google.com" + FormattedStrings.CRLF + "User-Agent: Some, Agent, and More" + FormattedStrings.CRLF + "Keep-Alive: 12345";
        assertEquals("Some, Agent, and More", builder.parse(inputString).userAgent());
        assertEquals(false, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void itGracefullyHandlesSemicolons() {
        String inputString = "HEAD /baz HTTP/1.1" + FormattedStrings.CRLF + "Host: google;com" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Keep-Alive: 12345";
        assertEquals("google;com", builder.parse(inputString).host());
        assertEquals(false, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void itDoesNotReturnMoreFieldsThanGiven() {
        String inputString = "HEAD /baz HTML/1.1" + FormattedStrings.CRLF + "Host: Some Host" + FormattedStrings.CRLF + "User-Agent: Stuff";
        assertEquals("", builder.parse(inputString).keepAlive());
    }

    @Test
    public void itReturnsBadRequestForAMalformedFirstLine() {
        String inputString = "HEAD /baz" + FormattedStrings.CRLF + "Host: Some Host" + FormattedStrings.CRLF + "User-Agent: Stuff";
        assertEquals(true, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void itReturnsBadRequestForAMalformedRequestWithMissingColons() {
        String inputString = "HEAD /foo HTTP/2.0" + FormattedStrings.CRLF + "Host: Some Host" + FormattedStrings.CRLF + "User-Agent = Stuff";
        assertEquals(true, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void itReturnsBadRequestForAMalformedRequestWithTooManyColons() {
        String inputString = "HEAD /foo HTTP/2.0" + FormattedStrings.CRLF + "Host: Some Host" + FormattedStrings.CRLF + "User-Agent: : :: :Stuff";
        assertEquals(true, builder.parse(inputString).isBadRequest());
    }

    @Test
    public void manyLineRequestWithABodyReturnsTheBody() {
        String inputString = "PUT /bar HTTP/1.1" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Accept-Encoding: true" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text.";
        assertEquals("This is some body text.", builder.parse(inputString).body());
    }

    @Test
    public void manyLineRequestWithManyLineBodyReturnsTheBody() {
        String inputString = "PUT /bar HTTP/1.1" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Accept-Encoding: true" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text." + FormattedStrings.CRLF + "And this is a second line of text.";
        assertEquals("This is some body text." + FormattedStrings.CRLF + "And this is a second line of text.", builder.parse(inputString).body());
    }

    @Test
    public void manyLineRequestWithManyLineBodyReturnsTheBodyPostVersion() {
        String inputString = "POST /bar HTTP/1.1" + FormattedStrings.CRLF + "User-Agent: Some Agent" + FormattedStrings.CRLF + "Accept-Encoding: true" + FormattedStrings.CRLF + FormattedStrings.CRLF + "This is some body text." + FormattedStrings.CRLF + "And this is a second line of text.";
        assertEquals("This is some body text." + FormattedStrings.CRLF + "And this is a second line of text.", builder.parse(inputString).body());
    }

    @Test
    public void itSeparatesTheQueryParamsStringFromTheUrl() {
        String inputString = "GET /bar?var1=%20%3Cvar_2=this%20is%20More%20text HTTP/1.1" + FormattedStrings.CRLF + "Host: localhost:5000" + FormattedStrings.CRLF;
        assertEquals("/bar", builder.parse(inputString).url());
        assertEquals("var1= <var_2=this is More text", builder.parse(inputString).queryParamsString());
    }

}
