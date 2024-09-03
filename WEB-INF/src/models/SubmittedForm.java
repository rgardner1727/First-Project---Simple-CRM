package models;

import java.io.Serializable;
import java.time.Instant;

public class SubmittedForm implements Serializable{

    private static final long serialVersionUID = 2L;

    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String lifeInsuranceQuestion1;
    private String lifeInsuranceQuestion2;
    private String lifeInsuranceQuestion3;
    private Instant dateSubmitted;

    public SubmittedForm(String emailAddress, String firstName, String lastName, String phoneNumber,
                         String lifeInsuranceQuestion1, String lifeInsuranceQuestion2, String lifeInsuranceQuestion3){
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.lifeInsuranceQuestion1 = lifeInsuranceQuestion1;
        this.lifeInsuranceQuestion2 = lifeInsuranceQuestion2;
        this.lifeInsuranceQuestion3 = lifeInsuranceQuestion3;
        this.dateSubmitted = Instant.ofEpochMilli(System.currentTimeMillis());
    }

    public String getEmailAddress(){return this.emailAddress;}

    public String getFirstName(){return this.firstName;}

    public String getLastName(){return this.lastName;}

    public String getPhoneNumber(){return this.phoneNumber;}

    public String getLifeInsuranceQuestion1(){return this.lifeInsuranceQuestion1;}

    public String getLifeInsuranceQuestion2(){return this.lifeInsuranceQuestion2;}

    public String getLifeInsuranceQuestion3(){return this.lifeInsuranceQuestion3;}

    public Instant getDateSubmitted(){return this.dateSubmitted;}

    @Override
    public String toString(){
        return String.format("Email Address: %s\n" +
                "First Name: %s\n" +
                "Last Name: %s\n" +
                "Phone Number: %s\n" +
                "Life Insurance Question 1: %s\n" +
                "Life Insurance Question 2: %s\n" +
                "Life Insurance Question 3: %s\n" +
                "Time Submitted: %s", this.emailAddress, this.firstName, this.lastName,
                this.phoneNumber, this.lifeInsuranceQuestion1, this.lifeInsuranceQuestion2, this.lifeInsuranceQuestion3, this.dateSubmitted);
    }
}
