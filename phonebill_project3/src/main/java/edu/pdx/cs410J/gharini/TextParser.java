package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.File;
import java.util.ArrayList;
import java.util.*;
import java.util.Iterator;

/***
 * This class is implemented as part of Project 2. This class implements in the interface PhoneBillParser.
 * This class contains the method parse() to parse the contents of the text file
 */

public class TextParser implements PhoneBillParser<PhoneBill> {
    private File file ;
    private String customer ;
    public TextParser(String path , String customer){
        this.file = new File (path);
        this.customer = customer;
    }
    private HashMap<String, ArrayList<ArrayList <String>>> callMap = new HashMap<String, ArrayList<ArrayList <String>>>();

    /**
     *
     * @return
     * @throws ParserException : Exception when there is an error during parsing the contents of file to phonebill.
     */
    @Override
    public PhoneBill parse() throws ParserException {

        PhoneBill bill ;
        FileHelper.checkIfFileExistsAndCreateIfOtherwise(this.file);
        String fileContent = FileHelper.readFile(this.file ,this.customer);
        callMap = FileHelper.splitContent (fileContent);
        bill = FileHelper.loadCallDetailsFromFileToPhoneBill (callMap,customer);
        return bill;
    }




}
