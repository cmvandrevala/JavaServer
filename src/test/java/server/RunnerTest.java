package server;

import org.junit.Before;
import org.junit.Test;
import routing.DataTable;
import routing.PathToUrlMapper;
import routing.RoutesTable;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class RunnerTest {

    private Runner runner;

    @Before
    public void setup() throws IOException {
        RoutesTable routesTable = new RoutesTable();
        DataTable dataTable = new DataTable();
        this.runner = new Runner(routesTable, dataTable, new PathToUrlMapper(""));
    }

    @Test
    public void itUsesAPortNumberOf5000ByDefault() throws IOException {
        assertEquals(5000, runner.portNumber);
    }

    @Test
    public void thePortNumberCanBeSet() throws IOException {
        this.runner.portNumber = 100;
        assertEquals(100, runner.portNumber);
    }

    @Test
    public void itUsesTenThreadsByDefault() throws IOException {
        assertEquals(10, this.runner.numberOfThreads);
    }

    @Test
    public void theNumberOfThreadsCanBeSet() throws IOException {
        this.runner.numberOfThreads = 5;
        assertEquals(5, this.runner.numberOfThreads);
    }

    @Test
    public void theRunnerIsStatusIsNotStoppedUponInitialization() throws IOException {
        assertFalse(this.runner.isStopped());
    }

    @Test
    public void theRunnerStatusCanBeChanged() throws IOException {
        this.runner.stop();
        assertTrue(runner.isStopped());
    }

}
