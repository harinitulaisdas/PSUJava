package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.ParserException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Project3 {

    /**
     *
     * @param args : Entry point for the project. The main method parses all command line arguments and validates the arguments. It also
     *             performs suitable actions for different options specified in the command line.
     */
    public static void main(String[] args){

        String customer_name = null;
        String callerNumber = null;
        String calleeNumber =null;
        String startDate = null;
        String startTime =null;
        String endDate =null;
        String endTime = null;
        String startAmPm = null;
        String endAmPm = null;
        String file = null;
        Date finalStartTime = null;
        Date finalEndTime = null;
        String prettyFile;
        Boolean prettyPrintToFile ;
        ArrayList<String> options = null;
        ArrayList<String> nonOptions =null;
        ArrayList<String> files=null ;
        ArrayList<ArrayList<String>> list;
        PhoneCall call ;
        PhoneBill bill ;
        PhoneBill billFromFile = null;
        String filePath;
        try{
            list = PhoneCallHelper.loadOptions(args);
            for(ArrayList a : list){
                if(options == null){
                    options = a;
                }
                else if(nonOptions==null){
                    nonOptions = a;
                }else if(files == null){
                    files = a;
                }
            }
            int numOfNonOptions = nonOptions.size ();
            prettyPrintToFile =  PhoneCallHelper.checkifPrettyPrintToFile(args);
            PhoneCallHelper.checkNumOfArgs(numOfNonOptions,args ,prettyPrintToFile);
            int startPt = PhoneCallHelper.getStartPoint(prettyPrintToFile,options,nonOptions,files);
            if(options.contains ("-textFile")){
                file = args[Arrays.asList (args).indexOf ("-textFile") +1];
            }
            for (int i = startPt; i<args.length ; i++){
                if(customer_name == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    customer_name = args[i];
                } else if (callerNumber == null){
                    callerNumber = PhoneCallHelper.getPhoneNumbers (args[i]);
                } else if (calleeNumber == null ){
                    calleeNumber = PhoneCallHelper.getPhoneNumbers (args[i]);
                    PhoneCallHelper.checkCallerAndCallee (callerNumber,calleeNumber);
                } else if (startDate == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkDateFormat (args[i]);
                    startDate = args[i];
                } else if (startTime == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    startTime = args[i];
                }else if (startAmPm == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkAmPmForCase (args[i]);
                    startAmPm = args[i];
                }else if(endDate == null ) {
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkDateFormat (args[i]);
                    endDate = args[i];
                }else if (endTime == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    endTime = args[i];
                }else if(endAmPm == null ) {
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkAmPmForCase (args[i]);
                    endAmPm = args[i];
                    PhoneCallHelper.checkTimeFormat ( startTime, startAmPm );
                    PhoneCallHelper.checkTimeFormat ( endTime, endAmPm );
                    PhoneCallHelper.checkDateDifference (startDate, startTime + " "+startAmPm,endDate,endTime+ " " +endAmPm);
                    finalStartTime = PhoneCallHelper.convertToDate (startDate,startTime,startAmPm);
                    finalEndTime = PhoneCallHelper.convertToDate (endDate,endTime,endAmPm);
                } else{
                    String msg = "Extraneous command line argument" ;
                    PhoneCallHelper.printErrorMessageAndExit (msg+" : "+args[i]);
                }
            }
            call = new PhoneCall(customer_name,callerNumber,calleeNumber,finalStartTime,finalEndTime);
            bill = new PhoneBill(customer_name);
            bill.addPhoneCall (call);

            if(options.contains ("-textFile")){
                try {
                    if(!new File (file).isAbsolute ()){
                        filePath = new File("").getAbsolutePath ();
                        TextDumper dumper = new TextDumper (filePath.concat ("/"+file),call,customer_name);
                        dumper.dump (bill);
                        TextParser parser = new TextParser (filePath.concat ("/"+file),customer_name);
                        billFromFile = parser.parse ();
                    } else{
                        System.out.println (file);
                        TextDumper dumper = new TextDumper (file,call,customer_name);
                        dumper.dump (bill);
                        TextParser parser = new TextParser (file,customer_name);
                        billFromFile = parser.parse ();
                    }
                    //System.out.println ("___________________________________________________________________________________________________________");
                    //System.out.println ("\nPhone Bill From the file : \n" + billFromFile.getPhoneCalls ());
                    //System.out.println ("___________________________________________________________________________________________________________");
                } catch (IOException |ParserException | FileException e){
                    PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
                    e.printStackTrace ();
                }
            }
            if(options.contains ("-pretty") && nonOptions.contains ("-")) {
                PrettyPrinter pretty_printer = new PrettyPrinter (billFromFile,customer_name);
                pretty_printer.dumpPrettyContentToStandardOut (billFromFile);
            }
            if(prettyPrintToFile){
                try {
                    int index = Arrays.asList (args).indexOf ("-pretty");
                    prettyFile = args[index + 1];
                    if (!new File (prettyFile).isAbsolute ()) {
                        filePath = new File("").getAbsolutePath ();
//                        TextDumper dumper = new TextDumper (filePath.concat ("/"+file), call, customer_name);
//                        dumper.dump (bill);
//                        TextParser parser = new TextParser (filePath.concat ("/"+file), customer_name);
//                        billFromFile = parser.parse ();
                        PrettyPrinter pretty_printer = new PrettyPrinter (filePath.concat ("/"+prettyFile), billFromFile, customer_name);
                        pretty_printer.dump (billFromFile);
                    }
                    else{
//                        TextDumper dumper = new TextDumper (file,call,customer_name);
//                        dumper.dump (bill);
//                        TextParser parser = new TextParser (file,customer_name);
//                        billFromFile = parser.parse ();
                        PrettyPrinter pretty_printer = new PrettyPrinter (prettyFile, billFromFile, customer_name);
                        pretty_printer.dump (billFromFile);
                    }
                } catch (IOException | FileException e){
                    PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
                }
            } if(options.contains ("-print")){
                System.out.println ("_______________________________________________________________________________________________________________");
                PhoneCallHelper.printCall (call);
                System.out.println ("_______________________________________________________________________________________________________________");
            }
            if(options.contains ("-README")){
                System.out.println ("_______________________________________________________________________________________________________________");
                PhoneCallHelper.readme ();
                System.out.println ("_______________________________________________________________________________________________________________");
            }
        }
        catch (InvalidArgumentFormatException|InvalidNumberOfArgumentsException|InvalidPhoneNumberException
              |InvalidDateAndTimeException|SameCallerAndCalleeException| InvalidOptionException ex) {
            PhoneCallHelper.printErrorMessageAndExit (ex.getMessage ());
        }
        catch(Exception e){
            PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
        }

        System.exit(1);
    }
}
