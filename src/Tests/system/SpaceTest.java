package system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

public class SpaceTest {
    /**
     * this is a test to test the Space class
     */
    private Leaf leaf1;
    private Leaf leaf2;

    /**
     * setting up the necessary/repeating environment variables
     */
    @Before
    public void setUp(){
        try{
            FileSystem.fileStorage = new SpaceStub(10);
            leaf1 = new Leaf("just a leaf",2);
            leaf2 = new Leaf("just a leaf",2);
        }catch(Exception e){
            fail("setting up failed");
        }
    }

    /**
     * testing the constructor
     */
    @Test
    public void createSpace(){
        Space space = new Space(10);
        assertEquals(10, space.countFreeSpace());
        assertEquals(10, space.getAlloc().length);
    }

    /**
     * this is to test if space can handle negative values (as it hasn't declared it might throw an exception)
     * we expect nothing to happen and no exception would be thrown, but dealt in the source code.
     */
    @Test
    public void createSpaceWithNegativeSize(){
        Space space = new Space(-2);
    }

    /**
     * testing the Alloc() method with sufficient space
     */
    @Test
    public void allocWithSufficientSpace() {
        try{
            Space space = new Space(10);
            space.Alloc(2, this.leaf1);
            assertEquals(8, space.countFreeSpace());
            //checking if the leaf got its appropriate locations and not any other ones.
            assertEquals(leaf1, space.getAlloc()[0]);
            assertEquals(leaf1, space.getAlloc()[1]);
            assertNotSame(leaf1, space.getAlloc()[2]);
        }catch(OutOfSpaceException e){
            fail(e.getMessage());
        }
    }

    /**
     * testing Alloc receiving negative value, should not throw an exception because it didn't declare it might.
     */
    @Test
    public void allocWithNegativeValue() {
        try{
            Space space = new Space(10);
            space.Alloc(-2, this.leaf1);
        }catch(OutOfSpaceException e){
            fail("wrong exception");
        }
    }

    /**
     * testing Alloc receiving null value as file, should not throw an exception because it didn't declare it might.
     */
    @Test
    public void allocWithNullLeaf() {
        try{
            Space space = new Space(10);
            space.Alloc(1, null);
        }catch(OutOfSpaceException e){
            fail(e.getMessage());
        }
    }

    /**
     * this will fail, the space returns NullPointerException instead of OutOfSpaceException
     * @throws Exception
     */
    @Test (expected = OutOfSpaceException.class)
    public void allocWithoutSufficientSpace() throws Exception{
        Space space = new Space(1);
        space.Alloc(2, leaf1);
    }

    /**
     * testing the Delloc() method with an existing file
     */
    @Test
    public void deallocExistingFile() {
        Space space = new Space(10);
        try{
            Leaf testLeaf = new Leaf("testLeaf", 2);
            space.Alloc(testLeaf.size, testLeaf);
            int currentSpace = space.countFreeSpace();
            space.Dealloc(testLeaf);
            assertEquals(currentSpace+testLeaf.size, space.countFreeSpace());
        }catch(Exception e){
            fail(e.toString());
        }
    }

    /**
     * testing the Delloc() method with a file that doesn't exist.
     * using a leaf stub.
     */
    @Test
    public void deallocNotExistingFile() {
        Space space = new Space(8);
        int currentFreeSpace = space.countFreeSpace();
        try{
            Tree fakeTree = new Tree("fakeTree");
            LeafStub testLeaf = new LeafStub("Not Real",1);
            testLeaf.parent = fakeTree;
            space.Dealloc(testLeaf);
            assertEquals(currentFreeSpace, space.countFreeSpace());
        }catch (OutOfSpaceException e){
            fail("something went wrong with the setup");
        }
    }
}