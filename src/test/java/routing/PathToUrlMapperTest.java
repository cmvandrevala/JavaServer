package routing;

import org.junit.Before;
import org.junit.Ignore;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PathToUrlMapperTest {

    PathToUrlMapper mapper;

    @Before
    public void setup() {
        this.mapper = new PathToUrlMapper();
    }

    @Ignore
    public void thereIsAPublicDirectoryForAssets() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/cob_spec/public"), mapper.publicDirectory);
    }

    @Ignore
    public void thereIsARootDirectory() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/www"), mapper.rootDirectory);
    }

    @Ignore
    public void anEmptyUrlReturnsTheIndex() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/www/index.html"), mapper.fileCorrespondingToUrl(""));
    }

    @Ignore
    public void aSingleSlashReturnsTheIndex() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/www/index.html"), mapper.fileCorrespondingToUrl("/"));
    }

    @Ignore
    public void fooMapsToAFile() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/www/foo.html"), mapper.fileCorrespondingToUrl("/foo"));
    }

    @Ignore
    public void aLongerUrlMapsToAFile() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/www/foo/bar/baz/quo.html"), mapper.fileCorrespondingToUrl("/foo/bar/baz/quo"));
    }

    @Ignore
    public void periodsAreIgnoredAndTheUserIsSentToTheIndex() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/www/index.html"), mapper.fileCorrespondingToUrl("/../../../quo"));
    }

    @Ignore
    public void file1MapsToAPublicResource() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/cob_spec/public/file1"), mapper.fileCorrespondingToUrl("/file1"));
    }

    @Ignore
    public void file2MapsToAPublicResource() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/cob_spec/public/file2"), mapper.fileCorrespondingToUrl("/file2"));
    }

    @Ignore
    public void gifFilesMapToAPublicResource() {
        assertEquals(new File("/Users/cyrus/IdeaProjects/JavaServer/cob_spec/public/image.gif"), mapper.fileCorrespondingToUrl("/image.gif"));
    }

}
