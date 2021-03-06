package routing;

import java.io.File;

public class PathToUrlMapper {

    File publicDirectory;

    public PathToUrlMapper(String publicDirectory) {
        this.publicDirectory = new File(publicDirectory);
    }

    public File fileCorrespondingToUrl(String url) {

        if(urlCorrespondsToIndexFile(url)) {
            return indexFile();
        }

        if(resourceFile(url).exists()) {
            return resourceFile(url);
        }

        return new File(this.publicDirectory,"");
    }

    public File[] filesInPublicDirectory() {
        return publicDirectory.listFiles();
    }

    private boolean urlCorrespondsToIndexFile(String url) {
        return url.equals("") || url.equals("/") || url.contains("..");
    }

    private File indexFile() {
        return new File(this.publicDirectory, "index.html");
    }

    private File resourceFile(String url) {
        return new File(this.publicDirectory, url.substring(1));
    }

}
