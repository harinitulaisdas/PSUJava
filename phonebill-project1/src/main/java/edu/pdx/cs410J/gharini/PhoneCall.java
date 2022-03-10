package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private String customer ;
  private String callerNumber ;
  private String calleeNumber ;
  private String startTime ;
  private String endTime ;
  private String startDate;
  private String endDate;

  public PhoneCall( String customer , String callerNumber , String calleeNumber , String startDate,
                   String startTime,String endDate,String endTime ) {

    this.customer = customer;
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startTime = startTime;
    this.endTime = endTime;
    this.startDate = startDate;
    this.endDate = endDate;
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
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return this.startDate+ " " +this.startTime;
  }
  @Override
  public String getEndTimeString() {
    return  this.endDate+" "+this.endTime;
  }

}
