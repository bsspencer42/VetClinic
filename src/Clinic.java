import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class Clinic {
    // Instance variables
    File patientFile;
    int day;

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
        Scanner fileScanner = null;
        Scanner userInput = new Scanner(System.in);
        fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            // Parse next appointment into string array
            String[] nextAppt = fileScanner.nextLine().trim().split(",");
            String name = nextAppt[0];
            String petType = nextAppt[1];
            String droolMice = nextAppt[2];
            String apptTime = nextAppt[3];
            double health = 0;
            double painLevel = 0;
            boolean success = false;
            // Health Check
            System.out.println("Consultation for " + name + " the " + petType + " at " + apptTime + ".");
            System.out.println("What is the health of " + name + "?");
            if (!petType.equals("Dog") && !petType.equals("Cat")) {
                throw new InvalidPetException();
            } // Check if pet type valid
            while (!success) {
                try {
                    health = userInput.nextDouble();
                    userInput.nextLine();
                    success = true;
                }
                catch (InputMismatchException e) {
                    userInput.nextLine();
                }
            }
            // Pain check
            System.out.println("On a scale of 1 to 10, how much pain is " + name + " in right now?");
            success = false;
            while (!success) {
                try {
                    painLevel = userInput.nextDouble();
                    userInput.nextLine();
                    success = true;
                }
                catch (InputMismatchException e) {
                    userInput.nextLine();
                }
            }
            // Speak
            if (petType.equals("Dog")){
                Dog currentPet = new Dog(name,health,(int) painLevel,droolMice);
                currentPet.speak();
                currentPet.treat();
            }
            else {
                Cat currentPet = new Cat(name, health, (int) painLevel,droolMice);
                currentPet.speak();
                currentPet.treat();
            }

        }
        return "done";
    }

    public static void main(String[] args) throws FileNotFoundException,InvalidPetException {
        Clinic c = new Clinic("Patients.csv");
        c.nextDay("Appointments.csv");
    }

}
