package westminsterskinconsultation;

import GUI.MainFrame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class WestminsterSkinConsultation {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        run(manager);
    }

    private static void run(WestminsterSkinConsultationManager manager){

        boolean exit = false;

        //Load exiting data if exists before program starts
        loadFromFile(manager);
        //print console menu for the first time
        printMenu();

        while(!exit){
            String select = getInput("Enter your selection. To print the menu once more, type [104 or 'PMA'] : ");

            switch (select) {
                case "100" -> addDoctor(manager);
                case "101" -> deleteADoctor(manager);
                case "102" -> manager.displayListOfDoctors();
                case "103" -> storeIntoFile(manager);
                case "104" -> printMenu();
                case "105" -> launchGUI(manager);
                case "999" -> exit = true;
                default -> System.out.println("Invalid Input !!");
            }
        }

    }

    private static void printMenu(){
        System.out.println("-".repeat(25)+ "<".repeat(25));
        System.out.println("-".repeat(6) + "Welcome to skin consultation manager" + "<".repeat(7));
        System.out.println("-".repeat(25)+ "<".repeat(25) + "\n");

        System.out.println("""
                    100  : Add a new doctor.
                    101  : Delete a doctor.
                    102  : Print the list of the doctors.                 
                    103  : Store Program Data into file.               
                    104  : Print Console Menu Again.
                    105  : Launch graphical user interface.                    
                    999  : Exit the Program.
                    """);
    }

    private static void addDoctor(WestminsterSkinConsultationManager manager){
        String addAnother;
        do{
            String name = getInput("Enter first name : ");
            String surName = getInput("Enter sur name : ");
            String specialisation = getInput("Enter specialisation : ");
            String mobileNumber = getInput("Enter mobile number : ");
            String medicalLicenceNumber = getInput("Enter medical licence number : ");

            Date dateOfBirth;
            while (true){
                try {
                    String dateOfBirthS = getInput("Enter date of birth [yyyy-mm-dd] : ");
                    String [] birthDate = dateOfBirthS.split("\\.");
                    dateOfBirth = new Date(Integer.parseInt(birthDate[0]), Integer.parseInt(birthDate[1]), Integer.parseInt(birthDate[2]));
                    break;
                } catch (NumberFormatException e){
                    System.out.println("Invalid date!!!");
                }
            }

            manager.addDoctor(new Doctor(medicalLicenceNumber,specialisation,name,surName,dateOfBirth,mobileNumber));

            System.out.println("Would you like to add another physician? ? [Y/N]");
            addAnother = scanner.nextLine();

        }while(!addAnother.equalsIgnoreCase("n"));
    }

    //method to get user inputs
    private static String getInput(String text){
        String input;
        do{
            System.out.print(text);
            input = scanner.nextLine();
        }
        while(input.isBlank());

        return input;
    }

    private static void deleteADoctor(WestminsterSkinConsultationManager manager){


        String medicalLicenseNumber = getInput("Using a doctor's medical license number, remove them from the database. ");

        manager.deleteDoctor(medicalLicenseNumber);
    }

    private static void storeIntoFile(WestminsterSkinConsultationManager manager){
        manager.saveInFile();
    }

    private static void loadFromFile(WestminsterSkinConsultationManager manager){
        manager.loadFromFile();
    }

    private static void launchGUI(WestminsterSkinConsultationManager manager){
        new MainFrame(manager);
    }
}
