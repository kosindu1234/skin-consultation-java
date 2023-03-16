package westminsterskinconsultation;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    private final ArrayList<Doctor> kosindu = new ArrayList<>(10);


    @Override
    public void addDoctor(Doctor doctor) {

        if( (!checkForDuplicates(doctor)) && (kosindu.size() < 10) ){
            kosindu.add(doctor);
            System.out.println("Dr. " + doctor.getName() + " "+"added to the system.");
            return;
        }

        System.out.println("Dr. " + doctor.getName() + " Exited from the system.");
    }

    @Override
    public void deleteDoctor(String medicalLicenceNumber) {
        if(kosindu.isEmpty()){
            System.out.println("Doctor list is already empty");
            return;
        }
        int index = searchDoctor(medicalLicenceNumber);//search the doctor want to remove and get his index no

        if(index > 0){
            Doctor temp = kosindu.remove(index);
            System.out.println("\nDr. " + temp.getName()+"successfully removed from the system." +
                    "\nEnter medical licence number : " + temp.getMedicalLicenceNumber() +
                    "\n" + kosindu.size() + " doctors remaining\n");
            return;
        }
        System.out.println("\nInvalid licence number or No results found.\n");
    }

    @Override
    public void displayListOfDoctors() {
        Collections.sort(kosindu);
        ListIterator<Doctor> iterator = kosindu.listIterator();

        if(kosindu.isEmpty()){
            System.out.println("No Doctors found in the system.");
        }else{
            while(iterator.hasNext()){
                System.out.println(iterator.next().toString() + "\n" + "*".repeat(50));
            }
        }


    }

    @Override
    public void saveInFile() {
        try  {
            FileOutputStream fos = new FileOutputStream("doctors.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Doctor doctor : kosindu){
                oos.writeObject(doctor);
            }
            oos.close();
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
        System.out.println("System data stored successfully\n");
    }

    @Override
    public void loadFromFile() {
        try  {
            FileInputStream fos = new FileInputStream("doctors.txt");
            ObjectInputStream oos = new ObjectInputStream(fos);
            boolean eof = false;//to determine if rea
            while (!eof){
                try {
                    Doctor tempDoctor = (Doctor) oos.readObject();
                    kosindu.add(tempDoctor);
                }catch (EOFException e){
                    eof = true;
                }
            }
            System.out.println("File data loaded successfully\n");
            oos.close();
        } catch (ClassNotFoundException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        }catch (IOException e){
            System.out.println("IOException " + e.getMessage());
        }
    }

    //sort doctors


    //returns true if there are doctor in the list with same licence number or mobile number
    private boolean checkForDuplicates(Doctor doctor){

        for(Doctor d : kosindu){
            if( (d.getMedicalLicenceNumber().equals(doctor.getMedicalLicenceNumber())) ||
                    (d.getMobileNumber().equals(doctor.getMobileNumber())) ){
                return true;
            }
        }

        return false;
    }

    //returns the index of the found doctor or return -1 if failed to found
    public int searchDoctor(String medicalLicenceNumber){
        for(Doctor d : kosindu){
            if(d.getMedicalLicenceNumber().equals(medicalLicenceNumber)){
                return kosindu.indexOf(d);
            }
        }

        return -1;
    }

    //method to create a table model to draw a table
    public DefaultTableModel getDoctorList(){

        Object [][] tableData = new Object[kosindu.size()][4];

        for(int i = 0; i < kosindu.size(); i++){
            tableData [i][0] = kosindu.get(i).getName() + " " + kosindu.get(i).getSurName();
            tableData [i][1] = kosindu.get(i).getSpecialisation();
        }
        Object [] columns = new Object[]{"Doctors Name", "Specialisation"};
        //add columns and table data to a table model and return it
        return new DefaultTableModel(tableData,columns);
    }
    //method to return doctors to display in comboBox/dropDown
    public String [] getComboList(){
        String [] comboList = new String[kosindu.size()];
        for(int i = 0; i < kosindu.size(); i++){
            comboList[i] = kosindu.get(i).getName() + "-" + kosindu.get(i).getMedicalLicenceNumber();
        }
        return comboList;
    }

    //method to return a doctor at specified index
    public Doctor getDoctorByIndex(int index){
        return this.kosindu.get(index);
    }

    //method to return full list of doctors
    public ArrayList<Doctor> getDoctors() {
        return kosindu;
    }
}

