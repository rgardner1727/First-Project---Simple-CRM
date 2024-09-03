package controllers;

import models.SubmittedForm;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;

/*class used to interact with SubmittedForm objects and the .txt database file*/

public class SubmittedFormController implements Serializable{

    //string representing the location of the database file on my local machine
    private static final String DB_URL = "C:\\Users\\rgard\\apache-tomcat-9.0.91\\webapps\\crm-project\\WEB-INF\\src\\resources\\crm_database.txt";
    private static final long serialVersionUID = 1L;

    //list that will hold all the forms that have been submitted and read from the database
    private List<SubmittedForm> submittedForms;


    //SubmittedFormController constructor that initializes the submittedForms List as an empty ArrayList
    //used when no list of SubmittedForm objects has been stored yet
    public SubmittedFormController(){
        this.submittedForms = new ArrayList<>();
    }

    //SubmittedFormController constructor that takes a List<SubmittedForm> as an argument and initializes the calling object's submittedForms field to the passed in list
    //used to add a SubmittedForm object to the already existing list of SubmittedForm objects
    public SubmittedFormController(List<SubmittedForm> submittedFormsFromFile){
        this.submittedForms = submittedFormsFromFile;
    }

    //returns the SubmittedFormController's submittedForms ArrayList field
    public List<SubmittedForm> getSubmittedForms(){
        return this.submittedForms;
    }

    //method that adds a SubmittedForm object to the object's submittedForms ArrayList field
    //accepts parameters representing all the requested information on the website form
    //checks to ensure all the form fields have been filled by checking if the response is null or an empty String; throws an InvalidParameter exception if so
    //finally it attempts to rewrite the List of SubmittedForms to the database
    public void addSubmittedForm(String emailAddress, String firstName, String lastName, String phoneNumber,
                                 String lifeInsuranceQuestion1, String lifeInsuranceQuestion2, String lifeInsuranceQuestion3){

        SubmittedForm submittedForm = new SubmittedForm(emailAddress, firstName, lastName, phoneNumber,
                lifeInsuranceQuestion1, lifeInsuranceQuestion2, lifeInsuranceQuestion3);
        this.submittedForms.add(submittedForm);

        try {
            this.writeSubmittedFormsToDatabase();
        }catch(IOException e){
            System.out.println("Failed to write submitted forms to the database.");
        }
    }

    //writes the List of SubmittedForm objects to the database
    //writes the whole List object to the file
    public void writeSubmittedFormsToDatabase() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(DB_URL);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.submittedForms);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    //reads the List of SubmittedForm objects from the database and returns the cast object
    public static List<SubmittedForm> readSubmittedFormsFromDatabase() throws IOException, ClassNotFoundException{
        FileInputStream fileInputStream = new FileInputStream(DB_URL);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (List<SubmittedForm>) objectInputStream.readObject();
    }
}
