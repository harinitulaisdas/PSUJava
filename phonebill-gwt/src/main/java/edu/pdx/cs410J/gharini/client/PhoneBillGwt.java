package edu.pdx.cs410J.gharini.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import java.util.Date;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
/***
 * @author : Harini Gowdagere Tulasidas 
 */

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
  private final Alerter alerter;
  private final PhoneBillServiceAsync phoneBillService;
  private final Logger logger;


  public PhoneBillGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  PhoneBillGwt(Alerter alerter) {
    this.alerter = alerter;
    this.phoneBillService = GWT.create(PhoneBillService.class);
    this.logger = Logger.getLogger("phoneBill");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  /***
   *
   * @param throwable
   */

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  /***
   *
   * @param throwable
   * @return
   */
  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }

    }

    return throwable;
  }

  /***
   *
   * @return widget
   *
   * This method creates widget for the adding the phone call details
   */
  private Widget addPhoneCallWidget(){
    Button addPhoneCallButton = new Button ("Add Phone Call");
    FlexTable addPhoneCallFlexTable = new FlexTable ();
    addPhoneCallFlexTable.setCellPadding (20);
    addPhoneCallFlexTable.setBorderWidth (0);
    addPhoneCallFlexTable.setCellSpacing (10);
    addPhoneCallFlexTable.getColumnFormatter ().setWidth (1,"500px");
    addPhoneCallFlexTable.getFlexCellFormatter ().setColSpan (0,1,3);
    addPhoneCallFlexTable.setWidget (0,0 , new HTML ("Customer Name"));
    addPhoneCallFlexTable.setWidget (0,1, new TextBox ());
    addPhoneCallFlexTable.setWidget (1,0 , new HTML ("Caller Number"));
    addPhoneCallFlexTable.setWidget (1,1, new TextBox ());
    addPhoneCallFlexTable.setWidget (2,0 , new HTML ("Callee Number"));
    addPhoneCallFlexTable.setWidget (2,1, new TextBox ());
    addPhoneCallFlexTable.setWidget (3,0 , new HTML ("Call Start Date/Time"));
    addPhoneCallFlexTable.setWidget (3,1, new TextBox ());
    addPhoneCallFlexTable.setWidget (4,0 , new HTML ("Call end Date/Time"));
    addPhoneCallFlexTable.setWidget (4,1, new TextBox ());
    addPhoneCallFlexTable.setWidget (5,1, addPhoneCallButton);
    addPhoneCallButton.addClickHandler (new ClickHandler(){
      @Override
      public void onClick(ClickEvent clickEvent){
        addPhoneCall (addPhoneCallFlexTable);
      }
    });
    return addPhoneCallFlexTable;
  }

  /***
   *
   * @return Widget
   * Created the widget for searching phone calls in the phone bill
   */

  private Widget searchPhoneCallsWidget(){
    FlexTable searchFlexTable = new FlexTable ();
    TextArea text = new TextArea ();
    TextBox customer = new TextBox ();
    customer.setMaxLength (50);
    TextBox startDate = new TextBox ();
    startDate.setMaxLength (50);
    TextBox endDate = new TextBox ();
    endDate.setMaxLength (50);
    text.setVisible (false);
    text.setVisibleLines (100);
    text.setHeight ("300px");
    text.setWidth ("500px");
    searchFlexTable.setCellPadding (20);
    searchFlexTable.setCellSpacing (8);
    searchFlexTable.getColumnFormatter ().setWidth (1,"500px");
    searchFlexTable.getFlexCellFormatter ().setColSpan (0,1,3);
    Button searchButton = new Button ("Search Phone Calls");
    searchFlexTable.setWidget (0,0,new HTML("Customer Name"));
    searchFlexTable.setWidget (0,1, customer);
    searchFlexTable.setWidget (1,0,new HTML ("Start Date/Time"));
    searchFlexTable.setWidget (1,1, startDate);
    searchFlexTable.setWidget (2,0,new HTML ("End Date/Time"));
    searchFlexTable.setWidget (2,1, endDate);

    searchFlexTable.setWidget (3,1,searchButton);
    searchFlexTable.setWidget (4,0, new HTML ("The Phone calls :"));
    searchFlexTable.setWidget (4,1,text);
    searchFlexTable.getFlexCellFormatter ().setColSpan (0,1,3);
    searchButton.addClickHandler (new ClickHandler(){
      @Override
      public void onClick(ClickEvent clickEvent){

        searchPhoneCalls (searchFlexTable);

      }

    });
    return searchFlexTable;

  }

  private void getReadme(){
    TextArea readmeArea = new TextArea ();
    readmeArea.setVisible (true);
    readmeArea.setVisibleLines (200);

    readmeArea.setText (PhoneCallHelper.readme ());


  }

  /***
   *
   * @return getPhoneBillFlexTable
   *
   * The flextable widget for the getting all the contents of the phonebill
   */

  private Widget getPhoneBillWidget(){
    FlexTable getPhoneBillFlexTable = new FlexTable ();

    getPhoneBillFlexTable.setCellPadding (20);
    getPhoneBillFlexTable.setCellSpacing (10);
    getPhoneBillFlexTable.getColumnFormatter ().setWidth (1,"500px");
    getPhoneBillFlexTable.getFlexCellFormatter ().setColSpan (0,1,3);
    TextArea text = new TextArea ();
    text.setVisible (false);
    text.setVisibleLines (100);
    text.setHeight ("300px");
    text.setWidth ("500px");

    Button getPhoneBillButton = new Button ("Get Phone Bill");
    getPhoneBillFlexTable.setWidget (0,0, new HTML ("Customer Name"));
    getPhoneBillFlexTable.setWidget (0,1,new TextBox ());
    getPhoneBillFlexTable.setWidget (1,0,getPhoneBillButton);
    getPhoneBillFlexTable.setWidget (2,0,text);
    getPhoneBillButton.addClickHandler (new ClickHandler () {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showPhoneBill(getPhoneBillFlexTable);

      }
    });
    return getPhoneBillFlexTable;
  }

  /***
   *
   * @param addPhoneCallFlexTable
   * This method preprocess the content for creating phone call and invokes the service method to add phone call.
   */

  private void addPhoneCall(FlexTable addPhoneCallFlexTable) {
    logger.info("adding new phonecall");

    String customerName = ((TextBox)addPhoneCallFlexTable.getWidget (0, 1)).getValue ().trim ();
    String callerNumber = ((TextBox)addPhoneCallFlexTable.getWidget (1,1)).getValue ().trim ();
    String calleeNumber = ((TextBox)addPhoneCallFlexTable.getWidget (2,1)).getValue ().trim ();
    String stTime = ((TextBox)addPhoneCallFlexTable.getWidget (3,1)).getValue ().trim ();
    String eTime = ((TextBox)addPhoneCallFlexTable.getWidget (4,1)).getValue ().trim ();
    Date startDateTime = null;
    Date endDateTime = null;

    try{
          if(customerName.equals ("") || customerName==null){
            Window.alert ("Customer Name cannot be empty");
            return;
          }
          if(callerNumber.equals ("")|| callerNumber==null){
            Window.alert ("Caller Phone Number Cannot be empty");
            return;
          }
          if(calleeNumber.equals ("")|| calleeNumber==null){
            Window.alert ("Caller Phone Number cannot be empty");
            return;
          }
          if(stTime.equals ("") || stTime == null){
            Window.alert ("Start Time of the call cannot be empty");
            return;
          }
          if(eTime.equals ("")|| eTime==null){
            Window.alert ("End time of the call cannot be empty");
            return;
          }
          PhoneCallHelper.checkPhoneNumberFormat (callerNumber);
          PhoneCallHelper.checkPhoneNumberFormat (calleeNumber);
          PhoneCallHelper.checkCallerAndCallee (callerNumber,calleeNumber);
          String[] sdatetime = stTime.split (" ");
          String [] edatetime = eTime.split (" ");
          if(sdatetime.length<3 ){
            Window.alert ("Invalid Start Date " + stTime);
            return;
          }
          if(edatetime.length<3){
            Window.alert ("Invalid End Date " + eTime);
            return;
          }
          PhoneCallHelper.checkDateFormat (sdatetime[0].trim ());
          PhoneCallHelper.checkTimeFormat (sdatetime[1].trim (),sdatetime[2].trim ());
          PhoneCallHelper.checkDateFormat (edatetime[0].trim ());
          PhoneCallHelper.checkTimeFormat (edatetime[1].trim (),edatetime[2].trim ());
          PhoneCallHelper.checkDateDifference (sdatetime[0].trim () , sdatetime[1].trim ()+" "+sdatetime[2].trim (),
                  edatetime[0].trim () , edatetime[1].trim ()+" "+edatetime[2].trim ());

          startDateTime = PhoneCallHelper.convertToDate (sdatetime[0].trim (),sdatetime[1].trim() , sdatetime[2].trim ());
          endDateTime = PhoneCallHelper.convertToDate (edatetime[0].trim () , edatetime[1].trim () , edatetime[2].trim ());

    }
    catch(InvalidArgumentFormatException | SameCallerAndCalleeException |InvalidDateAndTimeException | InvalidPhoneNumberException e){
      Window.alert (e.getMessage ());
      return;

    }
    catch(Exception e){
      Window.alert ("Exception  \n" + e.getMessage ());
      return;

    }
    PhoneCall call = new PhoneCall (callerNumber ,calleeNumber , startDateTime , endDateTime);
    phoneBillService.addPhoneCall (customerName, call, new AsyncCallback<Void> () {
      @Override
      public void onFailure(Throwable throwable) {
        Window.alert ("Error while creating the phone call .\n" + throwable.getMessage ());
        return;
      }

      @Override
      public void onSuccess(Void aVoid) {
        Window.alert ("Successfully created Phonecall ");

      }
    });
  }

  /***
   *
   * @param searchFlexTable
   * This method preprocesses the data for searching phone bill and calls the service method to get the phone calls between
   * the user specified time.
   */
  private void searchPhoneCalls(FlexTable searchFlexTable) {
    logger.info("search for phonecalls");
    String customer = ((TextBox)searchFlexTable.getWidget (0,1)).getValue ().trim ();
    String tempstartDate = ((TextBox)searchFlexTable.getWidget (1,1)).getValue ().trim ();
    String tempEndDate = ((TextBox)searchFlexTable.getWidget (2,1)).getValue ().trim ();
    TextArea textArea = (TextArea) searchFlexTable.getWidget (4,1);
    Date startDateTime = null;
    Date endDateTime = null;

    try{
      if(customer.equals ("")|| customer==null){
        Window.alert ("Customer name cannot be empty");
        return;
      }

      if(tempstartDate.equals ("") || tempstartDate == null){
        Window.alert ("Start Time of the call cannot be empty");
        return;
      }

      if(tempEndDate.equals ("") || tempEndDate == null){
        Window.alert ("End Time of the call cannot be empty");
        return;
      }
      String [] sDateTime = tempstartDate.split (" ");
      String [] eDateTime = tempEndDate.split (" ");
      if(sDateTime.length <3 ){
        Window.alert ("Invalid start date " + tempstartDate);
        return;
      }
      if(eDateTime.length<3){
        Window.alert ("Invalid end date  " + tempEndDate);
        return;
      }
      PhoneCallHelper.checkDateFormat (sDateTime[0].trim ());
      PhoneCallHelper.checkTimeFormat (sDateTime[1].trim (),sDateTime[2].trim ());
      PhoneCallHelper.checkDateFormat (eDateTime[0].trim ());
      PhoneCallHelper.checkTimeFormat (eDateTime[1].trim (),eDateTime[2].trim ());
      PhoneCallHelper.checkDateDifference (sDateTime[0].trim () , sDateTime[1].trim ()+" "+sDateTime[2].trim (),
              eDateTime[0].trim () , eDateTime[1].trim ()+" "+eDateTime[2].trim ());

      startDateTime = PhoneCallHelper.convertToDate (sDateTime[0].trim (),sDateTime[1].trim() , sDateTime[2].trim ());
      endDateTime = PhoneCallHelper.convertToDate (eDateTime[0].trim () , eDateTime[1].trim () , eDateTime[2].trim ());



    }catch (InvalidDateAndTimeException e){
      Window.alert ("Exception  " + e.getMessage ());
      return;
    } catch (Exception e){
      Window.alert ("Exception  " + e.getMessage ());
      return;
    }

    phoneBillService.searchPhoneBill (customer, startDateTime, endDateTime, new AsyncCallback<PhoneBill> () {
      @Override
      public void onFailure(Throwable throwable) {
        Window.alert ("Error while searching phone bill  " + throwable.getMessage ());
        return;
      }

      @Override
      public void onSuccess(PhoneBill phoneBill) {
        if(phoneBill.getPhoneCalls ().size () ==0){
          textArea.setText ("No Phone calls in the phone bill between the specified times");
        }else{
          textArea.setText (FileHelper.getPrettyContent (phoneBill));
        }
        textArea.setVisible (true);
        textArea.setReadOnly (true);
      }
    });

  }

  /**
   * This method preprocesses the data for getting all the phone calls in the
   * phone bill and calls the service method to get the phone bill
   *
   * @param phoneBillFlexTable
   */


  private void showPhoneBill(FlexTable phoneBillFlexTable) {
    String customer = null;

    customer = ((TextBox)phoneBillFlexTable.getWidget (0,1)).getValue ().trim ();
    TextArea textArea = (TextArea) phoneBillFlexTable.getWidget (2,0);

    try{
      if(customer.equals (" ") || customer ==null){
        Window.alert ("Customer Name Cannot be empty");
        return;
      }
      PhoneCallHelper.checkValidArgumentFormat (customer);
    } catch (InvalidArgumentFormatException  e){
      Window.alert(e.getMessage ());
      return;
    }catch (Exception  e){
      Window.alert(e.getMessage ());
      return;
    }

    logger.info("Calling getPhoneBill");

    phoneBillService.getPhoneBill( customer , new AsyncCallback<PhoneBill>() {


      @Override
      public void onFailure(Throwable ex) {
        Window.alert ("Error fetching the phone bill \n" + ex.getMessage ());
        return;

      }

      @Override
      public void onSuccess(PhoneBill phoneBill) {
        if(phoneBill.getPhoneCalls ().size ()==0){
          textArea.setText ("Customer doesnt have any phonecalls in the phonebill");
        }
        else{
          textArea.setText (FileHelper.getPrettyContent (phoneBill));
        }
       textArea.setVisible (true);
        textArea.setReadOnly (true);
        return;

      }
    });
  }


  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });
  }

  /***
   * Set up the UI for the application
   */

  private void setupUI() {
    RootLayoutPanel rootPanel = RootLayoutPanel.get ();

    rootPanel.setTitle ("Phone Bill GWT Application");
    MenuBar menuBar = new MenuBar (true);
    menuBar.setAutoOpen(true);
    menuBar.setWidth("100px");
    menuBar.setHeight ("15px");
    menuBar.setAnimationEnabled(true);
    MenuItem item1 = new MenuItem ("ReadMe", new Command () {
      @Override
      public void execute() {
        PopupPanel popupPanel = new PopupPanel (true);
        TextArea area = new TextArea ();
        area.setWidth ("600px");
        area.setHeight ("300px");
        area.setVisibleLines (100);
        area.setReadOnly (true);
        area.setText (PhoneCallHelper.readme ());
        popupPanel.add (area);
        popupPanel.center ();
        popupPanel.show ();
      }
    });


    menuBar.addItem (item1);


    TabLayoutPanel tabLayoutPanel = new TabLayoutPanel (3.5 , Style.Unit.EM);

    tabLayoutPanel.add (menuBar,"Help");
    tabLayoutPanel.add (addPhoneCallWidget (),"Add a Phone Call");
    tabLayoutPanel.add (searchPhoneCallsWidget (),"Search Phone Bill");
    tabLayoutPanel.add (getPhoneBillWidget (),"Show Phone Bill");


    tabLayoutPanel.addBeforeSelectionHandler (new BeforeSelectionHandler<Integer> () {
      @Override
      public void onBeforeSelection(BeforeSelectionEvent<Integer> beforeSelectionEvent) {
        int index = beforeSelectionEvent.getItem ();
        tabLayoutPanel.animate (10);
        tabLayoutPanel.remove (index);
        if(index==0){
          tabLayoutPanel.insert (menuBar,"Help",index);

        } else
         if(index ==1){
          tabLayoutPanel.insert (addPhoneCallWidget (),"Add Phone Call",index);

        } else if(index == 2){
          tabLayoutPanel.insert (searchPhoneCallsWidget (),"Search Phone Call" , index);

        } else if(index ==3){
          tabLayoutPanel.insert (getPhoneBillWidget (),"Show Phonebill",index);
        }

      }
    });


    rootPanel.add (tabLayoutPanel);


  }

  private void setUpUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        alertOnException(throwable);
      }
    });
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }

}
