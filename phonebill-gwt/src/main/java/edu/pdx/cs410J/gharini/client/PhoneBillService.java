package edu.pdx.cs410J.gharini.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Date;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

  /**
   * Returns the a dummy Phone Bill
   */
  PhoneBill getPhoneBill(String customer) throws IllegalStateException;

  PhoneBill searchPhoneBill(String customer  , Date startDate , Date endDate) throws IllegalStateException;


  void addPhoneCall(String customer , PhoneCall call) throws IllegalStateException;

  String getReadMe() throws IllegalStateException;

  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void throwUndeclaredException();

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException() throws IllegalStateException;



}
