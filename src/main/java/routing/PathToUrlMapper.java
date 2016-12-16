package routing;

import java.io.File;

public class PathToUrlMapper {

    File publicDirectory = new File(System.getProperty("user.dir") + "/public");

    public File fileCorrespondingToUrl(String url) {

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
        return new File(this.publicDirectory, "index.html");
    }

    private File htmlFile(String url) {
        return new File(this.publicDirectory, url.substring(1) + ".html");
    }

    private File resourceFile(String url) {
        return new File(this.publicDirectory, url.substring(1));
    }

}
