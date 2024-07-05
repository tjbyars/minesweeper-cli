package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
    Scanner sc;

    // Can't return an int if there's a possibility of trying to handle errors or return "done"
    public String GetInt() {
        return GetHandledInput("int");
    }

    public String GetInt(String exception) {
        return GetHandledInput("int", exception);
    }

    public String GetStr() {

    }

    // Type checked input with no exception
    public String GetHandledInput(String type) {
        String output = "";
        String errorMessage = "";
        try {
            errorMessage = switch (type) {
                case "double" -> {
                    output = String.valueOf(sc.nextDouble());
                    yield "Please input a double";
                }
                case "float" -> {
                    output = String.valueOf(sc.nextFloat());
                    yield "Please input a float";
                }
                case "int" -> {
                    output = String.valueOf(sc.nextInt());
                    yield "Please input an int";
                }
                case "String" -> {
                    output = String.valueOf(sc.nextLine());
                    yield "Please input a string";
                }
                default -> errorMessage;
            };
        } catch (InputMismatchException e) {
            System.out.println(errorMessage);
            sc.nextLine();
            output = GetHandledInput(type);
        }
        return output;
    }

    // Type checked input with an exception
    public String GetHandledInput(String type, String exception) {
        String output = "";
        String errorMessage = "";
        try {
            errorMessage = switch (type) {
                case "double" -> {
                    output = String.valueOf(sc.nextDouble());
                    yield "Please input a double";
                }
                case "float" -> {
                    output = String.valueOf(sc.nextFloat());
                    yield "Please input a float";
                }
                case "int" -> {
                    output = String.valueOf(sc.nextInt());
                    yield "Please input an int";
                }
                case "String" -> {
                    output = String.valueOf(sc.nextLine());
                    yield "Please input a string";
                }
                default -> errorMessage;
            };
        } catch (InputMismatchException e) {
            String exceptionCheck = sc.nextLine();
            if (exceptionCheck.equalsIgnoreCase(exception)) {
                output = exceptionCheck;
            } else {
                System.out.println(errorMessage);
                sc.nextLine();
                output = GetHandledInput(type, exception);
            }
        }
        return output;
    }

}
