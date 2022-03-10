package edu.pdx.cs410J.gharini.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractPhoneCall;
import java.lang.Override;
import java.util.Date;


/**
 * The phone call class . It holds all the details of the phone call.
 */

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
  /**
   * customer : customer of the phone call.
   */
  private String customer = null;
  /**
   * caller number : The phone number of the caller
   */
  private String callerNumber ;
  /**
   * caller number : The phone number of the callee
   */
  private String calleeNumber ;
  /**
   * startTime : The start date and time of the phone call
   */
  private Date startTime ;
  /**
   * endTime : The end date and time of the phone call
   */
  private Date endTime ;

  public PhoneCall(String callerNumber , String calleeNumber,Date startTime,
                   Date endTime ) {


    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public PhoneCall() {
  }

  @Override
  public String getCaller() {
    return this.callerNumber;
  }

  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  @Override
  public String getStartTimeString() {
    return shortFormat (getStartTime ());
  }
  @Override
  public String getEndTimeString() {
    return shortFormat (getEndTime());
  }

  @Override
  public Date getStartTime() {
    return this.startTime;
  }

  @Override
  public Date getEndTime() {
    return this.endTime;
  }

  private static  String shortFormat(Date dateTime){
    if(dateTime!=null)  {
      DateTimeFormat dateFormat = DateTimeFormat.getFormat (DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT);
      return dateFormat.format (dateTime);
    }
    else{
      return null;
    }

  }

  @Override
  public int compareTo(PhoneCall o) {
    if(this.getStartTime ().after (o.getStartTime ())){
      return 1;
    }
    else if(this.getStartTime ().before (o.getStartTime ())){
      return -1;
    }

    else if(this.getStartTime ().equals (o.getStartTime ())){

      return (this.getCaller ().compareTo (o.getCaller ()));

    }
    return 0;
  }
}
