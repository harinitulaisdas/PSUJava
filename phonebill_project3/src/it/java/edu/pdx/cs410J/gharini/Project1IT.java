//package edu.pdx.cs410J.gharini;
//
//import edu.pdx.cs410J.InvokeMainTestCase;
//import org.junit.Test;
//
//import static org.hamcrest.CoreMatchers.containsString;
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
///**
// * Tests the functionality in the {@link Project1} main class.
// */
//public class Project1IT extends InvokeMainTestCase {
//
//    /**
//     * Invokes the main method of {@link Project1} with the given arguments.
//     */
//    private MainMethodResult invokeMain(String... args) {
//        return invokeMain( Project1.class, args );
//    }
//
//  /**
//   * Tests that invoking the main method with no arguments issues an error
//   */
//  @Test
//  public void testNoCommandLineArguments() {
//    MainMethodResult result = invokeMain();
//    assertThat(result.getExitCode(), equalTo(-1));
//    assertThat(result.getTextWrittenToStandardError(), containsString("Missing Command Line Arguments"));
//  }
//
//    /**
//     * Tests that invoking the main method missing arguments except for the options issues an error
//     */
//    @Test
//    public void testFewCommandLineArgumentsMissing() {
//        MainMethodResult result = invokeMain("-README","-print", "123-456-7890","234-456-7890","12/12/2009", "12:12" ,"12/12/2009", "12:20" );
//        assertThat(result.getExitCode(), equalTo(-1));
//        assertThat(result.getTextWrittenToStandardError(), containsString("Missing few Command Line Arguments"));
//    }
//    /**
//     * Tests that invoking the main method with extra arguments issues an error
//     */
//   @Test
//    public void testTooManyCommandLineArguments() {
//        MainMethodResult result = invokeMain("-README","-print","MyName","123-456-7890","234-456-7890","12/12/2009", "12:12" ,"12/12/2009", "12:20" , "extra args","extra args","extra args","extra args");
//        assertThat(result.getExitCode(), equalTo(-1));
//        assertThat(result.getTextWrittenToStandardError(), containsString("Command Line has too many arguments"));
//    }
//    /**
//     * Tests that invoking the main method with the options that are not in specified format (case sensitive)would result in an error
//     */
//
//    @Test
//    public void testOptionsAreCaseSensitive(){
//        MainMethodResult result = invokeMain ("-REaDmE","-print","MyName","123-456-7890","234-436-7890","12/12/2009", "10:00" ,"12/12/2009", "12:20");
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Options are case sensitive , Usage : -README , -print , -textFile :"));
//        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
//    }
//
//    /**
//     * Tests that invoking the main method without any options would not result in error
//     */
//    @Test
//    public void testProgramRunsWithoutOptions(){
//        MainMethodResult result = invokeMain ("MyName","123-456-7890","234-436-7890","12/12/2009","10:00" ,"12/12/2009", "12:20");
//        assertThat (result.getExitCode (),equalTo (1));
//        assertThat (result.getTextWrittenToStandardError (), equalTo (""));
//        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
//
//    }
//
//
//    /**
//     * Tests that invoking the main method with the phone number with wrong format issues an error
//     */
//    @Test
//    public void testValidPhoneNumberFormat1(){
//      MainMethodResult result = invokeMain ("-README","-print","MyName","123/456/7890","234-456-7890","12/12/2009", "12:12" ,"12/12/2009", "12:20");
//      assertThat (result.getExitCode (),equalTo (-1));
//      assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Phone Number Format , Usage : nnn-nnn-nnnn"));
//    }
//
//    /**
//     * Tests that invoking the main method with the phone number with wrong format(extra characters) issues an error
//     */
//    @Test
//    public void testValidPhoneNumberFormat2(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "2334-4536-7890";
//        String startDate = "12/12/2009" ;
//        String startTime = "21:12";
//        String endDate = "12/12/2009";
//        String endTime = "22:20";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Phone Number Format , Usage : nnn-nnn-nnnn"));
//    }
//
//    @Test
//    public void testValidPhoneNumberFormat3(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = " ABC-123-4567";
//        String startDate = "12/12/2009" ;
//        String startTime = "21:12";
//        String endDate = "12/12/2009";
//        String endTime = "22:20";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Phone Number Format , Usage : nnn-nnn-nnnn  : "));
//    }
//
//
//    /**
//     * Tests that invoking the main method with the caller and callee having same phone number results in an error
//     */
//    @Test
//    public void testCallerAndCalleeHasToBeDifferent(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "334-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/12/2009" ;
//        String startTime = "21:12";
//        String endDate = "12/12/2009";
//        String endTime = "22:20";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Caller and Callee should be different  :"));
//    }
//
//    /**
//     * Tests that start data/end date of phone call in format other than yyyy/MM/dd HH:mm would result in error
//     */
//    @Test
//    public  void testValidDateFormat1(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12-22-2009";
//        String startTime = "21:12";
//        String endDate = "12/22/2009";
//        String endTime = "22:20";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Date Format , Usage : MM/dd/yyyy"));
//        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
//    }
//
//    /**
//     * Tests that start data/end date of phone call in format other than yyyy/MM/dd HH:mm would result in error
//     */
//    @Test
//    public  void testValidDateFormat2(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/22/2009" ;
//        String startTime = "21:12";
//        String endDate = "12/22/2009";
//        String endTime = "22:2v0";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid time format , Usage HH:mm :"));
//        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
//    }
//
//    /**
//     * Tests that start time/end time of phone call in format other than HH:mm would result in error
//     */
//    @Test
//    public  void testValidTimeFormat(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/22/2009" ;
//        String startTime = "21:12";
//        String endDate = "12/22/2009";
//        String endTime = "24:20";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid time format , Usage HH:mm :"));
//        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
//    }
//
//    /**
//     * Tests that start data of the phone call is before end date
//     */
//    @Test
//    public  void testStartDateISBeforeEndDate(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/23/2009";
//        String startTime = "21:12";
//        String endDate = "12/22/2009";
//        String endTime = "22:00";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Dates , Start Date should be before end date  "));
//        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
//    }
//
//    /**
//     * Tests that end data of the phone call is not after today
//     */
//
//    @Test
//    public  void testEndDateIsBeforeToday(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/22/2009" ;
//        String startTime = "21:12";
//        String endDate = "12/22/2019";
//        String endTime = "22:20";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid end date , end date should be before today!!  "));
//        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
//    }
//
//    /**
//     * Tests that the start and end times of phone call have 24hr format
//     */
//    @Test
//    public  void testStartAndEndTimeHave24HrFormat(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/22/2009" ;
//        String startTime = "21:12";
//        String endDate = "12/22/2009";
//        String endTime = "22:20";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (1));
//        assertThat (result.getTextWrittenToStandardError (),equalTo (""));
//        assertThat(result.getTextWrittenToStandardOut(), containsString ("Phone call from"));
//    }
//
//
//    /**
//     * Tests that the start and end times of phone call can be same and 00:00 is a valid time
//     */
//    @Test
//    public  void testStartAndEndTimesCanBeEqual(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/22/2009";
//        String endDate = "12/22/2009";
//        String startTime = "00:00";
//        String endTime = "00:00";
//
//        MainMethodResult result = invokeMain (option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (1));
//        assertThat (result.getTextWrittenToStandardError (),equalTo (""));
//        assertThat(result.getTextWrittenToStandardOut(), containsString ("Phone call from"));
//    }
//
//
//    /**
//     * Tests that the command line arguments except for options cannot start with a -
//     */
//
//    @Test
//    public  void testArgumentFormat(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "-myName";
//        String caller = "-123-456-7890";
//        String callee = "-334-456-7890";
//        String startDate = "12/22/2009";
//        String endDate = "12/22/2009";
//        String startTime = "00:00";
//        String endTime = "00:00";
//
//        MainMethodResult result = invokeMain (option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        //MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,endDate);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Argument : Argument cannot start with a -  :"));
//
//    }
//
//    /**
//     * Tests that the command line arguments except for options cannot start with a -
//     */
//    @Test
//    public void testPrintOption(){
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/22/2009";
//        String endDate = "12/22/2009";
//        String startTime = "00:00";
//        String endTime = "00:00";
//
//        MainMethodResult result = invokeMain (option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (1));
//        String output = "Phone call from " + caller + " to " + callee + " from " + startDate + " "+ startTime + " "+"to " + endDate + " " + endTime;
//        assertThat (result.getTextWrittenToStandardOut (),containsString (output+"\n"));
//
//
//    }
//
//    /**
//     * Tests that readme option in correct format works as expected.
//     */
//    @Test
//    public void testDashReadme(){
//        String option1 = "-README";
//        MainMethodResult result = invokeMain (option1);
//        assertThat (result.getExitCode (),equalTo (1));
//        assertThat (result.getTextWrittenToStandardOut (), containsString ("Course    : Advanced Java Programming."));
//    }
//
//    /**
//     * Tests that readme option works when it is specified in the proper case.
//     */
//    @Test
//    public void testDashReadmeForCaseSensitive(){
//        String option1 = "-ReaDME";
//        MainMethodResult result = invokeMain (option1);
//        assertThat (result.getExitCode (),equalTo (-1));
//        assertThat (result.getTextWrittenToStandardError (), containsString ("Options are case sensitive , Usage : -README , -print , -textFile :"));
//    }
//
//
//    /**
//     * Tests that program works as expected when all the arguments and options are passed in
//     */
//    @Test
//    public void testAllOptionsAndArgumentsPrintsReadmeAndPhonecallDetails(){
//        String option1 = "-README";
//        String option2 = "-print";
//        String name = "myName";
//        String caller = "123-456-7890";
//        String callee = "334-456-7890";
//        String startDate = "12/22/2009";
//        String startTime = "00:00";
//        String endTime = "00:00";
//        String endDate = "12/22/2009";
//        MainMethodResult result = invokeMain (option1,option2,name,caller,callee,startDate,startTime,endDate,endTime);
//        assertThat (result.getExitCode (),equalTo (1));
//        assertThat (result.getTextWrittenToStandardError (), equalTo (""));
//        assertThat (result.getTextWrittenToStandardOut (),containsString ("Phone call from"));
//        assertThat (result.getTextWrittenToStandardOut (),containsString ("Course    : Advanced Java Programming."));
//    }
//
//}
//
