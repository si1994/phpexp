package com.quirktastic.utility;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static String TAG = "Util";
    public static boolean loadNextRequest = false;
    public static String deviceToken = "";
    public static boolean isChatProfileActivityVisible = false;

    public static String getDeviceToken() {
        return deviceToken;
    }

    public static void setDeviceToken(String deviceToken) {
        Util.deviceToken = deviceToken;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isValidEmail(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isDateValid(String date, String dateFormat) {
        try {
            DateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidWord(String word) {

        return word.matches("[A-Za-z][^.]*");
    }

    public static String getCurrentDate(String dateFormat) {
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat(dateFormat).format(date);

        return modifiedDate;
    }

    public static ArrayList<Integer> getDateDifferenceInDDMMYYYY(Date from, Date to) {
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        fromDate.setTime(from);
        toDate.setTime(to);
        int increment = 0;
        int year = -1, month = -1, day = -1;

        Logger.e(TAG, "" + fromDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
            increment = fromDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        Logger.e(TAG, "increment" + increment);
// DAY CALCULATION
        if (increment != 0) {
            day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
        }

// MONTH CALCULATION
        if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH)) {
            month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

// YEAR CALCULATION
        year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
        Logger.e(TAG, "diff---->" + year + "\tYears\t\t" + month + "\tMonths\t\t" + day + "\tDays");
//        return   year+"\tYears\t\t"+month+"\tMonths\t\t"+day+"\tDays";
        ArrayList<Integer> dateDiff = new ArrayList<>();
        dateDiff.add(year);
        dateDiff.add(month);
        dateDiff.add(day);
        return dateDiff;
    }

    public static String changeDateFormat(String old_format, String new_format, String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(old_format);
        Date d = sdf.parse(date);
        sdf.applyPattern(new_format);
        return sdf.format(d);
    }

    public static String getTwoDigitInt(int value) {
        if (value > 9) {
            return "" + value;
        } else {
            return "0" + value;
        }
    }

    public static String getYYYYMMDDfromMDY(String displayedDate) {

        String formattedDate = "";

        try {
            if (displayedDate != null && displayedDate.length() > 0) {
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date date = dateFormat.parse(displayedDate);
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = dateFormat.format(date);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return formattedDate;
    }


    public static String getYYYYMMDDfromDMY(String displayedDate) {

        String formattedDate = "";

        try {
            if (displayedDate != null && displayedDate.length() > 0) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = dateFormat.parse(displayedDate);
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = dateFormat.format(date);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return formattedDate;
    }

    public static String getMDYfromYYYYMMDD(String displayedDate) {

        String formattedDate = "";

        try {
            if (displayedDate.equals("0000-00-00")) {
                formattedDate = "";
            } else if (displayedDate != null && displayedDate.length() > 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(displayedDate);
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                formattedDate = dateFormat.format(date);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return formattedDate;
    }


    public static String getDMYfromYYYYMMDD(String displayedDate) {

        String formattedDate = "";

        try {
            if (displayedDate.equals("0000-00-00")) {
                formattedDate = "";
            } else if (displayedDate != null && displayedDate.length() > 0) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(displayedDate);
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                formattedDate = dateFormat.format(date);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return formattedDate;
    }

    public static String calculateAge(Date birthDate) {
        int years = 0;
        int months = 0;
        int days = 0;

        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one
        //and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        System.out.println("String.valueOf(years);==========" + String.valueOf(years));
        //Create new Age object
        return String.valueOf(years);
//        return new Age(days, months, years);
    }


    private Pattern pattern;
    private Matcher matcher;

    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";


    /**
     * Validate date format with regular expression
     *
     * @param date date address for validation
     * @return true valid date format, false invalid date format
     */
    public boolean validate(final String date) {

        matcher = pattern.matcher(date);

        if (matcher.matches()) {
            matcher.reset();

            if (matcher.find()) {
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month.equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month.equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static int getAge(Date birthDate) {
        int years = 0;
        int months = 0;
        int days = 0;

        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one
        //and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        //Create new Age object
        return years;
    }

    public static boolean isValidDate(String date) {
        // set date format, this can be changed to whatever format
        // you want, MM-dd-yyyy, MM.dd.yyyy, dd.MM.yyyy etc.
        // you can read more about it here:
        // http://java.sun.com/j2se/1.4.2/docs/api/index.html

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        // declare and initialize testDate variable, this is what will hold
        // our converted string

        Date testDate = null;

        // we will now try to parse the string into date form
        try {
            testDate = sdf.parse(date);
        }

        // if the format of the string provided doesn't match the format we
        // declared in SimpleDateFormat() we will get an exception

        catch (ParseException e) {
            String errorMessage = "the date you provided is in an invalid date" +
                    " format.";
            return false;
        }

        // dateformat.parse will accept any date as long as it's in the format
        // you defined, it simply rolls dates over, for example, december 32
        // becomes jan 1 and december 0 becomes november 30
        // This statement will make sure that once the string
        // has been checked for proper formatting that the date is still the
        // date that was entered, if it's not, we assume that the date is invalid

        if (!sdf.format(testDate).equals(date)) {
            String errorMessage = "The date that you provided is invalid.";
            return false;
        }

        // if we make it to here without getting an error it is assumed that
        // the date was a valid one and that it's in the proper format

        return true;

    } // end isValidDate

    public static String uriToString(Uri uri) {
        return uri.toString();
    }

    public static Uri stringToUri(String uri) {
        return Uri.parse(uri);
    }


    private static long convertDate(String dateText) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            // TimeZone utcZone = TimeZone.getTimeZone("UTC");
            Date mDate = sdf.parse(dateText);
            sdf.setTimeZone(TimeZone.getDefault());
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getChatDate(String dateText) {
        long neededTimeMilis = convertDate(dateText);
        Calendar nowTime = Calendar.getInstance();
        Calendar neededTime = Calendar.getInstance();
        neededTime.setTimeInMillis(neededTimeMilis);

        if ((neededTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR))) {

            if ((neededTime.get(Calendar.MONTH) == nowTime.get(Calendar.MONTH))) {

                if (nowTime.get(Calendar.DATE) == neededTime.get(Calendar.DATE)) {
                    //here return like "Today at 12:00"
                    return "Today";

                } else if (nowTime.get(Calendar.DATE) - neededTime.get(Calendar.DATE) == 1) {
                    //here return like "Yesterday at 12:00"
                    return "Yesterday";

                } else {
                    //here return like "May 31, 12:00"
                    return android.text.format.DateFormat.format("dd MMMM yyyy", neededTime).toString();
                }

            } else {
                //here return like "May 31, 12:00"
                return android.text.format.DateFormat.format("dd MMMM yyyy", neededTime).toString();
            }

        } else {
            //here return like "May 31 2010, 12:00" - it's a different year we need to show it
            return android.text.format.DateFormat.format("dd MMMM yyyy", neededTime).toString();
        }
    }


    public static String getChatTime(String dateText) {

        long neededTimeMilis = convertDate(dateText);
        Calendar neededTime = Calendar.getInstance();
        neededTime.setTimeInMillis(neededTimeMilis);


        //here return like "May 31 2010, 12:00" - it's a different year we need to show it
        return android.text.format.DateFormat.format("hh:mm a", neededTime).toString().toUpperCase();

    }


    public static String gifEncode(String text) {

        return URLEncoder.encode(text.trim()).replace("+", "%20")
                .replace("%40", "@")
                .replace("*", "%2A");

    }

    public static String gifDecode(String text) {

        return URLDecoder.decode(
                text.trim()).trim();

    }


}
