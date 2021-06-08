package ac.za.cput.adp3assignment;

import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
/**
 *
 * @author Karl H(220236585)
 * CustomerDetails.java -> Reads file from the Stakeholder.ser file and writes only the customer to customerOutFile.txt
 */
public class CustomerDetails implements ReadingAndWritingFile {
    private BufferedWriter bwCustomer;
    private ObjectInputStream inputStakeholder; //read
    private ArrayList<Customer> customers = new ArrayList<Customer>(); 
    private int totalCanRent = 0;

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
            bwCustomer = new BufferedWriter( new FileWriter("customerOutFile.txt") );
            System.out.println("customerOutFile.txt opened and ready for writing");
        } catch(IOException e) {
            System.out.println("Error from me no file" + e.getMessage());
        }
    }

    @Override
    public void readFromStakeholdersFile() {
        try {
            while(true) {
                Object StakeholderType = (Stakeholder) inputStakeholder.readObject();
                if(StakeholderType instanceof Customer) {
                    customers.add((Customer) StakeholderType);
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
        try {
            bwCustomer.write("================== CUSTOMERS =============================\n" +
                    "ID   	Name      	Surname   	Date of birth  	Age  \n" +
                    "===========================================================\n");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void writeToOutFile() {
            customers.forEach(customer -> {
                try {
                    bwCustomer.write(String.format("%s   	%s       	%s   	%s   	%s  \n", 
                                customer.getStHolderId(), 
                                customer.getFirstName(), 
                                customer.getSurName(), 
                                reformatDate(customer.getDateOfBirth()), 
                                stakeholderAge(customer.getDateOfBirth())));
                    
                } catch(IOException | ParseException e) {
                    System.out.println(e.getMessage());
                }
            });
    }
    
    public void writeCustomerCarRents() {
        try {
            bwCustomer.write(String.format("\nNumber of customers who can rent:  %d \nNumber of customers who cannot rent:  %d",
            numberOfCustomerCanRent(), 
            numberOfCustomerCannotRent()) );
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void sortCustomerArray() {
        customers.sort((customer, nextcustomer) -> customer.getStHolderId().compareTo(nextcustomer.getStHolderId()));
    }

    @Override
    public void closeFile() {
        try {
            bwCustomer.close();
            System.out.println("CustomerFile Closed");
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
    
    public int stakeholderAge(String stakeholderDOB) throws ParseException {  
        SimpleDateFormat sDFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sDFormat.parse(stakeholderDOB);
        
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        
        LocalDate presentTime = LocalDate.of(year, month, date);
        int age = Period.between(presentTime, LocalDate.now()).getYears();

        return age;
    }
    
    public String reformatDate(String date) {
        String reformattedDate = "";
        try {
            Date dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            SimpleDateFormat sDFormat = new SimpleDateFormat("dd MMM yyyy");
            reformattedDate = sDFormat.format(dateFormat);
        } catch(ParseException e) {
            System.out.println(e.getMessage());
        }
        return reformattedDate;
    }
    
    public int numberOfCustomerCanRent() {
        for(int i = 0; i < customers.size(); i++) {
            if(customers.get(i).getCanRent()) {
                totalCanRent++;
            }
        }
        
        return totalCanRent;
    }

    public int numberOfCustomerCannotRent() {
        return customers.size() - totalCanRent;
    }
    
}
