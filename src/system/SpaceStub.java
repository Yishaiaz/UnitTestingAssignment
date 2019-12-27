package system;

public class SpaceStub extends Space {
    private int size;

    public SpaceStub(int size) {
        super(size);
        this.size= size;
    }

    @Override
    public void Alloc(int size, Leaf file) throws OutOfSpaceException {
        if (size > this.size){
            throw new OutOfSpaceException();
        }
    }

    public void changeSize(int size){
        this.size = size;
    }
}
