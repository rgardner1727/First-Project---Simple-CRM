package database_navigator;

import models.SubmittedForm;
import controllers.SubmittedFormController;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DatabaseNavigator {
    private SubmittedFormController submittedFormController;
    private List<SubmittedForm> submittedForms;
    private final Scanner scanner;
    private boolean isRunning;
    private boolean isFirstRun;


    //adding this comment to test changing files using git
    public DatabaseNavigator(){
        try{
            this.submittedFormController = this.createSubmittedFormController();
            this.submittedForms = submittedFormController.getSubmittedForms();
        }catch(IOException e){
            System.out.println("Failed to read in submitted forms from the database. Exiting the application.");
            System.exit(0);
        }catch(ClassNotFoundException e){
            System.out.println("Could not find the class associated with the submitted forms. Exiting the application.");
            System.exit(0);
        }
        this.scanner = new Scanner(System.in);
        this.isRunning = false;
        this.isFirstRun = true;
    }

    private enum ConfirmationMessage{
        EXIT_NAVIGATOR("Are you sure you would like to exit the navigator application? Enter 0 to EXIT or 1 to CONTINUE"),
        EXIT_PRINT_ALL("Would you like to exit the Print All function? Enter 0 to EXIT or 1 to CONTINUE"),
        EXIT_SEARCH_EMAIL_ADDRESS("Would you like to exit the Search by Email Address function? Enter 0 to EXIT or 1 to CONTINUE"),
        EXIT_SEARCH_FIRST_NAME("Would you like to exit the Search by First Name function? Enter 0 to EXIT or 1 to CONTINUE"),
        EXIT_SEARCH_LAST_NAME("Would you like to exit the Search by Last Name function? Enter 0 to EXIT or 1 to CONTINUE"),
        EXIT_SEARCH_PHONE_NUMBER("Would you like to exit the Search by Phone Number function? Enter 0 to EXIT or 1 to CONTINUE");
        public final String message;

        ConfirmationMessage(String message){
            this.message = message;
        }
        public String getMessage(){
            return this.message;
        }
    }

    private SubmittedFormController createSubmittedFormController() throws IOException, ClassNotFoundException {
        return new SubmittedFormController(SubmittedFormController.readSubmittedFormsFromDatabase());
    }

    public void navigationStartup(){
        this.isRunning = true;
        while(isRunning) {
            int response;
            if (this.isFirstRun) {
                System.out.println("Welcome to the Database Navigator");
                System.out.println("----------------------------------");
            }
            this.waitToDisplay();
            System.out.println("""
                    Enter any of the following numbers to perform the desired action:
                    0. Exit the navigator application
                    1. Print out all submitted form entries
                    2. Search for submitted forms by email address
                    3. Search for submitted forms by first name
                    4. Search for submitted forms by last name
                    5. Search for submitted forms by phone number""");
            response = this.scanner.nextInt();
            this.scanner.nextLine();
            switch (response) {
                case (0):
                    exitNavigator();
                    break;
                case (1):
                    printAllSubmittedForms();
                    break;
                case (2):
                    searchForSubmittedFormsByEmailAddress();
                    break;
                case (3):
                    searchForSubmittedFormByFirstName();
                    break;
                case (4):
                    searchForSubmittedFormsByLastName();
                    break;
                case (5):
                    searchForSubmittedFormsByPhoneNumber();
                    break;
                default:
                    System.out.println("Please enter a valid option.");
            }
            this.isFirstRun = false;
        }
    }

    private void exitNavigator(){
        int response;
        response = this.getValidInput(ConfirmationMessage.EXIT_NAVIGATOR);
            if(response == 0)
                System.exit(0);
    }

    private void printAllSubmittedForms(){
        int response;
        while(true) {
            System.out.println("Printing out all submitted forms:");
            System.out.println("---------------------------------");
            this.waitToDisplay();
            this.submittedForms.forEach(System.out::println);
            response = this.getValidInput(ConfirmationMessage.EXIT_PRINT_ALL);
            if(response == 0)
                return;
        }
    }

    private void searchForSubmittedFormsByEmailAddress(){
        int response;
        while(true){
            System.out.println("Enter the email address to search for: ");
            final String searchedEmailAddress = this.scanner.nextLine();
            System.out.println("Printing out all submitted forms that match the entered email address:");
            System.out.println("----------------------------------------------------------------------");
            this.waitToDisplay();
            this.submittedForms.stream().filter(submittedForm -> submittedForm.getEmailAddress().contains(searchedEmailAddress)).forEach(System.out::println);
            response = this.getValidInput(ConfirmationMessage.EXIT_SEARCH_EMAIL_ADDRESS);
            if(response == 0)
                return;
        }
    }

    private void searchForSubmittedFormByFirstName(){
        int response;
        while(true){
            System.out.println("Enter the first name to search for:");
            final String searchedFirstName = this.scanner.nextLine();
            System.out.println("Printing out all submitted forms that match the entered first name:");
            System.out.println("-------------------------------------------------------------------");
            this.waitToDisplay();
            this.submittedForms.stream().filter(submittedForm -> submittedForm.getFirstName().contains(searchedFirstName)).forEach(System.out::println);
            response = this.getValidInput(ConfirmationMessage.EXIT_SEARCH_FIRST_NAME);
            if(response == 0)
                return;
        }
    }

    private void searchForSubmittedFormsByLastName(){
        int response;
        while(true){
            System.out.println("Enter the last name to search for: ");
            final String searchedLastName = this.scanner.nextLine();
            System.out.println("Printing out all submitted forms that match the entered last name:");
            System.out.println("------------------------------------------------------------------");
            this.waitToDisplay();
            this.submittedForms.stream().filter(submittedForm -> submittedForm.getLastName().contains(searchedLastName)).forEach(System.out::println);
            response = this.getValidInput(ConfirmationMessage.EXIT_SEARCH_LAST_NAME);
            if(response == 0)
                return;
        }
    }

    private void searchForSubmittedFormsByPhoneNumber(){
        int response;
        while(true){
            System.out.println("Enter the phone number to search for with dashes and no spaces: ");
            final String searchedPhoneNumber = this.scanner.nextLine();
            System.out.println("Printing out all submitted forms that match the entered phone number:");
            System.out.println("---------------------------------------------------------------------");
            this.waitToDisplay();
            this.submittedForms.stream().filter(submittedForm -> submittedForm.getPhoneNumber().contains(searchedPhoneNumber)).forEach(System.out::println);
            response = this.getValidInput(ConfirmationMessage.EXIT_SEARCH_PHONE_NUMBER);
            if(response == 0)
                return;
        }
    }

    private int getValidInput(ConfirmationMessage confirmationMessage){
        int response;
        while(true) {
            try {
                System.out.println(confirmationMessage.getMessage());
                response = this.scanner.nextInt();
                this.scanner.nextLine();
                if(response == 0)
                    return response;
                else if(response == 1)
                    return response;
                else
                    System.out.println("Please enter a provided option when prompted.");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number when prompted.");
                this.scanner.nextLine();
            }
        }
    }

    private void waitToDisplay(){
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e){
            System.out.println("The thread was interrupted.");
        }
    }
}
