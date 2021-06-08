package ac.za.cput.adp3assignment;

/**
 *
 * @author Karl H(220236585)
 * RunManagementSystem.java -> Runs the program to read and write to specific files
 */
public class RunManagementSystem {
    public static void main(String[] args) {
        //Writng to the customerOutFile.txt
        CustomerDetails cD = new CustomerDetails();
        
        cD.openStakeholderSerFile();
        cD.openFile();
        
        cD.readFromStakeholdersFile();
        
        cD.sortCustomerArray();
        
        cD.writeHeadingToFile();
        cD.writeToOutFile();
        cD.writeCustomerCarRents();
        
        cD.closeFile();
        
        //Writing to the supplierOutFile.txt
        SupplierDetails sD = new SupplierDetails();
        
        sD.openStakeholderSerFile();
        sD.openFile();
        
        sD.readFromStakeholdersFile();
        
        sD.sortSupplierArray();
        
        sD.writeHeadingToFile();
        sD.writeToOutFile();
        
        sD.closeFile();
        
    }
}
