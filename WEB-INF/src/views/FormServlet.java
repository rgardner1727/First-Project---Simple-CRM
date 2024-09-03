package views;

import controllers.SubmittedFormController;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.InvalidParameterException;

public class FormServlet extends HttpServlet{

    private static final long serialVersionUID = 100L;
    private static SubmittedFormController submittedFormController;

    @Override
    public void init(){
        try {
            submittedFormController = new SubmittedFormController(SubmittedFormController.readSubmittedFormsFromDatabase());
        }catch(IOException e){
            System.out.println("Failed to read in submitted forms from the database.");
            submittedFormController = new SubmittedFormController();
        }catch(ClassNotFoundException e){
            System.out.println("Failed to find the class associated with the submitted forms.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/crm-project/form.html");
        //redirects the user to the form when they request the resource from the server.
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            String emailAddress = getValidFormInput(request, ParameterKeys.EMAIL_PARAMETER.getParameterKey(), InvalidFormInputMessages.INVALID_EMAIL_ADDRESS);
            String firstName = getValidFormInput(request, ParameterKeys.FIRST_NAME_PARAMETER.getParameterKey(), InvalidFormInputMessages.INVALID_FIRST_NAME);
            String lastName = getValidFormInput(request, ParameterKeys.LAST_NAME_PARAMETER.getParameterKey(), InvalidFormInputMessages.INVALID_LAST_NAME);
            String phoneNumber = getValidFormInput(request, ParameterKeys.PHONE_PARAMETER.getParameterKey(), InvalidFormInputMessages.INVALID_PHONE_NUMBER);
            String lifeInsuranceQuestion1 = getValidFormInput(request, ParameterKeys.QUESTION_1_PARAMETER.getParameterKey(), InvalidFormInputMessages.INVALID_QUESTION_1);
            String lifeInsuranceQuestion2 = getValidFormInput(request, ParameterKeys.QUESTION_2_PARAMETER.getParameterKey(), InvalidFormInputMessages.INVALID_QUESTION_2);
            String lifeInsuranceQuestion3 = getValidFormInput(request, ParameterKeys.QUESTION_3_PARAMETER.getParameterKey(), InvalidFormInputMessages.INVALID_QUESTION_3);

            submittedFormController.addSubmittedForm(emailAddress, firstName, lastName, phoneNumber, lifeInsuranceQuestion1, lifeInsuranceQuestion2, lifeInsuranceQuestion3);

        }catch(InvalidParameterException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }
        response.sendRedirect("/crm-project/successful-submit.html");
    }

    //method that takes an HttpServletRequest object, a String representing the key associated with a parameter of the request object,
    // and an error message to appropriately inform the user which field was not entered correctly.
    //sets a String equal to the value of the parameter retrieved when using the key
    //checks if the returned parameter is a valid form entry
    //returns the parameter if it is valid and throws an InvalidParameterException otherwise
    private String getValidFormInput(HttpServletRequest request, String requestParameterKey, InvalidFormInputMessages invalidFormInputMessages) throws InvalidParameterException{
        String requestParameter = request.getParameter(requestParameterKey);
        if(isValidFormInput(requestParameter))
            return requestParameter;
        else
            throw new InvalidParameterException(invalidFormInputMessages.getMessage());
    }

    //returns whether a requestParameter is not null and is not an empty String to check if it is valid
    private boolean isValidFormInput(String requestParameter){
        return (requestParameter != null && !requestParameter.isEmpty());
    }

    //enum representing all the error messages thrown when a field entered in the form is invalid
    private static enum InvalidFormInputMessages{
        INVALID_EMAIL_ADDRESS("The email address field of the form is required."),
        INVALID_FIRST_NAME("The first name field of the form is required."),
        INVALID_LAST_NAME("The last name field of the form is required."),
        INVALID_PHONE_NUMBER("The phone number field of the form is required."),
        INVALID_QUESTION_1("The first life insurance question of the form is required."),
        INVALID_QUESTION_2("The second life insurance question of the form is required."),
        INVALID_QUESTION_3("The third life insurance question of the form is required.");

        private final String message;

        InvalidFormInputMessages(String message){this.message = message;}

        private String getMessage(){return this.message;}
    }

    //enum representing the keys used to retrieve the value of a parameter
    private static enum ParameterKeys{
        EMAIL_PARAMETER("emailAddress"),
        FIRST_NAME_PARAMETER("firstName"),
        LAST_NAME_PARAMETER("lastName"),
        PHONE_PARAMETER("phoneNumber"),
        QUESTION_1_PARAMETER("lifeInsuranceQuestion1"),
        QUESTION_2_PARAMETER("lifeInsuranceQuestion2"),
        QUESTION_3_PARAMETER("lifeInsuranceQuestion3");

        private final String parameterKey;

        ParameterKeys(String parameterKey){this.parameterKey = parameterKey;}

        private String getParameterKey(){return this.parameterKey;}
    }

}
