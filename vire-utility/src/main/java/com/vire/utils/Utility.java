package com.vire.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        //Initialize regex for email.
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        //Make the comparison case-insensitive.
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /* Phone Number formats: (nnn)nnn-nnnn; nnnnnnnnnn; nnn-nnn-nnnn
                 Examples: Matches following phone numbers:
                 (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890
    */
    public static boolean isPhoneNumberValid(String phoneNumber){
        boolean isValid = false;
        //Initialize reg ex for phone number.
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            isValid = true;
        }
        return isValid;
    }

    /*Number: A numeric value will have following format: ^[-+]?: Starts with an optional "+" or "-" sign.
     [0-9]*: May have one or more digits. \\.? : May contain an optional "." (decimal point) character.
    [0-9]+$ : ends with numeric digit.
    */

    public static boolean isNumeric(String number){
        boolean isValid = false;

        //Initialize regex for numeric data.
        String expression = "^[-+]?[0-9]*\\.?[0-9]+$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            isValid = true;
        }
        return isValid;
    }

    public static boolean isValidAadhar(String aadhar){

        boolean isValid = false;
        //Initialize reg ex for aadhar.
        String expression = "^$|[0-9]{12}";
        CharSequence inputStr = aadhar;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            isValid = true;
        }
        return isValid;

    }

    public static boolean isValidFileID(String fileID){

        boolean isValid = false;
        //Initialize reg ex for file ID.
        String expression = "^[0-9]*$";
        CharSequence inputStr = fileID;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            isValid = true;
        }
        return isValid;

    }

    public static boolean isValidDOB(String dob){

        boolean isValid = false;
        //Initialize reg ex for Date of birth and date range years from 1800 to 2999.
        String expression = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((18|19|2[0-9])[0-9]{2})$";
        CharSequence inputStr = dob;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            isValid = true;
        }
        return isValid;

    }
    //
    public static String hhmmampmTimeFormat(Long timeMillis){
        DateFormat sdf2 = new SimpleDateFormat("hh:mm a");
        sdf2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdf2.format(new Date(timeMillis));
    }
    public static String DdMMMYYYYTimeFormat(Long timeMillis){
        DateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
        sdf2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdf2.format(new Date(timeMillis));
    }
    public static String customTimeFormat(Long timeMillis){
        DateFormat sdf2 = new SimpleDateFormat("MMMM dd 'at' HH:mm");
        sdf2.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdf2.format(new Date(timeMillis));
    }
    public static String calculateTimeDiff(Long updatedTime) {
        Long currentTime = System.currentTimeMillis();
        Long timeDifferenceMilliseconds = currentTime - updatedTime;
        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = timeDifferenceMilliseconds / ((long) 60 * 60 * 1000 * 24 * 365);

        if (diffSeconds < 1) {
            return "few seconds ago";
        } else if (diffMinutes < 1) {
            return diffSeconds + " s ";
        } else if (diffHours < 1) {
            return diffMinutes + " m ";
        } else if (diffDays < 1) {
            return diffHours + " h ";
        } else if (diffWeeks < 1) {
            return diffDays + " d ";
        } else if (diffMonths < 1) {
            if(diffWeeks == 1)
                return diffWeeks + " week";
            else
                return diffWeeks + " weeks";
        } else if (diffYears < 1) {
            if(diffMonths == 1)
                return diffMonths + " month";
            else
                return diffMonths + " months";
        } else {
            if(diffYears == 1)
                return diffYears + " year";
            else
                return diffYears + " years";
        }
    }
}
