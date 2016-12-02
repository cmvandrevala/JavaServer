package http_response;

import utilities.FormattedStrings;

public class Response418 implements HTTPResponse {

    public int statusCode() {
        return 418;
    }

    public String responseString() {
        return "HTTP/1.1 418 I'm a teapot" + FormattedStrings.CRLF +
                "Content-Type: text/plain" + FormattedStrings.CRLF +
                "Content-Length: 110" + FormattedStrings.CRLF +
                "Connection: close" + FormattedStrings.CRLF + FormattedStrings.CRLF +
                "I'm a teapot" + FormattedStrings.CRLF +
                "             ;,'" + FormattedStrings.CRLF +
                "     _o_    ;:;'" + FormattedStrings.CRLF +
                " ,-.'---`.__ ;" + FormattedStrings.CRLF +
                "((j`=====',-'" + FormattedStrings.CRLF +
                " `-\\     /" + FormattedStrings.CRLF +
                "    `-=-'     hjw" + FormattedStrings.CRLF;
    }

    // Image credit to Hayley Jane Wakenshaw at http://www.ascii-code.com/ascii-art/food-and-drinks/coffee-and-tea.php

}

