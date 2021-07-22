import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class Clinic {
    // Instance variables
    File patientFile;
    int day = 1;

    // Constructors
    public Clinic(String filename) {
        this(new File(filename));
    }

    public Clinic(File file) {
        this.patientFile = file;
    }

    // Methods
    public String nextDay(String filename) throws FileNotFoundException,InvalidPetException {
        return nextDay(new File(filename));
    }

    public String nextDay(File file) throws FileNotFoundException,InvalidPetException {
        //Setup file scanning object
        Scanner fileScanner = new Scanner(file);

        // Local vars
        String outPutString = "";

        // While loop to loop through each line in Appointments
        while (fileScanner.hasNextLine()) {
            // Parse next appointment into string array
            String[] nextAppt = fileScanner.nextLine().trim().split(",");

            // Create local variables for parsed line
            String name = nextAppt[0];
            String petType = nextAppt[1];
            double droolMice = Double.parseDouble(nextAppt[2]);
            String apptTime = nextAppt[3];

            // Health Check
            System.out.println("Consultation for " + name + " the " + petType + " at " + apptTime + ".");
            System.out.println("What is the health of " + name + "?");
            if (!petType.equals("Dog") && !petType.equals("Cat")) {
                throw new InvalidPetException();
            } // Check if pet type valid
            double InitialHealth = getData();

            // Pain check
            System.out.println("On a scale of 1 to 10, how much pain is " + name + " in right now?");
            int InitialPainLevel = (int) getData();

            // Create pet object
            Pet currentPet;  // Create w/ declared type as parent for case of either cat or dog
           if (petType.equals("Dog")){
               currentPet = new Dog(name,InitialHealth,InitialPainLevel,droolMice);
           }
           else {
               currentPet = new Cat(name,InitialHealth,InitialPainLevel,(int) droolMice);
           }
            System.out.println(currentPet.getHealth());

           // Speak and heal
           currentPet.speak();
           int timeToHeal = currentPet.treat();
           currentPet.heal();

            System.out.println(timeToHeal);
           // String to output
           outPutString += String.join(",",name, petType,String.valueOf(droolMice), "Day " + String.valueOf(day),apptTime,
                   String.valueOf(Integer.parseInt(apptTime)+ timeToHeal),String.valueOf(InitialHealth),String.valueOf(InitialPainLevel));
            System.out.println(outPutString);
        }
        return "done";
    }

    // Helper method - User input prompt
    private double getData(){
        boolean success = false;
        double InitialVal = 0;
        Scanner userInput = new Scanner(System.in);
        while (!success) {
            try {
                InitialVal = userInput.nextDouble();
                userInput.nextLine();
                success = true;
            }
            catch (InputMismatchException e) {
                userInput.nextLine();
            }
        }
        return InitialVal;
    }

    public static void main(String[] args) throws FileNotFoundException,InvalidPetException {
        Clinic c = new Clinic("Patients.csv");
        c.nextDay("Appointments.csv");
    }

}
