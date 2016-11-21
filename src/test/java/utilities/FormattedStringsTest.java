package utilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormattedStringsTest {

    @Test
    public void newline() {
        assertEquals("\r\n", FormattedStrings.newline);
    }
}
