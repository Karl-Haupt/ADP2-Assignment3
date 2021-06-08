package ac.za.cput.adp3assignment;


/**
 *
 * @author Karl H (220236585)
 * ReadingAndWrinting.java -> Implementation details for classes that need to read and write to files
 */
public interface ReadingAndWritingFile {    
    
    public abstract void openStakeholderSerFile();

    public abstract void openFile();
    
    public abstract void readFromStakeholdersFile();
    
    public abstract void writeHeadingToFile();
    
    public abstract void writeToOutFile();
    
    public abstract void closeFile();
    
    public abstract void closeStakeholderSerFile();
}
