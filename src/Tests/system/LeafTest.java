package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;

public class LeafTest {
    /**
     * This is to test the Leaf class.
     */
    private Tree fakeTree = null;

    /**
     * initialize static file storage to a stub.
     */
    @Before
    public void setUp(){
        FileSystem.fileStorage = new SpaceStub(10);
    }

    /**
     * test leaf name and space allocation code.
     */
    @Test
    public void correctMemoryAllocation(){
        try{
            Leaf leaf = new Leaf("test_name", 2);
            assertEquals(leaf.name, "test_name");
        }catch(Exception e){
            fail("no memory was alocated for the test");
        }
    }

    @Test (expected=OutOfSpaceException.class)
    public void setUpIfthrowsOutOfSpaceException() throws Exception{
        ((SpaceStub)FileSystem.fileStorage).changeSize(0);
        Leaf leaf = new Leaf("name", 5);
    }

}