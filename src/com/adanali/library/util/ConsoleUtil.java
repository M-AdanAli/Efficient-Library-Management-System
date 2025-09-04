package com.adanali.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleUtil {
    private static final Scanner scan = new Scanner(System.in);

    private ConsoleUtil(){}

    public static void printLibraryHeader(){
        clearConsole();
        System.out.println(" ----| Library Management System |----");
        printNewLines(2);
    }

    public static void printWelcomeUser(String userName){
        System.out.println("- Welcome "+ userName+"! -");
        ConsoleUtil.printNewLines(2);
    }

    public static String inputString(String prompt){
        System.out.print(prompt+" : ");
        return scan.nextLine();
    }

    public static LocalDate inputDate(String prompt){
        while (true){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.print(prompt+" (e.g., YYYY-MM-DD) : ");
            String dateString = scan.nextLine();
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (java.time.format.DateTimeParseException e) {
                System.err.println("Error: Invalid date format. Please use the specified format.");
            }
        }
    }

    public static int inputInteger(String prompt){
        System.out.print(prompt+" : ");
        return scan.nextInt();
    }

    public static void delay(long duration){
        try {
            Thread.sleep(duration);
        }catch (Exception e){
            System.out.println("Error!");
        }
    }

    public static void clearConsole() {
        printNewLines(50);
    }

    public static void printNewLines(int n){
        for (int i=0 ; i<n ; i++){
            System.out.println();
        }
    }
}
