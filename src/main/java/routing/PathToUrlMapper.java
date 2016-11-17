package routing;

import java.io.File;

class PathToUrlMapper {

    String publicDirectory = "cob_spec/public";
    String rootDirectory = "www";

    File fileCorrespondingToUrl(String url) {

        if(urlCorrespondsToIndexFile(url)) {
            return indexFile();
        }

        if(htmlFile(url).exists()) {
            return htmlFile(url);
        }

        if(resourceFile(url).exists()) {
            return resourceFile(url);
        }

        return new File("");

    }

    private boolean urlCorrespondsToIndexFile(String url) {
        return url.equals("") || url.equals("/") || url.contains("..");
    }

    private File indexFile() {
        return new File(this.rootDirectory + "/index.html");
    }

    private File htmlFile(String url) {
        return new File(this.rootDirectory + "/" + url.substring(1) + ".html");
    }

    private File resourceFile(String url) {
        return new File(this.publicDirectory + "/" + url.substring(1));
    }

}
