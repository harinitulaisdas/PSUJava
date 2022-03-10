package edu.pdx.cs410J.gharini;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.web.HttpRequestHelper;
import groovy.json.internal.IO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  It
 */
public class PhoneBillServlet extends HttpServlet
{
    static final String CUSTOMER_NAME="customer";
    static final String CALLER_NUMBER="caller";
    static final String CALLEE_NUMBER="callee";
    static final String START_TIME = "startTime";
    static final String END_TIME = "endTime";

    private final Map<String, PhoneBill> phoneBillHashMap = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the phone bill for the customer
     * HTTP parameter to the HTTP response. The search for phone calls between specified times is also handled here
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );
        if(response.getStatus ()!=200){
            response.sendError (response.getStatus () , "Server Unavailable " + response.getStatus () );
            return;
        }

        PrintWriter pw = response.getWriter ();

        PhoneBill phonebill = null;
        Date startD = new Date ();
        Date endD = new Date ();
        String name = null;
        List<String> params = Collections.list(request.getParameterNames ());
        name = getParameter( CUSTOMER_NAME, request );
        if(name == null){
            response.sendError (HttpServletResponse.SC_PRECONDITION_FAILED , "Please Enter Customer Name");
            return;
        }
        if(params.size ()==1){

            phonebill = getPhoneBill (name);
            if(phonebill==null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                pw.println("The Customer doesn't have a phone bill");
                pw.flush ();
                return;
            }else
            {
                response.setStatus (HttpServletResponse.SC_OK);
               pw.println (FileHelper.getPrettyContent (phonebill));
               pw.flush ();
            }
        }

        if(params.contains (START_TIME) && params.contains (END_TIME)){
            String startTime = getParameter (START_TIME , request);
            String endTime = getParameter (END_TIME , request);

            if(startTime == null || startTime.equals ("")){
                response.sendError (HttpServletResponse.SC_PRECONDITION_FAILED,"Please Enter start time");
                return;
            }
            if(endTime == null || endTime.equals ("")){
                response.sendError (HttpServletResponse.SC_PRECONDITION_FAILED , "Please Enter End Time");
                return;
            }

            try{
                PhoneCallHelper.checkValidArgumentFormat (startTime.trim ());
                PhoneCallHelper.checkValidArgumentFormat (endTime.trim ());
                String [] start_time = startTime.split (" ");
                String [] end_time = endTime.split (" ");
                if(start_time.length < 3 || end_time.length < 3){
                    response.sendError (HttpServletResponse.SC_PRECONDITION_FAILED , " Invalid Date and Time. Please enter valid start/end times");
                    throw new InvalidDateAndTimeException ("Invalid Date and Time. Please enter valid start/end times");
                }
                PhoneCallHelper.checkDateFormat (start_time[0].trim ());
                PhoneCallHelper.checkTimeFormat (start_time[1].trim (),start_time[2].trim ());
                PhoneCallHelper.checkDateFormat (end_time[0].trim ());
                PhoneCallHelper.checkTimeFormat (end_time[1].trim (),end_time[2].trim ());
                PhoneCallHelper.checkDateDifference (start_time[0].trim () , start_time[1].trim ()+" "+start_time[2].trim (),
                        end_time[0].trim () , end_time[1].trim ()+" "+end_time[2].trim () );

                startD = PhoneCallHelper.convertToDate (start_time[0].trim () ,start_time[1].trim () ,start_time[2].trim ());
                endD = PhoneCallHelper.convertToDate (end_time[0].trim () , end_time[1].trim (), end_time[2].trim ());
                phonebill = getPhoneBill(name);
                if(phonebill==null){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    pw.println("The Customer doesn't have a phone bill");
                    pw.flush ();
                    return;
                }
                if(phonebill.getPhoneCalls () !=null) {
                    Collection<PhoneCall> calls = phonebill.getPhoneCalls ();
                    PhoneBill billSearchResult = new PhoneBill (name);
                    for (PhoneCall call : calls) {
                        if ((call.getStartTime ().equals (startD) || call.getStartTime ().after (startD)) &&
                                (call.getEndTime ().equals (endD) || call.getEndTime ().before (endD))) {
                            billSearchResult.addPhoneCall (call);
                        }
                    }
                    if(billSearchResult.getPhoneCalls ().size () == 0){
                        response.setStatus (HttpServletResponse.SC_NOT_FOUND);
                        pw.println ("No Phone Calls between the times " + startTime + " " + endTime);
                        pw.flush ();
                    }
                    pw.println (FileHelper.getPrettyContent (billSearchResult));
                    pw.flush ();
                }
               else{
                   response.sendError (HttpServletResponse.SC_NOT_FOUND , "No Phone Calls in the Phone bill");
               }

            } catch (InvalidDateAndTimeException | InvalidArgumentFormatException e){

                response.sendError (HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage ());
            }
        }
        response.setStatus (HttpServletResponse.SC_OK);
    }
    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );
        Date START_TIME_DATE = new Date () ;
        Date END_TIME_DATE = new Date ();
        PrintWriter pw = response.getWriter ();
        String name = getParameter( CUSTOMER_NAME, request );
        if (name == null || name.equals ("")) {
            missingRequiredParameter(response, CUSTOMER_NAME);
            return;
        }
        String callerNumber = getParameter (CALLER_NUMBER ,request);
        if (callerNumber == null || callerNumber.equals ("")) {
            missingRequiredParameter(response, CALLER_NUMBER);
            return;
        }
        String calleeNumber = getParameter (CALLEE_NUMBER , request);
        if (calleeNumber == null || calleeNumber.equals ("")) {
            missingRequiredParameter(response, CALLEE_NUMBER);
            return;
        }
        String startTime = getParameter (START_TIME , request);
        if (startTime == null || startTime.equals ("")) {
            missingRequiredParameter(response, START_TIME);
            return;
        }
        String endTime = getParameter (END_TIME , request);
        if (endTime == null || endTime.equals ("")) {
            missingRequiredParameter(response, END_TIME);
            return;
        }
        PhoneBill phoneBill;
        if(this.phoneBillHashMap.containsKey (name)) {
             phoneBill = getPhoneBill(name);
        }else {
             phoneBill = new PhoneBill(name);
            addPhoneBill(phoneBill);
        }

        try{
            PhoneCallHelper.checkValidArgumentFormat (name);
            PhoneCallHelper.checkValidArgumentFormat (callerNumber);
            PhoneCallHelper.checkValidArgumentFormat (calleeNumber);
            PhoneCallHelper.checkPhoneNumberFormat (calleeNumber);
            PhoneCallHelper.checkCallerAndCallee (callerNumber,calleeNumber);
            PhoneCallHelper.checkValidArgumentFormat (startTime.trim ());
            PhoneCallHelper.checkValidArgumentFormat (endTime.trim ());
            String [] start_time = startTime.split (" ");
            String [] end_time = endTime.split (" ");
            if(start_time.length < 3 || end_time.length < 3){
                response.sendError (HttpServletResponse.SC_PRECONDITION_FAILED , " Invalid Date and Time. Please enter valid start/end times");
            }
            PhoneCallHelper.checkDateFormat (start_time[0].trim ());
            PhoneCallHelper.checkTimeFormat (start_time[1].trim (),start_time[2].trim ());
            PhoneCallHelper.checkDateFormat (end_time[0].trim ());
            PhoneCallHelper.checkTimeFormat (end_time[1].trim (),end_time[2].trim ());
            PhoneCallHelper.checkDateDifference (start_time[0].trim () , start_time[1].trim ()+" "+start_time[2].trim (),
                    end_time[0].trim () , end_time[1].trim ()+" "+end_time[2].trim () );

            START_TIME_DATE = PhoneCallHelper.convertToDate (start_time[0].trim () ,start_time[1].trim () ,start_time[2].trim ());
            END_TIME_DATE = PhoneCallHelper.convertToDate (end_time[0].trim () , end_time[1].trim (), end_time[2].trim ());
            PhoneCall phonecall = new PhoneCall (callerNumber,calleeNumber,START_TIME_DATE,END_TIME_DATE);
            phoneBill.addPhoneCall (phonecall);
            pw.println ("Phone call created and added to the phone bill\n");
            pw.println (phonecall);
            pw.flush ();
            response.setStatus( HttpServletResponse.SC_OK);

        } catch (InvalidArgumentFormatException|InvalidNumberOfArgumentsException|InvalidPhoneNumberException
                |InvalidDateAndTimeException|SameCallerAndCalleeException| InvalidOptionException ex) {
            response.sendError (HttpServletResponse.SC_PRECONDITION_FAILED , ex.getMessage ());

        }
        catch(Exception e){
            response.sendError (HttpServletResponse.SC_PRECONDITION_FAILED , e.getMessage ());

        }
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = String.format("The required parameter \"%s\" is missing", parameterName);;
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     *
     * @param customer
     * @return
     */

  public PhoneBill getPhoneBill(String customer) {
        return this.phoneBillHashMap.get(customer);
    }

    /**
     *
     * @param bill
     */
   public void addPhoneBill(PhoneBill bill) {
        this.phoneBillHashMap.put(bill.getCustomer(), bill);
    }

}
