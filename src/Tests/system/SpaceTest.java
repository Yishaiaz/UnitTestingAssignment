package system;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {
    private Space space;

    @Before
    public void setUp(){
        FileSystem fs = new FileSystem();
        this.space = new Space(10);
    }
    @Test
    public void allocWithSufficientSpace() {
        try{
            Leaf leaf = new Leaf("just a leaf",2);
            this.space.Alloc(2, leaf);
        }catch(Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    public void dealloc() {
    }

    @Test
    public void countFreeSpace() {
    }

    @Test
    public void getAlloc() {
    }
}