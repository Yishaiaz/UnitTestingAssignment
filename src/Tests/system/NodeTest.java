package system;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {
    /**
     * this is to test the Node class.
     */
    private Tree fakeTree = null;
    @Before
    public void setUp(){
        FileSystem.fileStorage = new Space(10);
    }
    /**
     * test whether a node WITHOUT a parent and updated depth, is still in depth=0
     */
    @Test
    public void getNoExistingPath(){
        try{
            Leaf leaf = new Leaf("test_name", 1);
            assertEquals(0, leaf.getPath().length);
        }catch(Exception e){
            fail("no memory was alocated for the test");
        }
    }
    @Before
    public void setUpGetRealPathTest(){
        try{
             this.fakeTree = new Tree("name");
        }catch(Exception e){
            fail("could not initialize fake tree");
        }
    }
    /**
     * test whether a node WITH a parent and updated depth, is still in depth=1
     */
    @Test
    public void getRealPath(){
        try{
            Leaf leaf = new Leaf("test_name",1);
            leaf.parent = this.fakeTree;
            leaf.depth = 1;
            assertEquals(1,leaf.getPath().length);
        }catch(Exception e){
            fail("Wrong ");
        }
    }
    /**
     * test whether a node WITH a parent and no updated depth, is still in depth=0
     */
    @Test
    public void getWrongDepth(){
        try{
            Leaf leaf = new Leaf("test_name",1);
            leaf.parent = this.fakeTree;
            assertEquals(0,leaf.getPath().length);
        }catch(Exception e){
            fail("Wrong me");
        }
    }
}