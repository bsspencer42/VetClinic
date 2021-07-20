import java.io.File;
import java.io.FileNotFoundException;
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
    public String nextDay(String filename) throws FileNotFoundException {
        return nextDay(new File(filename));
    }

    public String nextDay(File file) throws FileNotFoundException {
        //Setup file scanning object
        Scanner fileScanner = null;
        System.out.println(System.getProperty("user.dir"));
        fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            System.out.println(fileScanner.nextLine());
        }
        return "done";
    }

    public static void main(String[] args) throws FileNotFoundException {
        Clinic c = new Clinic("Patients.csv");
        c.nextDay("Appointments.csv");
    }

}
