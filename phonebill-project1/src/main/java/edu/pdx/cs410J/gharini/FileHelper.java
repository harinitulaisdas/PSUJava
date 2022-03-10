package edu.pdx.cs410J.gharini;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FileHelper {


    /**
     * @param phonebill : The path to the file
     *                  This method checks if a text file exists in the location. If the file doesn't exist
     *                  a new text file is created.
     */

    public static void checkIfFileExistsAndCreateIfOtherwise(File phonebill) {

        try {
            if (phonebill.exists () && phonebill.isDirectory ()) {
                throw new FileException ("This is a directory :" + phonebill);
            }
            else if (phonebill.exists () && !phonebill.isFile ()) {
                throw new FileException ("This is not a file :" + phonebill);
            }
            else if (phonebill.exists () && phonebill.length () == 0) {
                throw new FileException ("This file is malformed : " + phonebill);
            }
            else if (!phonebill.exists ()) {
                phonebill.createNewFile ();
            }
        } catch (FileNotFoundException fe) {
            throw new FileException (fe.getMessage () + " : " + phonebill);
        } catch (UnsupportedEncodingException ue) {
            throw new FileException (ue.getMessage () + " : " + phonebill);
        } catch (IOException ie) {
            throw new FileException ("File doesn't exist and cannot be created here : " + phonebill);
        }

    }

    /**
     * @param phonebill : the text file to read from
     * @return The contents of the file as String
     * This method reads the contents of the file to a string and returns it.
     */


    public static String readFile(File phonebill, String customer) {
        StringBuilder contentBuilder = new StringBuilder ();

        try (BufferedReader br = new BufferedReader (new FileReader (phonebill))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine ()) != null) {
                if (sCurrentLine != null) {
                    compareName (sCurrentLine, customer);
                    contentBuilder.append (sCurrentLine).append ("\n");
                }
            }
        } catch (IOException e) {
            throw new FileException ("The file is malformed : " + phonebill);
        } catch (FileException fe) {
            throw new FileException (fe.getMessage ());
        }
        return contentBuilder.toString ();
    }

    /**
     * @param line     : The string that contains the line read from the file.
     * @param customer : The customer name
     *                 This method compares the customer name on the file with the customer name from the command line argument.
     *                 If there is a mismatch an exception is raised with an appropriate message
     */

    public static void compareName(String line, String customer) {
        String[] values = line.split (",");
        if (!values[0].equals (customer)) {
            throw new FileException ("The customer name on the file is different from the one entered in command line : \nThe name entered by user : " + customer + "\nThe name on the file :  " + values[0]);
        }
    }

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
        PhoneCall callFromBill = null;
        iterator = map.values ().iterator ();
        while (iterator.hasNext()) {
            ArrayList element = iterator.next();
            iterator1 = element.iterator ();

            while (iterator1.hasNext ()){
                int i = 0;
                ArrayList tt = iterator1.next ();
                iterator2 = tt.iterator ();
                String callerNumber = null;
                String calleeNumber = null;
                String startDate = null;
                String endDate = null;
                String startTime = null;
                String endTime = null;
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
                        else if(startDate == null){
                            dateTime = val.trim().split (" ");
                            PhoneCallHelper.checkValidArgumentFormat (dateTime[0]);
                            PhoneCallHelper.checkDateFormat (dateTime[0]);
                            startDate = dateTime[0];
                            PhoneCallHelper.checkValidArgumentFormat (dateTime[1]);
                            PhoneCallHelper.checkTimeFormat (dateTime[1]);
                            startTime = dateTime[1];

                        }
                        else if(endDate == null ) {
                            dateTime = val.trim().split (" ");
                            PhoneCallHelper.checkValidArgumentFormat (dateTime[0]);
                            PhoneCallHelper.checkDateFormat (dateTime[0]);
                            endDate = dateTime[0];
                            PhoneCallHelper.checkValidArgumentFormat (dateTime[1]);
                            PhoneCallHelper.checkTimeFormat (dateTime[1]);
                            endTime = dateTime[1];
                            PhoneCallHelper.checkDateDifference (startDate, startTime,endDate,endTime);
                        }
                    } catch(InvalidArgumentFormatException iaf){
                        PhoneCallHelper.printErrorMessageAndExit ("The Content on the file is corrupted" +"\n" + iaf.getMessage () );
                    }catch(InvalidPhoneNumberException ipn){
                        PhoneCallHelper.printErrorMessageAndExit ("The Content on the file is corrupted" +"\n"+ ipn.getMessage ());
                    }
                    catch (InvalidDateAndTimeException idf){
                        PhoneCallHelper.printErrorMessageAndExit ("The Content on the file is corrupted" +"\n"  + idf.getMessage ());
                    }
                    catch (SameCallerAndCalleeException scc){
                        PhoneCallHelper.printErrorMessageAndExit ("The Content on the file is corrupted" +"\n" +scc.getMessage ());
                    }

                }
                callFromBill = new PhoneCall (customer,callerNumber,calleeNumber,startDate,startTime,endDate,endTime);
                bill.addPhoneCall (callFromBill);
            }


        }


        return bill;
    }
}