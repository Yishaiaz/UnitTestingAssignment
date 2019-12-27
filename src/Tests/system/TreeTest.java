package system;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {
    /**
     * this is a unit test for the class Tree
     */
    private Leaf leaf;
    private Tree mainTree;
    private Tree treeMinor;

    /**
     * setting up the necessary/repeating environment variables
     */
    @Before
    public void setUp(){
        FileSystem.fileStorage = new SpaceStub(1);
        try{
            // creating the main tree and its children, one node one sub-tree
            mainTree = new Tree("MainTree");
            leaf = new Leaf("leaf1", 1);
            treeMinor = new Tree("MinorTree");
            // putting the subtree and node in the main tree
            mainTree.children.put(leaf.name, leaf);
            mainTree.children.put("MinorTree", treeMinor);
        }catch (Exception e){
            fail("problem setting up the test");
        }
    }

    /**
     * tests the constructor of the class
     */
    @Test
    public void createTree(){
        assertEquals("MainTree", this.mainTree.name);
    }

    /**
     * tests the getChildByName() method with an existing child of the tree
     */
    @Test
    public void getChildByNameWithRealName() {
        Tree testTree = mainTree.GetChildByName(treeMinor.name);
        assertEquals(treeMinor, testTree);
    }

    /**
     * tests the getChildByName() method with a non existing name.
     * making sure it creates it as a subtree Tree instance with the correct name.
     */
    @Test
    public void getChildByNameWithNotRealName() {
        Tree notExist = mainTree.GetChildByName("NotExist");
        assertEquals(mainTree, notExist.parent);
        assertEquals("NotExist", notExist.name);
    }

    /**
     * logical failure, the function should not fail, but it will throw a ClassCastException,
     * which it doesn't declare to might do, therefore a logical failure.
     */
    @Test
    public void getChildByNameOfLeaf(){
        mainTree.GetChildByName(leaf.name);
    }



}