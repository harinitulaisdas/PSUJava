package edu.pdx.cs410J.gharini;

/**
 * This is the exception class for the case where the caller and callee have same phone number.
 */

public class SameCallerAndCalleeException extends RuntimeException {

    public SameCallerAndCalleeException(){

    }

    public SameCallerAndCalleeException(String msg){
        super(msg);
    }
}
