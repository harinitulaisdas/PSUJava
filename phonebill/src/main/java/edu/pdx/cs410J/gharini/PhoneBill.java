package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.ArrayList;
import java.util.Collection;


public class PhoneBill extends AbstractPhoneBill<PhoneCall>  {
    private Collection<PhoneCall> calls = new ArrayList<>();
    private String customer;

    public PhoneBill(String customer){
        this.customer = customer;
    }

    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall call) {
      this.calls.add (call);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {

        return this.calls;
    }

}

