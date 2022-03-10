package edu.pdx.cs410J.gharini;

public class FileException extends RuntimeException{
    public FileException(){

    }

    public FileException(String msg){
        super(msg);
    }
}
