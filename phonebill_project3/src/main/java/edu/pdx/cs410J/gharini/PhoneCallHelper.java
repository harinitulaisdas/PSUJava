package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class PhoneCallHelper {
    /**
     * @param msg This method prints the error message on the console when an exception is raised . The program terminates with the status code -1 when this
     *            method is executed.
     */
     static void printErrorMessageAndExit(String msg) {
        System.err.println (msg);
        System.exit (-1);

    }

    /**
     * @param call : An instance of a phone call .
     *             <p>
     *             This method prints the details of a phone call. This corresponds to the -print option in the command line arguments.
     */
    static void printCall(AbstractPhoneCall call) {
        System.out.println (call);
    }


    /**
     * This method has the readme information and prints it on the console ouput when the command line has -README option.
     */
    static void readme() {
        String readme = "Course    : Advanced Java Programming.\nName      : Harini Gowdagere Tulaisdas.\nemail     : gharini@pdx.edu\n";
        readme += "Project   : Project3\nObjective :\n";
        readme += "\t\tThe project 3 to implement a simple Phone Call Details generation has two classes phoneCall and phoneBill that represents the details of a phone call and phone bill respectively. " +
                "The phoneCall class has fields to hold the details of a phone call such as  customer name , caller (Phone number) , callee(phone number) ,  the start and end time of the call. " +
                "The phone bill has a collection of phone calls and the name of the customer \n" +
                "\n" +
                "\t\tThe application is invoked in the Project3 class. The Project3 class parses the command line arguments for the input." +
                " The command line arguments has the format [options] <args>. The [options] has 4 choices, -README , -print , -textFile filename (to write the" +
                " phone calls on to a file) , -pretty -(to pretty print on standard out)" +
                " and -pretty file (to pretty print on to a file) . The other arguments are phone numbers of the caller and callee and " +
                " the start and end times of the phone call. The start and end times have a 12 hr format.  The command line can have all the options or no options  and no arguments or all arguments for " +
                " a phone call. The program checks for the validity of all the arguments and failure to confirm validity results in a friendly error message. " +
                "\n\n" +
                "\t\tThe -print option in the input prints the phone call and the -readme option prints the readme information.  ";
        System.out.println (readme);
    }

    /**
     * @param arg : The phone number (caller / callee ) f
     * @return phoneNumber : A valid phone number.
     * <p>
     * This method parses the phone number and checks for the valid format.
     */

    static String getPhoneNumbers(String arg) {
        String phoneNumber = null;
        checkValidArgumentFormat (arg);
        checkPhoneNumberFormat (arg);
        phoneNumber = arg;
        return phoneNumber;
    }


    /**
     * @param : numOfNonOptions : count of number of non option arguments in the command line.
     * @throws : InvalidNumberOfArgumentsException : The number of arguments passed in is incorrect.
     *           <p>
     *           This method generates the appropriate exceptions if there are no command line arguments  , few arguments are missing or there are extra
     *           arguments. There can no options/1 option or all options provided. The non option arguments can be 0 or 5.
     * @param: numOfArgs : count of number of arguments passed in the command line
     * @param: numOfOptions : count of number of options in the command line arguments
     */

    static void checkNumOfArgs(int numOfNonOptions,  String [] args , Boolean ispretty) {
        if (args.length == 0) {
            throw new InvalidNumberOfArgumentsException ("Missing Command Line Arguments " );
        }
        else if (numOfNonOptions > 0 && numOfNonOptions < 9) {
            throw new InvalidNumberOfArgumentsException ("Missing few Command Line Arguments ");
        }
        else if (!Arrays.asList (args).contains ("-textFile") && !ispretty && numOfNonOptions > 10) {

            throw new InvalidNumberOfArgumentsException ("Command Line has too many arguments ");
        }

    }


    /**
     * @param args command line arguments
     * @return list : List of lists ,the list of options and non option arguments.
     * @throws : InvalidOptionException : The options are invalid/incorrect format.
     *           <p>
     *           This method checks if the options entered in the command line adhere to the specified format. The options strict match -README -print
     *           and are case sensitive. The method throws exceptions if the options are not in the correct format. The method also separates the
     *           options and non option arguments.
     */
    static ArrayList<ArrayList<String>> loadOptions(String[] args) {
        ArrayList<String> options = new ArrayList<> ();
        ArrayList<String> nonOptions = new ArrayList<> ();
        ArrayList<String> files = new ArrayList<> ();
        String textFile = null;
        String prettyFile = null;
        ArrayList<ArrayList<String>> list = new ArrayList<> ();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i].toLowerCase ();
            if ((arg.contains ("readme") || arg.contains ("print") || arg.contains ("textfile")|| arg.equals ("-pretty")) && !arg.startsWith ("-")) {
                throw new InvalidOptionException ("Invalid Option , Usage : -README -print -textFile -pretty:" + arg);
            }
            else if (arg.equals ("-readme") || arg.equals ("-print") || arg.equals ("-textfile") || arg.equals ("-pretty")) {
                if (args[i].matches ("-README") || args[i].matches ("-print") || args[i].matches ("-textFile") ||args[i].matches ("-pretty") ) {
                    options.add (args[i]);
                }
                else {
                    throw new InvalidOptionException ("Options are case sensitive , Usage : -README , -print , -textFile , -pretty:" + args[i]);
                }
            }
            else if(args[i].endsWith(".txt") || args.equals ("-")){
                files.add (args[i]);
            }else {
                nonOptions.add (args[i]);
            }
        }
        for (String opt : options) {
           if (opt.equals ("-textFile")) {
               checkFileOption (args ,"-textFile");
            }
        }
        list.add (options);
        list.add (nonOptions);
        list.add (files);
        return list;
    }

    /**
     * @param  : The non option arguments from the command line.
     *                   This method checks that the the -textFile is followed by the filename/filepath
     */
    static void checkFileOption(String [] args , String option) {
       int indexOftxtOption= Arrays.asList (args).indexOf (option);
        if (!args[indexOftxtOption + 1].endsWith (".txt")) {
            throw new InvalidOptionException ("The option -textFile should be followed by the file name/path ,Usage : -textFile filename.txt : " + args[indexOftxtOption + 1].endsWith (".txt"));
       }
    }

    /**
     *
     * @param args : the command line arguments.
     * This method checks if the pretty content is to be written on to the file or standard output.
     */

     static Boolean checkifPrettyPrintToFile(String[] args ) {
         Boolean isPrettyFile = false;
         if(Arrays.asList (args).contains ("-pretty")){
             int indexOfPretty = Arrays.asList (args).indexOf ("-pretty");
             if (args[indexOfPretty + 1].endsWith (".txt")) {
                 isPrettyFile = true;
             } else if(!args[indexOfPretty + 1].equals ("-")){
                 throw new InvalidOptionException ("The pretty option must be followed with a filename or - :" + args[indexOfPretty + 1]);
             }

         }

         return isPrettyFile;
     }

    /**
     *
     * @param
     * @param prettyPrintToFile : Boolean value that determines if the prety is to be written on to the file or standard output.
     * @param options : the optional arguments.
     * @return : the index of the argument where the phone call attributes begin.
     */
     static int getStartPoint( Boolean prettyPrintToFile , ArrayList options,ArrayList nonOptions ,ArrayList files){
         int startPt = 0;
         if(options.contains ("-textFile") && options.contains ("-pretty")){
             startPt = options.size () + 2;
         } else if(options.contains ("-pretty")){
             startPt = options.size () + 1;
         }else if(options.contains ("-textFile")){
             startPt = options.size () + 1;
         }
         else{
             startPt = options.size ();
         }
         return startPt;
     }
    /**
     * @param arg : The command line argument.
     * @throws InvalidArgumentFormatException : Argument format is wrong exception
     *                                        <p>
     *                                        This method checks if the command line argument is in the correct format.
     *                                        The arguments other than options cannot start with a hyphen (-). An exception is thrown
     *                                        when the argument doesnt have the correct format with an appropriate message.
     */
    static void checkValidArgumentFormat(String arg) {
        if (arg.length () > 1 && arg.startsWith ("-")) {
            throw new InvalidArgumentFormatException ("Invalid Argument : Argument cannot start with a -  :" + arg);
        }
    }

    /**
     * @param arg : The Phone number
     * @throws InvalidPhoneNumberException : The phone number is not in the correct format.
     *                                     <p>
     *                                     This method checks if the phone numbers have the format nnn-nnn-nnnn. In case the phone numbers are in wrong format an exception
     *                                     with appropriate message is thrown.
     */
    static void checkPhoneNumberFormat(String arg) {
        if (!arg.matches ("^\\(?([0-9]{3})\\)?[-\\s]?([0-9]{3})[-\\s]?([0-9]{4})")) {
            throw new InvalidPhoneNumberException ("Invalid Phone Number Format , Usage : nnn-nnn-nnnn  : " + arg);
        }

    }
    /**
     * @param caller : The phone number of the caller.
     * @param callee : The phone number of the callee.
     * @throws SameCallerAndCalleeException : The caller and callee has same phone numbers.
     *                                      <p>
     *                                      This method checks if the caller and callee has same phone number. In case they have same phone number
     *                                      a SameCallerAndCalleeException is thrown that gives a message that the phone numbers are same.
     */
     static void checkCallerAndCallee(String caller, String callee) {
        if (caller.equals (callee)) {
            throw new SameCallerAndCalleeException ("Caller and Callee should be different  :" + caller + " " + callee);
        }

    }
    /**
     *
     * @param ampm : the am/pm argument from the command line
     */
    static void checkAmPmForCase(String ampm){
         if(!ampm.matches (("am|pm"))){
             throw new InvalidDateAndTimeException ("The am/pm is case sensitive : " + ampm);
         }
    }

    /**
     * @param arg : the date variable as string
     * @throws InvalidDateAndTimeException This method checks if the Start Date and end Date of the phone call is in the valid format. The valid format for time date/time
     *                                     is "yyyy/MM/dd " . If the dates are in any format other than the specified one , an exception is thrown with the appropriate message.
     */
     static void checkDateFormat(String arg) {
         SimpleDateFormat sdf = new SimpleDateFormat ("MM/dd/yyyy", Locale.US);
        sdf.setLenient (false);
        if (!arg.matches ("\\d{2}/\\d{2}/\\d{4}"))
            throw new InvalidDateAndTimeException ("Invalid Date Format , Usage : MM/dd/yyyy  " + arg);
        try {
            Date date = sdf.parse (arg);
        } catch (ParseException pe) {
            throw new InvalidDateAndTimeException ("Invalid Date , Usage : MM/dd/yyyy  " + arg);

        } catch (DateTimeParseException dpe) {
            throw new InvalidDateAndTimeException ("Invalid Date , Usage : MM/dd/yyyy  " + arg);

        } catch (Exception e) {
            throw new InvalidDateAndTimeException ("Invalid Date , Usage : MM/dd/yyyy  " + arg);
        }
    }

    /**
     * @param time : the time variable as string
     * @param am_pm: the am/pm variable as string
     * @throws InvalidDateAndTimeException This method checks if the Start time and end time is in the valid format. The valid format for time
     *                                     is " HH:mm" . If the times are in any format other than the specified one , an exception is thrown with the appropriate message.
     */

     static void checkTimeFormat(String time , String am_pm) {
        SimpleDateFormat sdf = new SimpleDateFormat ("hh:mm a", Locale.US);
        String callTime = time + ' ' + am_pm;
        // ()
        String pattrn = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
                //"\\b(?:[01]?\\d|2[0-2]):[0-5][0-9](\\s)?(?i)(am|pm)";
        if (!callTime.matches (pattrn))
            throw new InvalidDateAndTimeException ("Invalid time format , Time must be in 12 hr format:" + callTime);
        try {
            Date date = sdf.parse (callTime);
        } catch (DateTimeParseException  | ParseException  dpe) {
            throw new InvalidDateAndTimeException ("Invalid time , Usage : HH:mm " + callTime);
        } catch (Exception e) {
            throw new InvalidDateAndTimeException ("Invalid time , Usage : HH:mm " + callTime);
        }

    }

    /**
     *
     * @param startDate : The Start time of the phone call.
     * @param endDate   : The end time of the phone call.
     * @throws InvalidDateAndTimeException
     *
     * This method is used the check the difference between the start and end date. It ensures that the end date of phone call is after
     * the start date and the end date of the phone call is before current date and time. In case of failure to conform to the rule and exception
     * stating appropriate reason is thrown.
     */
      static void checkDateDifference(String startDate ,String startTime, String endDate, String endTime ){
         String strDate = startDate + " " + startTime;
         String eDate = endDate + " " + endTime;
         SimpleDateFormat sdf = new SimpleDateFormat ("mm/dd/yyyy hh:mm a" ,Locale.US);
         sdf.setLenient (false);
         try {
                 Date d1 = sdf.parse (strDate);
                 Date d2 = sdf.parse (eDate);
                 Date cur_date = new Date ();
                 //System.out.println (cur_date);
                 long diff = d2.getTime () - d1.getTime ();
                 long diff1 = cur_date.getTime () - d2.getTime ();
              if (diff < 0) {
                  throw new InvalidDateAndTimeException ("Invalid Dates , Start Date : "+strDate +" should be before end date : " + eDate);
              }
              else if (diff1 < 0) {
                  throw  new InvalidDateAndTimeException ("Invalid end date , end date " + eDate +" should be before today!!  " );
              }
             }
              catch (ParseException e) {
             throw new InvalidDateAndTimeException ("Invalid date ");
          }

    }

    /**
     *
     * @param date : The date of the phone call
     * @param time : The time of Phone call
     * @param ampm  : am/pm of the phone call
     * @return  : the time formatted using simple date format
     *
     */
    static Date convertToDate(String date , String time , String ampm){
          Date sDate = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat ("MM/dd/yyyy hh:mm aa");
            sdf.setLenient(false);
            sDate = sdf.parse (date+' '+time+' '+ampm);
        }catch (ParseException pe){
            throw  new InvalidDateAndTimeException ("Invalid date , Usage : mm/dd/yyyy hh:mm a \n" + date+' '+time+' '+ampm);
        }
      return sDate;
    }


}
