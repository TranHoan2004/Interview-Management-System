package com.ims_team4.utils;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Validation {

    @NotNull
    public static String removeUnnecessaryBlank(@NotNull String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    @NotNull
    public static String removeAllBlank(@NotNull String input) {
        return input.trim().replaceAll("\\s+", "");
    }

    public static boolean pressYN() throws Exception {
        String input = getStringByRegex("Do you want to continue? (Y/N): ", "[YNyn]", "[YNyn]");
        return input.equalsIgnoreCase("y");
    }

    public static String normalFormName(String input) {
        input = removeUnnecessaryBlank(input);
        String[] temp = input.split(" ");
        input = "";
        for (int i = 0; i < temp.length; i++) {
            input = String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) {
                input += " ";
            }
        }
        return input;
    }

    @NotNull
    public static String getNonEmptyString(String mess, String string) throws Exception {
        System.out.print(mess);
        string = removeUnnecessaryBlank(string);
        if (string.equalsIgnoreCase("")) {
            throw new Exception("Please input Non-Empty String!!!");
        }
        return string;
    }

    @NotNull
    public static String normalFormStringAfterDot(String input) {
        String output = "";
        input = removeUnnecessaryBlank(input);
        input = String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1);
        input = input.replaceAll("\\.\\s+", ".").replaceAll("\\s+\\.", ".");
        output += input.charAt(0);
        StringBuilder outputBuilder = new StringBuilder(output);
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i - 1) == '.' && Character.isAlphabetic(input.charAt(i))) {
                outputBuilder = new StringBuilder(" " + Character.toUpperCase(input.charAt(i)));
            } else {
                outputBuilder.append(input.charAt(i));
            }
        }
        output = outputBuilder.toString();
        return output;
    }

    public static int getInt(String message, String error, int min, int max) throws Exception {
        int input = Integer.parseInt(getStringByRegex(message, "[0-9]+", error));
        if (input < min || input > max) {
            throw new Exception("Out of range!");
        } else {
            return input;
        }
    }

    @NotNull
    public static String getStringByRegex(String message, String regex, String err) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print(message + " ");
        String input = sc.nextLine();
        if (input.isEmpty()) {
            throw new Exception("Not null!");
        } else if (input.matches(regex)) {
            return input;
        }
        throw new Exception(err);
    }

    @NotNull
    public static String getEmail(String message) throws Exception {
        String regex = "^[A-Za-z](.*)([@]{1})(.{2,})(\\.)(.{2,})"; // phai bat dau bang chu cai
        return getStringByRegex(message, regex, "Please enter email with format <account name>@<domain>");
    }

    @NotNull
    public static String getPhone(String message) throws Exception {
        String phoneNum = getStringByRegex(message, "[0-9]+", "Please enter number only!!");
        if (phoneNum.length() == 10 || phoneNum.length() == 11) {
            return phoneNum;
        }
        throw new Exception("Phone number must be at least 10 characters and at most 11 characters");
    }

    public static double getDouble(String message, String error, double min, double max) throws Exception {
        double input = Double.parseDouble(getStringByRegex(message, "[0-9]*\\.?[0-9]+", error));
        if (input < min || input > max) {
            throw new Exception("Out of range!");
        } else {
            return input;
        }
    }

    @NotNull
    public static LocalDate getDate(String input) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print(input);
        input = sc.nextLine();
        // regex: yyyy/MM/dd
        int day = 0;
        int month = 0;
        int year = 0;
        String regex1 = "^(\\d{1,2})[/-](\\d{1,2})[/-](\\d{4})$"; // MM/-dd/-yyyy
        String regex2 = "^(\\d{1,2})[/-](\\d{4})[/-](\\d{1,2})$"; // MM/-yyyy/-dd
        String regex3 = "^(\\d{4})[/-](\\d{1,2})[/-](\\d{1,2})$"; // yyyy/-MM/-dd
        // case MM/-dd/-yyyy
        if (input.matches(regex1)) {
            month = Integer.parseInt(input.substring(0, 2));
            day = Integer.parseInt(input.substring(3, 5));
            year = Integer.parseInt(input.substring(6, 10));
        } else if (input.matches(regex2)) {
            // MM/-yyyy/-dd
            month = Integer.parseInt(input.substring(0, 2));
            year = Integer.parseInt(input.substring(3, 7));
            day = Integer.parseInt(input.substring(8, 10));
        } else if (input.matches(regex3)) {
            // yyyy/MM/dd
            day = Integer.parseInt(input.substring(8, 10));
            year = Integer.parseInt(input.substring(0, 4));
            month = Integer.parseInt(input.substring(5, 7));
        } else {
            throw new Exception("Must follow the format of date (Ex: yyyy/MM/dd)");
        }
        int temp = 0;
        if (month > 12) {
            temp = day;
            day = month;
            month = temp;
        }
        LocalDate date = LocalDate.of(year, month, day);
        isLeapYear(date);
        return date;
    }

    public static boolean getFormattedPassword(String password) throws Exception {
        password = removeUnnecessaryBlank(password);
        if (password.length() < 8 || password.length() > 32) {
            throw new Exception("Password must have length between 8 and 32 characters");
        }
        return true;
    }

    @NotNull
    public static LocalDate getCurrentDate() throws Exception {
        return LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private static void isLeapYear(@NotNull LocalDate localDate) throws Exception {
        if (!localDate.isLeapYear() && localDate.getMonth().equals(Month.FEBRUARY) && localDate.getDayOfMonth() == 29) {
            throw new Exception("February has 28 days in non-leap years");
        }
    }

}
