package edu.pdx.cs410J.gharini;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bill REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all phone bill entries from the server
     * @param customerName
     */
    public String getPrettyPhoneBill(String customerName) throws IOException {
        Response response = get(this.url, "customer", customerName);
        String output=null;
        throwExceptionIfNotOkayHttpStatus(response);

        if (response.getContent ()==null){
            output = "The customer doesn't have a phone bill";
        }
        else
            output = response.getContent ();
        return output;
    }

    /***
     * Search for phone calls between the specified times.
     * @param customerName
     * @param startTime
     * @param endTime
     * @return  : the phone calls between specified time in pretty format.
     * @throws IOException
     */

    public String searchPhonebill(String customerName , String startTime , String endTime) throws IOException{
        Response response = get(this.url,"customer" , customerName,"startTime" , startTime ,"endTime",endTime);
        throwExceptionIfNotOkayHttpStatus(response);

        return response.getContent();
    }

    /***
     *  HTTP post request to post the phone call.
     * @param customerName
     * @param caller
     * @param callee
     * @param startTime
     * @param endTime
     * @throws IOException
     */

    public void addPhoneCall(String customerName, String caller , String callee , String startTime , String endTime) throws IOException {
        String[] postParameters = {
                "customer", customerName,
                "caller", caller,
                "callee", callee,
                "startTime", startTime,
                "endTime", endTime,
        };
        Response response = postToMyURL(postParameters);
        throwExceptionIfNotOkayHttpStatus(response);
    }


    @VisibleForTesting
    Response postToMyURL(String... phoneCallInformation) throws IOException {
      return post(this.url, phoneCallInformation);
    }

    /***
     *
     * @param response
     * @return
     */

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code == HTTP_NOT_FOUND) {
            String customer = response.getContent();
            throw new NoSuchPhoneBillException("Customer doesn't have phone calls in the phone bill for the specified time");

        } else if (code != HTTP_OK) {
            throw new PhoneBillRestException(code);
        }
        return response;
    }

    /***
     *
     */

    private class PhoneBillRestException extends RuntimeException {
      public PhoneBillRestException(int httpStatusCode) {
        super("Got an HTTP Status Code of " + httpStatusCode);
      }
    }

}
