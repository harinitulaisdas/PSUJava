package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class implements phonebill and dumps the pretty content on to a file/ standard output as requested
 * on the command line argument.
 */
public class PrettyPrinter implements PhoneBillDumper {
    private File file ;
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
        this.file = new File(path);
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
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        FileHelper.checkIfFileExistsAndCreateIfOtherwise (this.file);
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{
            fw = new FileWriter(this.file,false);
            bw = new BufferedWriter(fw);
            bw.write(FileHelper.getPrettyContent (this.bill));

        }catch (IOException e){
            e.printStackTrace ();
        } finally {
            bw.close();
            fw.close();
        }

    }

    /**
     *
     * @param bill
     * This method writes the pretty content on to the standard output.
     */
    public void dumpPrettyContentToStandardOut(AbstractPhoneBill bill){
        System.out.println (FileHelper.getPrettyContent ((PhoneBill)bill));

    }


}
