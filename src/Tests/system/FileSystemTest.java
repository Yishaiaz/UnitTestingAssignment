package system;

import org.junit.Before;
import org.junit.Test;
import java.nio.file.DirectoryNotEmptyException;
import static org.junit.Assert.*;

public class FileSystemTest {
    /**
     * This is to test the FileSystem class.
     */
    private Tree tree;
    private Leaf leaf1;
    private FileSystem fileSystemFilled;
    private String[] path = {"root", "firstFolder"};

    /**
     * setting up the the variables needed for the tests.
     * @throws Exception
     */
    @Before
    public void setUpDisk() throws Exception {
        this.fileSystemFilled = new FileSystem(10);
        fileSystemFilled.dir(this.path);
        String[] pathWithFile = {"root", "firstFolder", "tamir"};
        fileSystemFilled.file(pathWithFile,2);
    }

    /**
     * testing the constructor with valid values.
     */
    @Test
    public void createFileSystem(){
        FileSystem fileSystem = new FileSystem(10);
        String[] testRootArray = new String[1];
        testRootArray[0] = "root";
        assertEquals(10, fileSystem.fileStorage.countFreeSpace());
    }

    /**
     * create a file system with negative parameters, should not fail because
     * an exception was not declared to be possibly thrown.
     */
    @Test
    public void createFileSystemNegativeValue(){
        try{
            FileSystem fileSystem = new FileSystem(-1);
        }catch(Exception e){
            fail("An exception was thrown without declaring on it");
        }
    }

    /**
     * creating regular directory
     */
    @Test
    public void dir() {
        FileSystem fileSystem = new FileSystem(10);
        String[] dirPath ={"root", "TestFolder"};
        try{
            fileSystem.dir(dirPath);
        }catch(BadFileNameException e){
            fail("was suppose to create a new directory");
        }
    }

    /**
     * no "root" folder at the beginning of the path.
     * @throws Exception
     */
    @Test (expected = BadFileNameException.class)
    public void dirWithBadName() throws Exception{
        FileSystem fileSystem = new FileSystem(10);
        String[] dirPath ={"TestFolder"};
        fileSystem.dir(dirPath);

    }
    /**
     * new folder's name already exists in the file system as file.
     * @throws Exception
     */
    @Test (expected = BadFileNameException.class)
    public void dirWithBadSameName() throws Exception{
        FileSystem fileSystem = new FileSystem(10);
        String[] dirPath ={"root", "TestName"};
        fileSystem.file(dirPath, 2);
        fileSystem.dir(dirPath);
    }

    /**
     * new folder's name already exists in the file system as file.
     */
    @Test
    public void dirWithBadSameFolderName(){
        FileSystem fileSystem = new FileSystem(10);
        String[] dirPath ={"root", "TestName"};
        try{
            fileSystem.dir(dirPath);
            fileSystem.dir(dirPath);
        }catch(BadFileNameException e){
            fail("should not have done this according to your code.");
        }
    }

    /**
     * check that the disk() method returns what it should after the before.
     */
    @Test
    public void disk() {
        FileSystem fileSystem = this.fileSystemFilled;
        // checking the first block
        String[][] diskStatus = fileSystem.disk();
        assertEquals("root",diskStatus[0][0]);
        assertEquals("firstFolder",diskStatus[0][1]);
        assertEquals("tamir",diskStatus[0][2]);
        // checking the second block
        assertEquals("root",diskStatus[1][0]);
        assertEquals("firstFolder",diskStatus[1][1]);
        assertEquals("tamir",diskStatus[1][2]);
        // check that the rest are empty.
        assertNull(diskStatus[2]);
        assertNull(diskStatus[3]);
    }

