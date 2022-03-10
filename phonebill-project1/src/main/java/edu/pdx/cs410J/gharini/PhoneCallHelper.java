package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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
        readme += "Project   : Project1\nObjective :\n";
        readme += "\t\tThe project 1 to implement a simple Phone Call Details generation has two classes phoneCall and phoneBill that represents the details of a phone call and phone bill respectively. " +
                "The phoneCall class has fields to hold the details of a phone call such as  customer name , caller (Phone number) , callee(phone number) ,  the start and end time of the call. " +
                "The phone bill has a collection of phone calls and the name of the customer \n" +
                "\n" +
                "\t\tThe application is invoked in the Project1 class. The Project1 class parses the command line arguments for the input." +
                " The command line arguments has the format [options] <args>. The [options] has two choices, -readme and -print. The other arguments are phone numbers of the caller and callee and " +
                " the start and end times of the phone call. The command line can have either one of the 2 options or both options and no arguments or all arguments for " +
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

    static void checkNumOfArgs(int numOfNonOptions, int numOfArgs , String [] args) {
        if (numOfArgs == 0) {
            throw new InvalidNumberOfArgumentsException ("Missing Command Line Arguments " );
        }
        else if (numOfNonOptions > 0 && numOfNonOptions < 7) {
            throw new InvalidNumberOfArgumentsException ("Missing few Command Line Arguments ");
        }
        else if (numOfNonOptions > 7) {
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
        ArrayList<ArrayList<String>> list = new ArrayList<> ();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i].toLowerCase ();
            if ((arg.contains ("readme") || arg.contains ("print") || arg.contains ("textfile")) && !arg.startsWith ("-")) {
                throw new InvalidOptionException ("Invalid Option , Usage : -README -print -textFile :" + arg);
            }
            else if (arg.equals ("-readme") || arg.equals ("-print") || arg.equals ("-textfile")) {
                if (args[i].matches ("-README") || args[i].matches ("-print") || args[i].matches ("-textFile")) {
                    options.add (args[i]);
                }
                else {
                    throw new InvalidOptionException ("Options are case sensitive , Usage : -README , -print , -textFile :" + args[i]);
                }

            }
            else {
                if(args[i].startsWith ("-")) {
                    throw new InvalidOptionException ("Invalid Option : " + args[i]);

                }else{
                   nonOptions.add (args[i]) ;
                }
            }
        }

        for (String opt : options) {
//            if(!opt.equals ("-README") || !opt.equals ("-print") || !opt.equals ("-textFile")){
//                throw new InvalidOptionException ("Invalid Option :" +  opt);
//            }
            if (opt.equals ("-textFile")) {
                checkFileOption (nonOptions);
            }

        }
        list.add (options);
        list.add (nonOptions);
        return list;
    }

    /**
     * @param nonOptions : The non option arguments from the command line.
     *                   This method checks that the the -textFile is followed by the filename/filepath
     */
    static void checkFileOption(ArrayList<String> nonOptions) {
        if (!nonOptions.get (0).endsWith (".txt")) {
            throw new InvalidOptionException ("The option -textFile should be followed by the file name/path ,Usage : -textFile filename.txt : " + nonOptions.get (0));
        }
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
        if (arg.startsWith ("-")) {
            throw new InvalidArgumentFormatException ("Invalid Argument : Argument cannot start with a '-' (Optional arguments are -README -print) :" + arg);
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
     * @param arg : the date variable as string
     * @throws InvalidDateAndTimeException This method checks if the Start time and end time is in the valid format. The valid format for time
     *                                     is " HH:mm" . If the times are in any format other than the specified one , an exception is thrown with the appropriate message.
     */

     static void checkTimeFormat(String arg) {
        SimpleDateFormat sdf = new SimpleDateFormat ("HH:mm", Locale.US);
        if (!arg.matches ("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
            throw new InvalidDateAndTimeException ("Invalid time format , Usage HH:mm :" + arg);

        try {
            Date date = sdf.parse (arg);

        } catch (DateTimeParseException dpe) {
            throw new InvalidDateAndTimeException ("Invalid time , Usage : HH:mm " + arg);
        } catch (ParseException e) {
            throw new InvalidDateAndTimeException ("Invalid time , Usage : HH:mm " + arg);
        } catch (Exception e) {
            throw new InvalidDateAndTimeException ("Invalid time , Usage : HH:mm " + arg);
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
      static void checkDateDifference(String startDate ,String startTime,String endDate, String endTime){

         String strDate = startDate + " " + startTime;
         String eDate = endDate + " " + endTime;

        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("MM/dd/yyyy HH:mm" , Locale.US);
        LocalDateTime startDateTime = LocalDateTime.parse (strDate , dateTimeFormat );
        LocalDateTime endDateTime = LocalDateTime.parse (eDate,dateTimeFormat);
        LocalDateTime currDate = LocalDateTime.now ();
        long diff = ChronoUnit.SECONDS.between (startDateTime ,endDateTime);
        long diff1 = ChronoUnit.SECONDS.between (endDateTime , currDate);

        if(diff<0){
            throw new InvalidDateAndTimeException ("Invalid Dates , Start Date should be before end date  " + strDate + " " + eDate);
        } else if(diff1<0){
            throw  new InvalidDateAndTimeException ("Invalid end date , end date should be before today!!  " + eDate);
        }
    }

}
