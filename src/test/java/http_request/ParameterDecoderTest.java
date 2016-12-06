package http_request;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ParameterDecoderTest {

    private ParameterDecoder decoder;

    @Before
    public void setup() {
        decoder = new ParameterDecoder();
    }

    @Test
    public void noParameterDecoding() throws Exception {
        assertEquals("some string", decoder.decode("some string"));
    }

    @Test
    public void space() throws Exception  {
        assertEquals(" ", decoder.decode("%20"));
    }

    @Test
    public void twoSpaces() throws Exception  {
        assertEquals("  ", decoder.decode("%20%20"));
    }

    @Test
    public void lessThanSign() throws Exception  {
        assertEquals("<", decoder.decode("%3C"));
    }

    @Test
    public void greaterThanSign() throws Exception  {
        assertEquals(">", decoder.decode("%3E"));
    }

    @Test
    public void comma() throws Exception  {
        assertEquals(",", decoder.decode("%2C"));
    }

    @Test
    public void equalsSign() throws Exception  {
        assertEquals("=",decoder.decode("%3D"));
    }

    @Test
    public void minusSign() throws Exception  {
        assertEquals("-",decoder.decode("-"));
    }

    @Test
    public void astrix() throws Exception  {
        assertEquals("*",decoder.decode("*"));
    }

    @Test
    public void ampersand() throws Exception  {
        assertEquals("&",decoder.decode("%26"));
    }

    @Test
    public void atSign() throws Exception  {
        assertEquals("@",decoder.decode("%40"));
    }

    @Test
    public void hashtag() throws Exception  {
        assertEquals("#",decoder.decode("%23"));
    }

    @Test
    public void dollarSign() throws Exception  {
        assertEquals("$",decoder.decode("%24"));
    }

    @Test
    public void leftSquareBrace() throws Exception  {
        assertEquals("[",decoder.decode("%5B"));
    }

    @Test
    public void rightSquareBrace() throws Exception  {
        assertEquals("]",decoder.decode("%5D"));
    }

    @Test
    public void colon() throws Exception  {
        assertEquals(":",decoder.decode("%3A"));
    }

    @Test
    public void doubleQuote() throws Exception  {
        assertEquals("\"",decoder.decode("%22"));
    }

    @Test
    public void questionMark() throws Exception  {
        assertEquals("?",decoder.decode("%3F"));
    }

    @Test
    public void exclamationMark() throws Exception  {
        assertEquals("!",decoder.decode("!"));
    }

    @Test
    public void plusSign() throws Exception  {
        assertEquals("+",decoder.decode("%2B"));
    }

    @Test
    public void lessThanSignWithSpaces() throws Exception  {
        assertEquals(" < ", decoder.decode("%20%3C%20"));
    }

}