    /**
     * create a file
     */
    @Test
    public void file() {
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] pathWithFile = {"root", "firstFolder", "tamir"};
            fileSystemFilled.file(pathWithFile,2);
        }catch(Exception e){
            fail("something went wrong while creating a new file");
        }
    }

    /**
     * tests file creation of a path which its first entry isn't 'root'
     */
    @Test (expected = BadFileNameException.class)
    public void badFileCreationSameRootNotFirstInPath() throws Exception{
        FileSystem fileSystemFilled = new FileSystem(10);
        fileSystemFilled.dir(this.path);
        String[] pathWithFile = {"firstFolder", "root", "tamir"};
        fileSystemFilled.file(pathWithFile,2);
    }

    /**
     * tests file creation with not enough free space
     */
    @Test (expected = OutOfSpaceException.class)
    public void badFileCreationNotEnoughSpace() throws Exception{
        FileSystem fileSystemFilled = new FileSystem(1);
        fileSystemFilled.dir(this.path);
        String[] pathWithFile = {"root", "firstFolder", "tamir"};
        fileSystemFilled.file(pathWithFile,2);
    }

    /**
     * testing the file() method with inserting two same name files with different sizes.
     * covering the case where removing the old copy will make room for the new one.
     * notice this fails because there is a bug in the code (filesystem line 121 (should be + and not - ).
     */
    @Test (expected = OutOfSpaceException.class)
    public void badFileCreationFileWithSameName() throws Exception{
        FileSystem fileSystemFilled = new FileSystem(10);
        fileSystemFilled.dir(this.path);
        String[] pathWithFile = {"root", "firstFolder", "tamir"};
        fileSystemFilled.file(pathWithFile,2);
        fileSystemFilled.file(pathWithFile,9);
        String[][] currDisk = fileSystemFilled.disk();

        for (int i = 0; i < currDisk.length-1; i++) {
            assertEquals("root",currDisk[i][0]);
            assertEquals("firstFolder",currDisk[i][1]);
            assertEquals("tamir",currDisk[i][2]);
        }
        assertNull(currDisk[currDisk.length-1]);
    }

    /**
     * testing the file() method with inserting a file with the same name as a folder
     */
    @Test (expected = BadFileNameException.class)
    public void badFileCreationFileWithSameNameAsFolder() throws Exception{
        FileSystem fileSystemFilled = new FileSystem(10);
        fileSystemFilled.dir(this.path);
        String[] pathWithFile = {"root", "TestName"};
        fileSystemFilled.dir(pathWithFile);
        fileSystemFilled.file(pathWithFile,2);
    }

    /**
     * testing the file() method with passing a negative 'k' parameter
     * Will fail, as it will throw an exception (NegativeInteger) the method hasn't declared it might
     */
    @Test
    public void badFileCreationFileWithNegativeK(){
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] pathWithFile = {"root", "TestName"};
            fileSystemFilled.dir(pathWithFile);
            fileSystemFilled.file(pathWithFile,-2);
        }catch(Exception e){
            fail("should not throw exception, name and space is valid");
        }
    }

    /**
     * testing the file() method with passing a null 'name' array parameter
     * Will fail, as it will throw an exception (NullPointer) the method hasn't declared it might
     */
    @Test
    public void badFileCreationFileWithNullArray(){
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            fileSystemFilled.file(null,2);
        }catch(Exception e){
            fail("should not throw exception, name and space is valid");
        }
    }

    /**
     * testing the method lsdir()
     * checking a valid example
     */
    @Test
    public void lsdir() {
        String[] pathToCheck = {"root", "firstFolder"};
        String[] names = this.fileSystemFilled.lsdir(pathToCheck);
        String[] actual = {"tamir"};
        for (int i = 0; i < names.length; i++) {
            assertEquals(actual[i], names[i]);
        }
    }


    /**
     * testing the method lsdir()
     * checking with a non existing folder name
     */
    @Test
    public void lsdirNotExistingFolder() {
        String[] pathToCheck = {"root", "NotThere"};
        String[] names = this.fileSystemFilled.lsdir(pathToCheck);
        assertNull(names);
    }

    /**
     * testing the method lsdir()
     * checking with a filename as folder name parameter
     */
    @Test
    public void lsdirFileNameInsteadFolder() {
        String[] pathToCheck = {"root", "firstFolder", "tamir"};
        String[] names = this.fileSystemFilled.lsdir(pathToCheck);
        assertNull(names);
    }
    /**
     * testing the method lsdir()
     * checking with a null as path
     * will fail as it throws NullPointerException the method didn't declare it might
     */
    @Test
    public void lsdirWithNullPath() {
        String[] pathToCheck = null;
        String[] names = this.fileSystemFilled.lsdir(pathToCheck);
        assertNull(names);
    }

    /**
     * testing the method rmfile()
     * removing a valid example
     */
    @Test
    public void rmfile() {
        String[] pathToTamir = {"root", "firstFolder", "tamir"};
        this.fileSystemFilled.rmfile(pathToTamir);
        assertNull(this.fileSystemFilled.FileExists(pathToTamir));
    }
    /**
     * testing the method rmfile()
     * removing a invalid example - non existing file
     */
    @Test
    public void rmfileNotExistingFile() {
        String[] pathToTamir = {"root", "firstFolder", "loTamir"};
        this.fileSystemFilled.rmfile(pathToTamir);
        this.fileSystemFilled.FileExists(pathToTamir);
    }

    /**
     * testing the method rmfile()
     * removing a invalid example - path = null
     * will fail, due to NullPointerException the method didn't declare it might throw
     */
    @Test
    public void rmfileNullPath() {
        String[] pathToTamir = null;
        this.fileSystemFilled.rmfile(pathToTamir);
        this.fileSystemFilled.FileExists(pathToTamir);
    }
    /**
     * testing the method rmdir()
     * removing a valid example
     */
    @Test
    public void rmdir() {
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            fileSystemFilled.rmdir(this.path);
        }catch(Exception e){
            fail("valid case, should not throw exceptions=>"+e.getClass());
        }
    }

    /**
     * testing the method rmdir()
     * removing a invalid example - not existing folder
     */
    @Test
    public void rmdirNotExistingFolder() {
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] path = {"root","NotReallyHere"};
            fileSystemFilled.rmdir(path);
        }catch(Exception e){
            fail("valid case, should not throw exceptions=>"+e.getClass());
        }
    }

    /**
     * testing the method rmdir()
     * removing a invalid example - not empty folder
     */
    @Test (expected = DirectoryNotEmptyException.class)
    public void rmdirNotEmptyFolder() throws Exception{
        FileSystem fileSystemFilled = new FileSystem(10);
        fileSystemFilled.dir(this.path);
        String[] path = {"root","firstFolder", "tamir"};
        fileSystemFilled.file(path, 2);
        fileSystemFilled.rmdir(this.path);
    }
    /**
     * testing the method rmdir()
     * removing a invalid example - path=null
     * will fail, due to NullPointerException the method didn't declare it might throw
     */
    @Test
    public void rmdirPathNull() {
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] path = null;
            fileSystemFilled.rmdir(path);
        }catch(Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }

    /**
     * testing the method FileExist()
     * valid example
     */
    @Test
    public void fileExists() {
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] path = {"root","firstFolder", "tamir"};
            fileSystemFilled.file(path, 2);
            fileSystemFilled.FileExists(path);
        }catch(Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }
    /**
     * testing the method FileExist()
     * invalid example - not existing file
     */
    @Test
    public void fileExistsNotExists() {
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] path = {"root","firstFolder", "tamir"};
            fileSystemFilled.file(path, 2);
            String[] notRealPath = {"root","firstFolder", "NotTamir"};
            assertNull(fileSystemFilled.FileExists(notRealPath));
        }catch(Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }
    /**
     * testing the method FileExist()
     * invalid example - a path to a directory instead of a file
     * will fail, due to ClassCastException the method didn't declare it might throw
     */
    @Test
    public void fileExistsPathToFolder() {
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] path = {"root","firstFolder", "tamir"};
            fileSystemFilled.file(path, 2);
            String[] pathToFolder = {"root","firstFolder"};
            assertNull(fileSystemFilled.FileExists(pathToFolder));
        }catch(Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }

    /**
     * testing the method FileExist()
     * invalid example - path = null
     * will fail, due to NullPointerException the method didn't declare it might throw
     */
    @Test
    public void fileExistsPathNull() {
        try{
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] path = {"root","firstFolder", "tamir"};
            fileSystemFilled.file(path, 2);
            String[] notRealPath = null;
            assertNull(fileSystemFilled.FileExists(notRealPath));
        }catch(Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }

    /**
     * testing the method DirExist()
     * with a valid example
     */
    @Test
    public void dirExists() {
        try {
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            fileSystemFilled.DirExists(this.path);
        }catch (Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }

    /**
     * testing the method DirExist()
     * with a invalid example - a none existing folder
     */
    @Test
    public void dirExistsNotExistingFolder() {
        try {
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] notRealPath = {"root","LoBe'emet"};
            assertNull(fileSystemFilled.DirExists(notRealPath));
        }catch (Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }

    /**
     * testing the method DirExist()
     * with a invalid example - a path ending with a file
     */
    @Test
    public void dirExistsPathEndingWithFile() {
        try {

            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            String[] path = {"root","firstFolder", "tamir"};
            fileSystemFilled.file(path, 2);
            assertNull(fileSystemFilled.DirExists(path));
        }catch (Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }

    /**
     * testing the method DirExist()
     * with a invalid example - path=null
     * will fail, due to NullPointerException the method didn't declare it might throw
     */
    @Test
    public void dirExistsPathNull() {
        try {
            FileSystem fileSystemFilled = new FileSystem(10);
            fileSystemFilled.dir(this.path);
            assertNull(fileSystemFilled.DirExists(null));
        }catch (Exception e){
            fail("shouldn't throw an exception=>"+e.getClass());
        }
    }
}