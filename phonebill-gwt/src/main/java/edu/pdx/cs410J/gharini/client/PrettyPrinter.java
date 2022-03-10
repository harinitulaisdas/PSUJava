package edu.pdx.cs410J.gharini.client;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import edu.pdx.cs410J.gharini.client.FileHelper;
import java.io.IOException;

/**
 * This class implements phonebill and dumps the pretty content on to a file/ standard output as requested
 * on the command line argument.
 */
public class PrettyPrinter {

    private String customer;
    private PhoneBill bill;

    /**
     *
     * @param path : the path to the pretty file
     * @param bill : the phone bill(calls read from the file)
     * @param customer : the customer of the phone bill.
     */
    public PrettyPrinter(String path , PhoneBill bill , String customer){
        this.bill= bill;

        this.customer = customer;
    }

    /**
     *
     * @param bill : the phone bill with phone calls
     * @param customer : the customer of the phone bill.
     */
    public PrettyPrinter(PhoneBill bill , String customer){
        this.bill= bill;
        this.customer = customer;
    }

    /**
     *
     * @param bill : the phone bill that is written on to the file in pretty format
     * @throws IOException
     */


    /**
     *
     * @param bill
     * This method writes the pretty content on to the standard output.
     */
    public String dumpPrettyContentToStandardOut(AbstractPhoneBill bill){
        //System.out.println (FileHelper.getPrettyContent ((PhoneBill)bill));
        return FileHelper.getPrettyContent ((PhoneBill)bill);

    }


}
