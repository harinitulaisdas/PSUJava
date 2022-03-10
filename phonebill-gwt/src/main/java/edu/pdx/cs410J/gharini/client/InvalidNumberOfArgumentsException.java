package edu.pdx.cs410J.gharini.client;
/**
 * This is the exception class for the Invalid number of arguments in the command line
 */

public class InvalidNumberOfArgumentsException extends RuntimeException {
    public InvalidNumberOfArgumentsException() {
    }

    public InvalidNumberOfArgumentsException( String msg){
        super(msg);


    }

}
