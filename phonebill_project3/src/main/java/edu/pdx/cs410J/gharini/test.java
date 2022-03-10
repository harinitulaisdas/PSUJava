package edu.pdx.cs410J.gharini;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;


public class test {


        public static void main(String[] args) {


            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy hh:mm a", java.util.Locale.US);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat ("mm/dd/yyyy");


            Date date1 = null;
            Date date2 = null;
            try {
                Date myDate = sdf1.parse("28/12/2013 12:30 am");
                Date myDate2 = sdf1.parse("28/12/2013 12:40 am");
                Date myDate3 = sdf2.parse ("12/12/13");
                //DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
                //Date wmyDate = df.parse("28/12/13");
                long diff = myDate2.getTime () - myDate.getTime ();

                sdf1.applyPattern("EEE, d MMM yyyy , hh:mm a");

                String sMyDate = sdf1.format(myDate);

                System.out.println (myDate3);
                date1 = sdf.parse("2009-12-31");
                date2 = sdf.parse("2010-01-31");
            } catch (ParseException e) {
                e.printStackTrace ();
            }


//            System.out.println("date1 : " + sdf.format(date1));
//            System.out.println("date2 : " + sdf.format(date2));
//
//            if (date1.after (date2) ) {
//                System.out.println("Date1 is after Date2"  + date1.after (date2));
//            } else if (date1.before (date2)) {
//                System.out.println("Date1 is before Date2" + date1.before (date2));
//            } else if (date1.equals (date2) ) {
//                System.out.println("Date1 is equal to Date2");
//            } else {
//                System.out.println("How to get here?");
//            }


//            Date curDate = new Date();
//            String val1 = "124-345-6789";
//            String val2 = "123-345-6789";
//            Date date1 = sdf.parse("2009-12-31");
//            Date date2 = sdf.parse("2010-01-31");
//
//            System.out.println (val1.compareTo (val2));


//
//            DateToStr = DateFormat.getTimeInstance().format(curDate);
//            System.out.println(DateToStr);
//
//            DateToStr = DateFormat.getDateInstance().format(curDate);
//            System.out.println(DateToStr);
//
//            DateToStr = DateFormat.getDateTimeInstance().format(curDate);
//            System.out.println(DateToStr);

//            DateToStr = DateFormat.getTimeInstance(DateFormat.SHORT).format(dt);
//            System.out.println(DateToStr);

//            DateToStr = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(
//                    curDate);
//            System.out.println(DateToStr);

//            DateToStr = DateFormat.getTimeInstance(DateFormat.LONG).format(dt);
//            System.out.println(DateToStr);
//
//            DateToStr = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
//                    DateFormat.SHORT).format(curDate);
//            System.out.println(DateToStr);

//            try {
//                Date strToDate = DateFormat.getDateInstance()
//                        .parse("July 17, 1989");
//                System.out.println(strToDate.toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

        }
    }

