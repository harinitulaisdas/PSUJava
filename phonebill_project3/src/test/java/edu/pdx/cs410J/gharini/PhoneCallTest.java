//package edu.pdx.cs410J.gharini;
//
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//
///**
// * Unit tests for the {@link PhoneCall} class.
// */
//public class PhoneCallTest {
//  @Ignore
//  @Test(expected = UnsupportedOperationException.class)
//  public void getStartTimeStringNeedsToBeImplemented() {
//    PhoneCall call = new PhoneCall("Harini" ,"123-345-4565","433-455-6789 ","12/12/2009", "20:10" ,"12/12/2009", "20:20" );
//    call.getStartTimeString ();
//  }
//    /**
//     * This tests the initially all the phone calls have the same callee.
//     */
//
//  @Test
//  public void initiallyAllPhoneCallsHaveTheSameCallee() {
//    PhoneCall call = new PhoneCall("Harini" ,"123-345-4565","433-455-6789","12/12/2009"," 20:10" ,"12/12/2009"," 20:20" );
//    assertThat(call.getCallee(), containsString(call.getCallee ()));
//  }
//    /**
//     * This tests if the getStartTime() can return null.
//     */
//
//  @Test
//  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
//    PhoneCall call = new PhoneCall("Harini" ,"123-345-4565","433-455-6789 ","null" ,"null" ,"12/12/2009"," 20:20" );
//    String out = "null null";
//    assertThat(call.getStartTime(), is(nullValue ()));
//  }
//    /**
//     * This tests if the getEndTime() can return null.
//     */
//  @Test
//  public void forProject1ItIsOkayIfGetEndTimeResultsNull(){
//      String out = "null null";
//      PhoneCall call = new PhoneCall ("Harini" ,"123-345-4565","433-455-6789 ","12/12/2009"," 20:20" ,"null","null");
//      assertThat (call.getEndTimeString () , is(out));
//  }
//
//    /**
//     * This tests if the getCaller() returns the phone number of the caller.
//     */
//  @Test
//  public void  testGetCaller(){
//      String callerPhoneNumber = "123-456-7890";
//      PhoneCall call = new PhoneCall ("harini","123-456-7890",null ,null,null,null,null);
//      assertThat (call.getCaller (),containsString (callerPhoneNumber));
//
//  }
//
//    /**
//     * This tests if the getCallee() returns the phone number of the callee.
//     */
//  @Test
//  public void testGetCallee(){
//      String calleePhoneNumber = "123-456-7890";
//      PhoneCall call = new PhoneCall ("harini",null,"123-456-7890",null,null,null,null);
//      assertThat (call.getCallee (),containsString (calleePhoneNumber));
//
//    }
//
//    /**
//     * This tests if the getStartTime() returns the start time of the phone call.
//     */
//
//   @Test
//   public void testGetStartTime(){
//      String startTime = "20:20";
//      PhoneCall call = new PhoneCall ("harini",null,"123-456-7890", "12/12/2009"," 20:20",null,null);
//      assertThat (call.getStartTimeString (),containsString (startTime));
//   }
//    /**
//     * This tests if the getEndTime() returns the end time of the phone call.
//     */
//    @Test
//    public void testGetEndTime(){
//        String endTime = "20:30";
//        PhoneCall call = new PhoneCall ("harini",null,"123-456-7890", "12/12/2009"," 20:20","12/12/2009"," 20:30");
//        assertThat (call.getEndTimeString (),containsString (endTime));
//    }
//}
