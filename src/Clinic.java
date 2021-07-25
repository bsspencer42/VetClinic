import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Clinic {
    // Instance variables
    private File patientFile;
    private int day = 1;

    // Constructors
    public Clinic(String filename) {
        this(new File(filename));
    }

    public Clinic(File file) {
        this.patientFile = file;
    }

    // Methods

    // Get output of day results
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

            // Increment day
            this.day = day+1;

            // Create local variables for parsed line
            String name = nextAppt[0];
            String petType = nextAppt[1];
            double droolMice = Double.parseDouble(nextAppt[2]);
            String miceDrool; // Used to store int/double for output as string
            String timeIn = nextAppt[3];

            // Health Check
            System.out.println("Consultation for " + name + " the " + petType + " at " + timeIn + ".");
            System.out.println("What is the health of " + name + "?");
            if (!petType.equals("Dog") && !petType.equals("Cat")) {
                throw new InvalidPetException();
            }
            double InitialHealth = getData(0,1);

            // Pain check
            System.out.println("On a scale of 1 to 10, how much pain is " + name + " in right now?");
            int InitialPainLevel = (int) getData(1,10);

            // Create pet object
            Pet currentPet;  // Create w/ declared type as parent for case of either cat or dog
            if (petType.equals("Dog")){
                currentPet = new Dog(name,InitialHealth,InitialPainLevel,droolMice);
                miceDrool = String.valueOf(droolMice);
            }
            else {
                currentPet = new Cat(name,InitialHealth,InitialPainLevel,(int) droolMice);
                miceDrool = String.valueOf(((int) droolMice));
            }
            InitialHealth = currentPet.getHealth();

            // Treat the pet
            currentPet.speak();
            int timeToHeal = currentPet.treat();

            // Calculate time out
            String timeOut = addTime(timeIn,timeToHeal);

            // String to output
            outPutString += String.join(",",name, petType,String.valueOf(miceDrool), "Day " + String.valueOf(day),timeIn,
                    timeOut,String.valueOf(InitialHealth),String.valueOf(InitialPainLevel)) + "\n";
        }
        return outPutString;
    }

    // Add patient info to file
    public boolean addToFile(String patientInfo) {
        // Parse input
        String [] patientParsed = patientInfo.trim().split(",");
        String name = patientParsed[0];

        Scanner fileScanner = null;
        PrintWriter filePrint = null;

        try {
            // Setup scanner object
            fileScanner = new Scanner(patientFile);
            String tempFileContents = "";

            // First read all data to String array
            while (fileScanner.hasNextLine()){
                tempFileContents += fileScanner.nextLine()+ "\n";
            }
            fileScanner.close();

            // Create printwriter object to modify file as necessary
            filePrint = new PrintWriter(patientFile);

            String[] tempFileArray = tempFileContents.split("\n");

            // Loop through contents and add data
            int lineCount = 0; // counter
            boolean success = false; // flag for name exists
            String[] currentLine;
            for (String line : tempFileArray){
                lineCount += 1;
                if (line.contains(name)){
                    success = true;
                    String[] lineParse = line.split(",");
                    String day = patientParsed[3]; String timeIn = patientParsed[4]; String timeOut = patientParsed[5];
                    String health = patientParsed[6]; String pain = patientParsed[7];
                    filePrint.println(String.join(",",line,day,timeIn,timeOut,health,pain));
                }
                else if (!line.equals("")) {
                    filePrint.println(line);
                }
            }
            // If patient name does not exist in record, append to end
            if (!success){
                filePrint.println(String.join(",",patientInfo));
            }
        }
        catch (Exception e){
            e.getMessage();
            return false;
        }
        finally {
            if (fileScanner != null){
                fileScanner.close();
            }
            if (filePrint != null){
                filePrint.close();
            }
        }
        return true;
    }

    // Helper method - User input prompt
    private double getData(double minVal, double maxVal){
        boolean success = false;
        double InitialVal = 0;
        Scanner userInput = new Scanner(System.in);
        while (!success) {
            try {
                InitialVal = userInput.nextDouble();
                userInput.nextLine();

                if (InitialVal < minVal || InitialVal > maxVal) {
                    System.out.println("Please enter a number");
                }
                else {
                    success = true;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Please enter a number");
                userInput.nextLine();
            }
        }
        return InitialVal;
    }

    // Helper method for time out calc
    private String addTime(String timeIn, int treatmentTime){
        // Handle minutes
        int sumMins = (Integer.parseInt(timeIn.substring(timeIn.length()-2)) + treatmentTime);  // Get total num mins
        String mins = String.valueOf((sumMins % 60)); // Leftover mins
        int hrs = sumMins / 60; // Leftover hours

        // Add mins trailing zeros
        if (mins.length() == 1) {
            mins = "0" + mins;
        }

        // Calculate timeOut
        String timeOut = String.valueOf(Integer.parseInt(timeIn.substring(0,timeIn.length()-2)) + hrs) + mins;

        // Add trailing zeros
        for (int i = 0; i < 4 - timeOut.length();i++)
        {
            timeOut = "0" + timeOut;
        }
        return timeOut;
    }

}
