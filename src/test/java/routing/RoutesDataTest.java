package routing;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RoutesDataTest {

    private RoutesData data;

    @Before
    public void setup() {
        this.data = new RoutesData();
    }

    @Test
    public void itCanAddDataToARoute() {
        this.data.add("/", "data", "foo");
        assertEquals("foo", this.data.retrieve("/", "data"));
    }

    @Test
    public void itCanAddDataWithADifferentKeyAndValueToARoute() {
        this.data.add("/", "anotherKey", "anotherValue");
        assertEquals("anotherValue", this.data.retrieve("/", "anotherKey"));
    }

    @Test
    public void itCanAddDataToOneRouteMultipleTimes() {
        this.data.add("/foo","a","b");
        this.data.add("/foo","c","d");
        this.data.add("/foo","e","f");
        assertEquals("b", this.data.retrieve("/foo", "a"));
    }

    @Test
    public void itCanAddDataToDifferentRoutes() {
        this.data.add("/foo","a","b");
        this.data.add("/bar","c","d");
        this.data.add("/baz","e","f");
        assertEquals("d", this.data.retrieve("/bar", "c"));
    }

    @Test
    public void itReturnsNoDataIfAKeyIsNotDefined() {
        this.data.add("/baz","a","b");
        assertEquals("", this.data.retrieve("/baz", "c"));
    }

    public void itReturnsNoDataIfAUrlIsNotDefined() {
        assertEquals("",this.data.retrieve("invalid","should not return"));
    }

    @Test
    public void itCanRemoveAllOfTheDataFromARoute() {
        this.data.add("/foo","a","b");
        this.data.add("/foo","c","d");
        this.data.removeAll("/foo");
        assertEquals("", this.data.retrieve("/foo", "a"));
        assertEquals("", this.data.retrieve("/foo", "c"));
    }
}
