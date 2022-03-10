package edu.pdx.cs410J.gharini;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneBill } class.
 */
public class PhoneBillTest {
    /**
     * This tests if the getCustomer() returns the name of the customer
     */
    @Test
    public void testGetCustomer(){
        String customer = "Harry";
        PhoneBill bill = new PhoneBill(customer);
        assertThat (bill.getCustomer (),containsString (customer));
    }

    /**
     * This tests if the getPhoneCall() gives the phone call details.
     */
    @Test
    public void testGetCalls(){
        String option1 = "-README";
        String option2 = "-print";
        String name = "myName";
        String caller = "123-456-7890";
        String callee = "234-4536-7890";
        String startDate = "2009/12/12";
        String startTime = "12:12";
        String endDate = "2009/12/12";
        String endTime = "12:22";
        PhoneCall call = new PhoneCall (name,caller,callee,startDate, startTime,endDate,endTime);
        PhoneBill bill = new PhoneBill (name);
        bill.addPhoneCall (call);
        assertThat (bill.getPhoneCalls ().toString (),containsString (call.toString ()));
    }
}
