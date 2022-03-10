package edu.pdx.cs410J.gharini.server;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.gharini.client.PhoneBill;
import edu.pdx.cs410J.gharini.client.PhoneCall;
import edu.pdx.cs410J.gharini.client.PhoneBillService;
import edu.pdx.cs410J.gharini.client.PhoneCallHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{

    private final Map<String, PhoneBill> phoneBillHashMap = new HashMap<> ();

    /***
     * Service implementation of get phonebill method
     * @param customer
     * @return
     * @throws IllegalStateException
     */
  @Override
  public PhoneBill getPhoneBill(String customer) throws IllegalStateException{


      PhoneBill phoneBill = getPhoneBillForCustomer (customer);
      if(phoneBill==null){
          throw  new IllegalStateException ("Phonebill is empty");
      } else if(!phoneBill.getCustomer ().equals (customer)){

          throw new IllegalArgumentException ("Customer " + customer + " doesnt have any phonebill");
      }

    return phoneBill;
  }


    public PhoneBill getPhoneBillForCustomer(String customer) {
        return this.phoneBillHashMap.get(customer);
    }

    public void addPhoneBill(PhoneBill bill) {
        this.phoneBillHashMap.put(bill.getCustomer(), bill);
    }

    /***
     *Service implementation of searchPhoneBill method
     * @param customer
     * @param startDate
     * @param endDate
     * @return
     * @throws IllegalStateException
     */
    @Override
    public PhoneBill searchPhoneBill(String customer  , Date startDate , Date endDate) throws IllegalStateException{

        PhoneBill bill = new PhoneBill(customer);
        PhoneBill phoneBill = getPhoneBillForCustomer (customer);
        if(phoneBill==null){
            throw  new IllegalStateException ("Phonebill is empty");
        } else if(!phoneBill.getCustomer ().equals (customer)){

            throw new IllegalArgumentException ("Customer " + customer + " doesnt have any phonebill");
        }
         else{
            for(PhoneCall call : phoneBill.getPhoneCalls ()){
                if((call.getStartTime ().equals (startDate) || call.getStartTime ().after (startDate)) &&
                        (call.getEndTime ().equals (endDate) || call.getEndTime ().before (endDate))){

                    bill.addPhoneCall (call);

                }
            }
        }

        return  bill;

    }

    /***
     * Service implementation of add phone call method
     * @param customer
     * @param call
     */

    @Override
    public void addPhoneCall(String customer , PhoneCall call){

        PhoneBill phoneBill;
        if(this.phoneBillHashMap.containsKey (customer)) {
            phoneBill = getPhoneBillForCustomer (customer);
        }else {
            phoneBill = new PhoneBill(customer);
            addPhoneBill(phoneBill);
        }
      if(phoneBill == null){
           phoneBill = new PhoneBill (customer); }else
      if(!phoneBill.getCustomer ().equals (customer))  {
          throw new IllegalArgumentException ("Customer " + customer + " doesnt have any phonebill");
      }
       phoneBill.addPhoneCall (call);
    }

    @Override
    public String getReadMe() throws IllegalStateException {
        return null;
    }


    @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

}
