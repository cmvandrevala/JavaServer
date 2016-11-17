package routing;

import java.io.File;

class PathToUrlMapper {

    String publicDirectory = "cob_spec/public";
    String rootDirectory = "www";

    File pathCorrespondingToUrl(String url) {

        if(urlCorrespondsToIndex(url)) {
            return new File(this.rootDirectory + "/index.html");
        } else {
            return new File(this.rootDirectory + "/" + url.substring(1) + ".html");
        }

    }

    private boolean urlCorrespondsToIndex(String url) {
        if(url.equals("") || url.equals("/") || url.contains(".")) {
            return true;
        } else {
            return false;
        }
    }
}
