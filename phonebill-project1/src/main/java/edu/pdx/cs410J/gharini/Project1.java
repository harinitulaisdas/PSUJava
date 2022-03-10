package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.ArrayList;


/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

    /**
     *
     * @param args : The main method for the project1 takes input as command line arguments(args)
     *
     *             The Phone call and Phone bill object is constructed in this method by parsing the Command line arguments and
     *             performing respective error checks. The Phone call object has Customer , callerNumber , calleeNumber , start Date and end date fields.
     *
     *             The phone bill class has a collection of phone calls and the customer name.
     *
     *             The functionality to write the phone bill to a text file and to read a text file containing
     *             phone bill and convert it to a PhoneBill object is implemented in this project.
     *
     *             All the exceptions thrown during the error checks are handled in the main method and the user is prompted with an appropriate message.
     */
    public static void main(String[] args) {
        String customer = null;
        String callerNumber = null;
        String calleeNumber = null;
        String startDate = null;
        String endDate = null;
        String startTime = null;
        String endTime = null;
        String file = null;
        ArrayList<String> options = new ArrayList<>();
        ArrayList<String> nonOptions = new ArrayList<>();
        ArrayList<ArrayList<String>> list = null;
        try{
            list = PhoneCallHelper.loadOptions(args);
            options = list.get(0);
            nonOptions = list.get (1);
            int numOfArgs = args.length;
            int numOfOptions = options.size();
            int numOfNonOptions = nonOptions.size ();
            PhoneCallHelper.checkNumOfArgs(numOfNonOptions,numOfArgs,args);
            int startPt = 0;
            if(options.contains ("-textFile")){
                startPt = numOfOptions + 1;
            } else startPt = numOfOptions;
            for (int i = startPt; i<args.length ; i++){
                if(customer == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    customer = args[i];

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
                    PhoneCallHelper.checkTimeFormat (args[i]);
                    startTime = args[i];

                }else if(endDate == null ) {
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkDateFormat (args[i]);
                    endDate = args[i];


                }else if (endTime == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkTimeFormat (args[i]);
                    endTime = args[i];
                    PhoneCallHelper.checkDateDifference (startDate, startTime,endDate,endTime);
                    }
                    else{
                    PhoneCallHelper.printErrorMessageAndExit ("Extraneous command line argument " + args[i]);
                }
            }
        }
        catch(InvalidArgumentFormatException iaf){
            PhoneCallHelper.printErrorMessageAndExit (iaf.getMessage ());
        }
        catch(InvalidNumberOfArgumentsException ina) {
            PhoneCallHelper.printErrorMessageAndExit (ina.getMessage ());
        }
        catch(InvalidPhoneNumberException ipn){
            PhoneCallHelper.printErrorMessageAndExit (ipn.getMessage ());
        }
        catch (InvalidDateAndTimeException idf){
            PhoneCallHelper.printErrorMessageAndExit (idf.getMessage ());
        }
        catch (SameCallerAndCalleeException scc){
            PhoneCallHelper.printErrorMessageAndExit (scc.getMessage ());
        }
        catch (InvalidOptionException ioe){
            PhoneCallHelper.printErrorMessageAndExit (ioe.getMessage ());
        }
        catch(Exception e){
            PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
        }
        PhoneCall call = new PhoneCall(customer,callerNumber,calleeNumber,startDate, startTime,endDate,endTime);
        PhoneBill bill = new PhoneBill(customer);
        PhoneBill billFromFile;
        String filePath = null;
        bill.addPhoneCall (call);
        for(String option:options){
            if(option.equals ("-print")){
                System.out.println ("_______________________________________________________________________________________________________________");
                PhoneCallHelper.printCall (call);
                System.out.println ("_______________________________________________________________________________________________________________");
            }else if(option.equals ("-README")){
                System.out.println ("_______________________________________________________________________________________________________________");
                PhoneCallHelper.readme ();
                System.out.println ("_______________________________________________________________________________________________________________");
            }else if(option.equals ("-textFile")){
                file = nonOptions.get (0);
                try {
                    if(!new File(file).isAbsolute ()){
                        filePath = new File("").getAbsolutePath ();
                        TextDumper dumper = new TextDumper (filePath.concat ("/"+file),call,customer);
                        dumper.dump (bill);
                        TextParser parser = new TextParser (filePath.concat ("/"+file),customer);
                        billFromFile = parser.parse ();
                    } else{
                        System.out.println (filePath);
                        TextDumper dumper = new TextDumper (file,call,customer);
                        dumper.dump (bill);
                        TextParser parser = new TextParser (file,customer);
                        billFromFile = parser.parse ();
                    }
                    System.out.println ("___________________________________________________________________________________________________________");
                    System.out.println ("\nPhone Bill From the file : \n" + billFromFile.getPhoneCalls ());
                    System.out.println ("___________________________________________________________________________________________________________");
                } catch (IOException e){
                    PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
                } catch (ParserException e) {
                    PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
                } catch (FileException fe){
                    PhoneCallHelper.printErrorMessageAndExit (fe.getMessage ());
                }
            }
        }
        System.exit(1);
    }

}