package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static void main(String[] args) {
        String hostName = null;
        String portString = null;
        String customer = null;
        String callerNumber = null;
        String calleeNumber = null;
        String startDate = null;
        String startTime =null;
        String endDate =null;
        String endTime = null;
        String startAmPm = null;
        String endAmPm = null;
        Date finalStartTime = null;
        Date finalEndTime = null;
        ArrayList<String> options = null;
        ArrayList<String> nonOptions =null;
        ArrayList<ArrayList<String>> list;
        PhoneCall call = null;
        PhoneBill bill;
        int port = 0;

        try{
            list = PhoneCallHelper.loadOptions(args);
            for(ArrayList a : list) {
                if (options == null) {
                    options = a;
                }
                else if (nonOptions == null) {
                    nonOptions = a;
                }
            }
            if(options!=null && options.contains ("-README")){
                PhoneCallHelper.readme ();
                System.exit (1);
            }
            if(options!=null && (options.contains ("-host") || options.contains ("-port"))){
                  PhoneCallHelper.checkHostAndPort (args);
            }
            if(options!=null && nonOptions!=null){
                PhoneCallHelper.checkNumberOfArguments (args,nonOptions.size (),options.size (),options);
            }
            int hostIndex = Arrays.asList (args).indexOf ("-host");
            int portIndex = Arrays.asList (args).indexOf ("-port");
            hostName = args[hostIndex+1];
            portString = args[portIndex +1];
            port = Integer.parseInt (portString);
            PhoneBillRestClient client = new PhoneBillRestClient (hostName,port);
            int startPt = options.size ()+2;
            if( !options.contains ("-search")){

                for(int i = startPt ; i < args.length ; i++){
                    if(customer == null ){
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        customer = args[i];
                    }
                    else if (callerNumber == null){
                        callerNumber = PhoneCallHelper.getPhoneNumbers (args[i]);
                    }
                    else if (calleeNumber == null){
                        calleeNumber = PhoneCallHelper.getPhoneNumbers (args[i]);
                        PhoneCallHelper.checkCallerAndCallee (callerNumber,calleeNumber);
                    }
                    else if (startDate == null){
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        PhoneCallHelper.checkDateFormat (args[i]);
                        startDate = args[i];
                    }
                    else if (startTime == null){
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        startTime = args[i];
                    }
                    else if (startAmPm == null){
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        PhoneCallHelper.checkAmPmForCase (args[i]);
                        startAmPm = args[i];
                    }
                    else if(endDate == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        PhoneCallHelper.checkDateFormat (args[i]);
                        endDate = args[i];
                    }
                    else if (endTime == null){
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        endTime = args[i];
                    }
                    else if(endAmPm == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        PhoneCallHelper.checkAmPmForCase (args[i]);
                        endAmPm = args[i];
                        PhoneCallHelper.checkTimeFormat ( startTime, startAmPm );
                        PhoneCallHelper.checkTimeFormat ( endTime, endAmPm );
                        PhoneCallHelper.checkDateDifference (startDate, startTime + " "+startAmPm,endDate,endTime+ " " +endAmPm);
                        finalStartTime = PhoneCallHelper.convertToDate (startDate,startTime,startAmPm);
                        finalEndTime = PhoneCallHelper.convertToDate (endDate,endTime,endAmPm);
                    }
                    else{
                        String msg = "Extraneous command line argument" ;
                        PhoneCallHelper.printErrorMessageAndExit (msg+" : "+args[i]);
                    }
                }
                call = new PhoneCall(callerNumber,calleeNumber,finalStartTime,finalEndTime);
                bill = new PhoneBill(customer);
                bill.addPhoneCall (call);

            } else {
                for (int i = startPt; i < args.length; i++) {

                    if (customer == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        customer = args[i];
                    }
                    else if (startDate == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        PhoneCallHelper.checkDateFormat (args[i]);
                        startDate = args[i];
                    }
                    else if (startTime == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        startTime = args[i];
                    }
                    else if (startAmPm == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        PhoneCallHelper.checkAmPmForCase (args[i]);
                        startAmPm = args[i];
                    }
                    else if (endDate == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        PhoneCallHelper.checkDateFormat (args[i]);
                        endDate = args[i];
                    }
                    else if (endTime == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        endTime = args[i];
                    }
                    else if (endAmPm == null) {
                        PhoneCallHelper.checkValidArgumentFormat (args[i]);
                        PhoneCallHelper.checkAmPmForCase (args[i]);
                        endAmPm = args[i];
                        PhoneCallHelper.checkTimeFormat (startTime, startAmPm);
                        PhoneCallHelper.checkTimeFormat (endTime, endAmPm);
                        PhoneCallHelper.checkDateDifference (startDate, startTime + " " + startAmPm, endDate, endTime + " " + endAmPm);
                        finalStartTime = PhoneCallHelper.convertToDate (startDate, startTime, startAmPm);
                        finalEndTime = PhoneCallHelper.convertToDate (endDate, endTime, endAmPm);
                    }
                    else {
                        String msg = "Extraneous command line argument";
                        PhoneCallHelper.printErrorMessageAndExit (msg + " : " + args[i]);
                    }

                }
            }
            if(options.contains ("-host") && options.contains ("-port")){
                try {
                    if (options.contains ("-search")) {
                        String st_time = startDate + " " + startTime + " " + startAmPm;
                        String e_time = endDate + " " + endTime + " " + endAmPm;
                        if(startDate==null || endDate==null){
                            PhoneCallHelper.printErrorMessageAndExit ("Missing start/end times." +
                                    "The -search option should have start and end times. ");
                        }
                        System.out.println (client.searchPhonebill (customer, st_time, e_time));
                    } else if (nonOptions.size () == 3 && options.size ()==2) {
                        System.out.println (client.getPrettyPhoneBill (customer));
                    }
                    else{
                        if(call!=null){
                            String st_time = startDate + " " + startTime + " " + startAmPm;
                            String e_time = endDate + " " + endTime + " " + endAmPm;
                          client.addPhoneCall (customer,callerNumber,calleeNumber,st_time,e_time);
                          System.out.println ("Phone call created and added to the phone bill");
                        }
                    }
                } catch (IOException  e){
                    PhoneCallHelper.printErrorMessageAndExit ("Error while fetching content :\n" + e.getMessage ());
                } catch (Exception e){
                    PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
                }
            }

            if(options.contains ("-print")){
                System.out.println ("__________________________________________________________________________________________");
                PhoneCallHelper.printCall (call);
                System.out.println ("__________________________________________________________________________________________");
            }

        } catch (InvalidArgumentFormatException|InvalidNumberOfArgumentsException|InvalidPhoneNumberException
                |InvalidDateAndTimeException|SameCallerAndCalleeException| InvalidOptionException ex) {
            PhoneCallHelper.printErrorMessageAndExit (ex.getMessage ());
        }
        catch(Exception e){
            PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
        }
    System.exit(1);
   }



}