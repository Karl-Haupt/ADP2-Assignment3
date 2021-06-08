package ac.za.cput.adp3assignment;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Karl H(220236585)
 * SupplierDetails.java -> Reads file from the Stakeholder.ser file and writes only the supplier to supplierOutFile.txt
 */
public class SupplierDetails implements ReadingAndWritingFile {
    private BufferedWriter bwSupplier; //read
    private ObjectInputStream inputStakeholder; //read
    private ArrayList<Supplier> suppliers = new ArrayList<Supplier>();

    @Override
    public void openStakeholderSerFile() {
        try {
            inputStakeholder = new ObjectInputStream( new FileInputStream("Stakeholder.ser") );
            System.out.println("Stakeholder.ser opened & ready to read from");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void openFile() {
        try {
            bwSupplier = new BufferedWriter( new FileWriter("supplierOutFile.txt") );
            System.out.println("supplierOutFile.txt opened and ready for writing");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void readFromStakeholdersFile() {
        try {
            while(true) {
                Object StakeholderType = (Stakeholder) inputStakeholder.readObject();
                if(StakeholderType instanceof Supplier) {
                    suppliers.add((Supplier) StakeholderType);
                }
            }
            
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("ReadFromStakeholder" + e.getMessage());
        } finally {
            closeStakeholderSerFile();
            System.out.println("Stakeholder Closed");
        }
        
    }

    @Override
    public void writeHeadingToFile() {
        try{
            bwSupplier.write("======================== SUPPLIERS ==============================\n" +
                    "ID   Name                	        Prod Type	Description    \n" +
                    "================================================================== \n");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeToOutFile() {
        suppliers.forEach(supplier -> {
            try {
            bwSupplier.write(String.format("%s   %s                	%s		%s    \n", 
                            supplier.getStHolderId(), 
                            supplier.getName(), 
                            supplier.getProductType(), 
                            supplier.getProductDescription()) );  
            }catch(IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }
    
    public void sortSupplierArray() {
        suppliers.sort((supplier, nextSupplier) -> supplier.getName().compareTo(nextSupplier.getName()));
    }

    @Override
    public void closeFile() {
        try {
            bwSupplier.close();
            System.out.println("SupplierFile Closed");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void closeStakeholderSerFile() {
        try {
            inputStakeholder.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
