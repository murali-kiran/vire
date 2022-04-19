package com.vire.utils;

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


}
