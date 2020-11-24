package com.demo.springboot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeselValidator {
    private String entry;

    public PeselValidator(String entry) {
        this.entry = entry;
    }

    public boolean isValid(){
        return(
                this.isNumeric()
                && this.checkLength()
                && this.isMonthCodeCorrect()
                && this.isDateCorrect()
                && this.isChecksumValid()
                );
    }

    private boolean isNumeric(){
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.entry);
        return matcher.matches();
    }

    private boolean checkLength(){
        return (this.entry.length() == 11);
    }

    private boolean isMonthCodeCorrect(){
        String regex = "^[13579][0-2]|[02468][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        String monthCode = this.entry.substring(2, 4);
        Matcher matcher = pattern.matcher(monthCode);
        return matcher.matches();
    }

    private boolean isDateCorrect(){
        int yearSpecifier = Integer.parseInt(this.entry.substring(2, 3));
        if(yearSpecifier % 2 != 0){
            yearSpecifier--;
        }
        String yearFirstDigits;
        switch (yearSpecifier){
            case 0:
                yearFirstDigits = "19";
                break;
            case 2:
                yearFirstDigits = "20";
                break;
            case 4:
                yearFirstDigits = "21";
                break;
            case 6:
                yearFirstDigits = "22";
                break;
            case 8:
                yearFirstDigits = "18";
                break;
            default:
                yearFirstDigits = "00";
                break;
        }
        String year = yearFirstDigits + this.entry.substring(0, 2);
        int monthFirstDigit = Integer.parseInt(this.entry.substring(2, 3));
        monthFirstDigit -= yearSpecifier;
        String month = Integer.toString(monthFirstDigit) + this.entry.substring(3, 4);
        String day = this.entry.substring(4, 6);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try{
            dateFormat.parse(day+"/"+month+"/"+year);
            return true;
        }catch (ParseException e){
            return false;
        }
    }

    private boolean isChecksumValid(){
        int a = Integer.parseInt(this.entry.substring(0, 1));
        int b = Integer.parseInt(this.entry.substring(1, 2));
        int c = Integer.parseInt(this.entry.substring(2, 3));
        int d = Integer.parseInt(this.entry.substring(3, 4));
        int e = Integer.parseInt(this.entry.substring(4, 5));
        int f = Integer.parseInt(this.entry.substring(5, 6));
        int g = Integer.parseInt(this.entry.substring(6, 7));
        int h = Integer.parseInt(this.entry.substring(7, 8));
        int i = Integer.parseInt(this.entry.substring(8, 9));
        int j = Integer.parseInt(this.entry.substring(9, 10));
        int k = Integer.parseInt(this.entry.substring(10, 11));

        int checksum = 1 * a + 3 * b + 7 * c + 9 * d + 1 * e + 3 * f + 7 * g + 9 * h + 1 * i + 3 * j + 1 * k;

        return (checksum % 10 == 0);
    }
}
