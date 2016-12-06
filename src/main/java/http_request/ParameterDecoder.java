package http_request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Hashtable;

public class ParameterDecoder {

    private Hashtable<String,String> conversions = new Hashtable<>();

    public String decode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, "UTF-8");
    }

}
