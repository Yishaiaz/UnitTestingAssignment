package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

public class LeafTest {

    @Before
    public void setUp(){
        FileSystem fileSystem = new FileSystem(2);
    }

    @Test
    public void initializingTest(){
        try{
            Leaf leaf = new Leaf("test_name", 2);
            assertEquals(leaf.name, "test_name");
        }catch(Exception e){
            fail("no memory was alocated for the test");
        }
    }

    @Test
    public void getNoExistingPath(){
        try{
            Leaf leaf = new Leaf("test_name", 2);
            assertEquals(leaf.getPath().length, 0);
        }catch(Exception e){
            fail("no memory was alocated for the test");
        }

    }

//    @After
//    public

}