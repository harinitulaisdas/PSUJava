//package edu.pdx.cs410J.gharini;
//import org.junit.Test;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Collection;
//import java.util.Date;
//
//import static edu.pdx.cs410J.gharini.PhoneBillServlet.CUSTOMER_NAME;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.not;
//import static org.hamcrest.Matchers.nullValue;
//import static org.mockito.Mockito.*;
//
///**
// * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
// * provide mock http requests and responses.
// */
//public class PhoneBillServletTest {
//
//    @Test
//    public void initiallyServletContainsNoPhoneBills() throws ServletException, IOException {
//        PhoneBillServlet servlet = new PhoneBillServlet();
//
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
//        PrintWriter mockPrintWriter = mock(PrintWriter.class);
//
//        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);
//
//        when(mockRequest.getParameter(CUSTOMER_NAME)).thenReturn("Customer");
//
//        servlet.doGet(mockRequest, mockResponse);
//
//        verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
//    }
//
//    @Test
//    public void addPhoneBill() throws ServletException, IOException {
//        PhoneBillServlet servlet = new PhoneBillServlet();
//
//        String customer = "Customer";
//        String caller = "123-456-8901";
//        String callee = "234-567-1234";
//        String startTime = "02/12/2017 3:00 am";
//        String endTime = "02/12/2017 3:30 am";
////        long startTime = System.currentTimeMillis();
////        long endTime = System.currentTimeMillis() + 100000L;
//
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        when(mockRequest.getParameter("customer")).thenReturn(customer);
//        when(mockRequest.getParameter("caller")).thenReturn(caller);
//        when(mockRequest.getParameter("callee")).thenReturn(callee);
//        when(mockRequest.getParameter("startTime")).thenReturn(startTime);
//        when(mockRequest.getParameter("endTime")).thenReturn(endTime);
//
//        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
//        PrintWriter mockPrintWriter = mock(PrintWriter.class);
//
//        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);
//
//        servlet.doPost(mockRequest, mockResponse);
//
//        verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
//
//        PhoneBill bill = servlet.getPhoneBill(customer);
//        assertThat(bill, not(nullValue()));
//        assertThat(bill.getCustomer(), equalTo(customer));
//
//        Collection<PhoneCall> calls = bill.getPhoneCalls();
//        assertThat(calls.size(), equalTo(1));
//
//        PhoneCall call = calls.iterator().next();
//        assertThat(call.getCaller(), equalTo(caller));
//        assertThat(call.getCallee(), equalTo(callee));
//        assertThat(call.getStartTime(), equalTo(new Date(startTime)));
//        assertThat(call.getEndTime(), equalTo(new Date(endTime)));
//    }
//
//    @Test
//    public void getReturnsPrettyPhoneBill() throws IOException, ServletException {
//        PhoneBillServlet servlet = new PhoneBillServlet();
//
//        String customer = "Customer";
//        PhoneBill bill = new PhoneBill(customer);
//        PhoneCall call = new PhoneCall("123-456-7890", "234-456-6789", new Date(), new Date());
//        bill.addPhoneCall(call);
//        servlet.addPhoneBill(bill);
//
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
//        PrintWriter mockPrintWriter = mock(PrintWriter.class);
//
//        when(mockResponse.getWriter()).thenReturn(mockPrintWriter);
//
//        when(mockRequest.getParameter(CUSTOMER_NAME)).thenReturn("Customer");
//
//        servlet.doGet(mockRequest, mockResponse);
//
//        verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
//        verify(mockPrintWriter).println(customer);
//        verify(mockPrintWriter).println(call.toString());
//    }
//
//}
////
////import org.junit.Test;
////
////import javax.servlet.ServletException;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////import java.io.IOException;
////import java.io.PrintWriter;
////
////import static org.hamcrest.MatcherAssert.assertThat;
////import static org.hamcrest.Matchers.equalTo;
////import static org.mockito.Mockito.*;
////
/////**
//// * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
//// * provide mock http requests and responses.
//// */
////public class PhoneBillServletTest {
////
////  @Test
////  public void initiallyServletContainsNoDictionaryEntries() throws ServletException, IOException {
////    PhoneBillServlet servlet = new PhoneBillServlet();
////
////    HttpServletRequest request = mock(HttpServletRequest.class);
////    HttpServletResponse response = mock(HttpServletResponse.class);
////    PrintWriter pw = mock(PrintWriter.class);
////
////    when(response.getWriter()).thenReturn(pw);
////
////    servlet.doGet(request, response);
////
////    int expectedWords = 0;
////    verify(pw).println(Messages.formatWordCount(expectedWords));
////    verify(response).setStatus(HttpServletResponse.SC_OK);
////  }
////
////  @Test
////  public void addOneWordToDictionary() throws ServletException, IOException {
////    PhoneBillServlet servlet = new PhoneBillServlet();
////
////    String word = "TEST WORD";
////    String definition = "TEST DEFINITION";
////
////    HttpServletRequest request = mock(HttpServletRequest.class);
////    when(request.getParameter("word")).thenReturn(word);
////    when(request.getParameter("definition")).thenReturn(definition);
////
////    HttpServletResponse response = mock(HttpServletResponse.class);
////    PrintWriter pw = mock(PrintWriter.class);
////
////    when(response.getWriter()).thenReturn(pw);
////
////    servlet.doPost(request, response);
////    verify(pw).println(Messages.definedWordAs(word, definition));
////    verify(response).setStatus(HttpServletResponse.SC_OK);
////
////    assertThat(servlet.getDefinition(word), equalTo(definition));
////  }
////
////}
