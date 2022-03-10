package edu.pdx.cs410J.gharini;

/**
 * the exception class to handle file related exceptions.
 */
public class FileException extends RuntimeException{
    public FileException(){

    }

    public FileException(String msg){
        super(msg);
    }
}
