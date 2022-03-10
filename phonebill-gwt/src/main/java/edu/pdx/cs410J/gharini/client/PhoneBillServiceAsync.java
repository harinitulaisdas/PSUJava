package edu.pdx.cs410J.gharini.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Date;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {


  /**
   * Returns the a dummy Phone Bill
   */
  void getPhoneBill(String customer, AsyncCallback<PhoneBill> async);

  /**
   * Search for phone calls in the phone bill
   * @param customer
   * @param startDate
   * @param endDate
   * @param async
   */
  void searchPhoneBill(String customer, Date startDate, Date endDate, AsyncCallback<PhoneBill> async);

  /***
   * add phone calls to the phone bill
   * @param customer
   * @param call
   * @param async
   */

  void addPhoneCall(String customer, PhoneCall call, AsyncCallback<Void> async);

  void getReadMe(AsyncCallback<String> async);

  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void throwUndeclaredException(AsyncCallback<Void> async);

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException(AsyncCallback<Void> async);
}
