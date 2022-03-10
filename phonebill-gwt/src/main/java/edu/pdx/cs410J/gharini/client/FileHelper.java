package edu.pdx.cs410J.gharini.client;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.*;

public class FileHelper {


    /**
     * @param content : The contents of the file as a string
     * @return hashmap : The hashmap that contains customer name as key and his/her phone call details as values.
     * <p>
     * This method takes a string that has content of the file as input and converts it to a hashmap that contains
     * the list of all phone calls from the file. Each phone call of the customer is converted to a list and all phone call lists
     * of a customer are added to another list. This list is then added as value to a hashmap along with customer name as key.
     */
    public static HashMap splitContent(String content) {
        String[] values;
        String name = null;
        HashMap<String, ArrayList<ArrayList<String>>> hashmap = new HashMap<String, ArrayList<ArrayList<String>>> ();
        ArrayList<ArrayList<String>> callContent = new ArrayList<ArrayList<String>> ();
        if (((content.split ("\n")).length) == 1) {
            ArrayList<String> temp = new ArrayList<String> ();
            values = content.split (",");
            name = values[0];
            helperToExtractCallContent (values, callContent, temp);
            hashmap.put (name, callContent);
        }
        else if (((content.split ("\n")).length) > 1) {
            String[] eachCall = (content.split ("\n"));
            for (String call : eachCall) {
                ArrayList<String> temp = new ArrayList<String> ();
                values = call.split (",");
                name = values[0];
                helperToExtractCallContent (values, callContent, temp);
            }
            hashmap.put (name, callContent);
        }
        return hashmap;
    }

    /**
     * @param values
     * @param callContent
     * @param temp        This is a utility method that helps in the parsing the contents of the file to a phone bill.
     */

    private static void helperToExtractCallContent(String[] values, ArrayList<ArrayList<String>> callContent, ArrayList<String> temp) {
        for (int i = 1; i < values.length; i++) {
            temp.add (values[i]);
        }
        callContent.add (temp);
    }
    /**
     *
     * @param map  : hashMap that maps customer name to his/her phone bill.
     * @param customer : customer name from the text file.
     * @return : A PhoneBill .
     *
     * This method reads the values of the hashmap and parses it a phone call and adds it to the phone bill of the customer.
     */

    public static PhoneBill loadCallDetailsFromFileToPhoneBill (HashMap map , String customer){
        Iterator<ArrayList<ArrayList>> iterator = null;
        Iterator<ArrayList> iterator1 = null;
        Iterator<String> iterator2 = null;
        PhoneBill bill = new PhoneBill (customer) ;
        PhoneCall callFromBill ;
        iterator = map.values ().iterator ();
        while (iterator.hasNext()) {
            ArrayList element = iterator.next();
            iterator1 = element.iterator ();
            while (iterator1.hasNext ()){
                ArrayList tt = iterator1.next ();
                iterator2 = tt.iterator ();
                String callerNumber = null;
                String calleeNumber = null;
                String startDate = null;
                String endDate = null;
                String startTime = null;
                String startAmPm=null;
                String endTime = null;
                String endAmPm=null;
                String[] dateTime = null ;
                while (iterator2.hasNext ()){
                    String val = iterator2.next ();
                    try{
                        if(callerNumber == null){
                            callerNumber = PhoneCallHelper.getPhoneNumbers (val.trim ());
                        }
                        else if(calleeNumber == null){
                            calleeNumber = PhoneCallHelper.getPhoneNumbers (val.trim ());
                            PhoneCallHelper.checkCallerAndCallee (callerNumber,calleeNumber);
                        }
                        else if(startDate == null && startTime == null && startAmPm == null){
                            dateTime = getDateTime (val);
                            startDate = dateTime[0];
                            startTime = dateTime[1];
                            startAmPm = dateTime[2];
                        }
                        else if(endDate == null && endTime == null && endAmPm == null){
                            dateTime = getDateTime (val);
                            endDate = dateTime[0];
                            endTime = dateTime[1];
                            endAmPm = dateTime[2];
                        }
                    }catch(InvalidArgumentFormatException |InvalidPhoneNumberException| InvalidDateAndTimeException | SameCallerAndCalleeException ex){
                        PhoneCallHelper.printErrorMessageAndExit ("The Content on the file is corrupted" +"\n" + ex.getMessage () );
                    }
                }
                callFromBill = new PhoneCall (callerNumber,calleeNumber,PhoneCallHelper.convertToDate (startDate,startTime,startAmPm),PhoneCallHelper.convertToDate (endDate,endTime,endAmPm));
                bill.addPhoneCall (callFromBill);
            }
        }
        return bill;
    }
    /**
     *
     * @param val : The date and time value as read from the file
     * @return : the date and time validated and formatted to specified format.
     */

    private static String[] getDateTime(String val) {
        String[] dateTime;
        PhoneCallHelper.checkValidArgumentFormat (val.trim ());
        dateTime = val.split (" ");
        PhoneCallHelper.checkDateFormat (dateTime[0].trim ());
        PhoneCallHelper.checkTimeFormat (dateTime[1].trim (),dateTime[2].trim ());
        return dateTime;
    }

    /**
     *
     * @param sdate  : The start time of the phone call
     * @param edate   : the end time of the phone call
     * @return the call duration in minutes.
     */
    private static long getDurationOfcallInMinutes(Date sdate  , Date edate){
       long diffInMilliSec = edate.getTime () - sdate.getTime ();
       long diffInMinutes = diffInMilliSec/(1000*60);
       return diffInMinutes;
    }

    /**
     *@param bill : the phone bill with all the phone calls
     * @return  : The sorted set of the phone calls
     */
    private  static SortedSet<PhoneCall> getSortedSet(PhoneBill bill) {
        SortedSet<PhoneCall> sortedCalls = new TreeSet<> (bill.getPhoneCalls ());
        return sortedCalls;
    }

    /**
     *
     * @param bill : The phonebill of a customer
     * @return : the bill with phone calls sorted in chronological order of time
     * formatted as a pretty content
     */

    public static String getPrettyContent(PhoneBill bill){
        String prettyContent = null;
        SortedSet<PhoneCall> sortedCalls = getSortedSet (bill);
        prettyContent = " The phone bill of the customer \n";
        prettyContent+= "----------------------------------------------------------------------------------\n";
        prettyContent += "*Name of the customer :" + bill.getCustomer () + "\n\n";
        for(PhoneCall call : sortedCalls){
            prettyContent+= "----------------------------------------------------------------------------------\n";
            prettyContent+="*Phone call from  " + call.getCaller () + "  to  "+ call.getCallee () + "\n";
            prettyContent+="*  on  " +  getPrettyDateTime (call.getStartTime ()) + "  to  " +  getPrettyDateTime (call.getEndTime ())+"\n";
            prettyContent+= "----------------------------------------------------------------------------------\n";
            prettyContent+="*The call duration was  "+ getDurationOfcallInMinutes (call.getStartTime (),call.getEndTime ()) +"  minutes\n";
            prettyContent+= "----------------------------------------------------------------------------------\n";
        }
       return prettyContent;
    }

    /**
     *
     * @param date
     * @return
     */

    public static String getPrettyDateTime(Date date)  {
        DateTimeFormat sdf =  DateTimeFormat.getFormat ("EEE ,MMM yyyy 'at' hh:mm a");

        String sMyDate = sdf.format (date);
        return sMyDate;
    }
}