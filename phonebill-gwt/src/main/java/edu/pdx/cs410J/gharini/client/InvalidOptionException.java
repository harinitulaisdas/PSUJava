package edu.pdx.cs410J.gharini.client;

/**
 * This is the exception class for the Invalid options in the command line
 */
public class InvalidOptionException extends RuntimeException {
   public InvalidOptionException(){

   }
   public InvalidOptionException(String msg){
       super(msg);
   }
}
