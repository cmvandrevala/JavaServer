package http_request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class ParameterDecoder {

    String decode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, "UTF-8");
    }

}
