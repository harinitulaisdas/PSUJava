package edu.pdx.cs410J.gharini.client;

/***
 * Exception class for the class when the customer doesn't have a phonebill
 */
public class NoSuchPhoneBillException extends RuntimeException {


    public NoSuchPhoneBillException() {

    }

    public NoSuchPhoneBillException( String msg){
        super(msg);
    }
}
