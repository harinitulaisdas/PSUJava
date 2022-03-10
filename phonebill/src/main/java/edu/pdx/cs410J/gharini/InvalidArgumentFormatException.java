package edu.pdx.cs410J.gharini;
/**
 * This is the exception class for the Invalid argument format in the command line
 */
public class InvalidArgumentFormatException extends RuntimeException{
    public InvalidArgumentFormatException(){

    }
    public InvalidArgumentFormatException(String msg){
        super(msg);
    }
}
