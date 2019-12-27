package system;

public class LeafStub extends Leaf{

    public LeafStub(String name, int size) throws OutOfSpaceException {
        super(name, size);
        this.allocations = new int[0];
    }


}
