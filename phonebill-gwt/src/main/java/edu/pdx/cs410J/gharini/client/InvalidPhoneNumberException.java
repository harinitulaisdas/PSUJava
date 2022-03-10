package edu.pdx.cs410J.gharini.client;
/**
 * This is the exception class for the Invalid phone number in the command line argument
 */

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException() {

    }
    public InvalidPhoneNumberException( String msg){
        super(msg);
    }
}
