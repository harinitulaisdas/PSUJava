package edu.pdx.cs410J.gharini.client;
/**
 * This is the exception class for the Invalid format of the start and end times of the phone call.
 */

public class InvalidDateAndTimeException extends RuntimeException {
    public InvalidDateAndTimeException(){

    }

    public  InvalidDateAndTimeException(String msg){
        super(msg);
    }
}
