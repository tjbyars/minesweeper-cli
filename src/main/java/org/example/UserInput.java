package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {
    Scanner sc = new Scanner(System.in);

    // Can't return an int if there's a possibility of trying to handle errors or return "done"
    public String GetInt() {
        return GetHandledInput("int");
    }

    public String GetString() {
        return GetHandledInput("String");
    }

    // Type checked input with no exception
    public String GetHandledInput(String type) {
        String output = "";
        String errorMessage = "";
        try {
            errorMessage = switch (type) {
                case "double" -> {
                    output = String.valueOf(sc.nextDouble());
                    sc.nextLine();
                    yield "Please input a double";
                }
                case "float" -> {
                    output = String.valueOf(sc.nextFloat());
                    sc.nextLine();
                    yield "Please input a float";
                }
                case "int" -> {
                    output = String.valueOf(sc.nextInt());
                    sc.nextLine();
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
                    sc.nextLine();
                    yield "Please input a double";
                }
                case "float" -> {
                    output = String.valueOf(sc.nextFloat());
                    sc.nextLine();
                    yield "Please input a float";
                }
                case "int" -> {
                    output = String.valueOf(sc.nextInt());
                    sc.nextLine();
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

    // Prompt user to choose between preset or custom board
    public String GetBoardChoice() {
        System.out.println("\nWould you like to use a preset board size, or enter a custom one?\n" +
                "Enter 1 to choose a preset board size, or 2 to choose a custom board size.");
        int choice = Integer.parseInt(GetHandledInput("int"));
        String chosenOption = "";
        if (choice == 1) {
            chosenOption = "preset";
        } else if (choice == 2) {
            chosenOption = "custom";
        } else {
            GetBoardChoice();
        }
        return chosenOption;
    }

    // Prompt user to choose preset board style
    public String GetBoardStyle() {
        System.out.println("\nWould you like to play \"Beginner\", \"Intermediate\", or \"Expert\"?\n" +
                "Enter 1 to choose \"Beginner\", 2 to choose \"Intermediate\", or 3 to choose \"Expert\".");
        int choice = Integer.parseInt(GetHandledInput("int"));
        String chosenStyle = "";
        if (choice == 1) {
            chosenStyle = "beginner";
        } else if (choice == 2) {
            chosenStyle = "intermediate";
        } else if (choice == 3) {
            chosenStyle = "expert";
        } else {
            GetBoardStyle();
        }
        return chosenStyle;
    }

    // Prompt user for custom board dimensions
    public int[] GetDimensions() {
        System.out.println("How wide would you like the board to be? (Suggested: 10)");
        int width = Integer.parseInt(GetInt());
        System.out.println("How tall would you like the board to be? (Suggested: 10)");
        int height = Integer.parseInt(GetInt());
        int size = width * height;
        return new int[]{width, height, size};
    }

    // Prompt user for number of mines on custom board
    public int GetCustomMines(int size) {
        int maxMines = size / 2;    // (x-1)(y-1)
        System.out.println("How many mines would you like (maximum: " + maxMines + ")");
        int chosenMines = Integer.parseInt(GetInt());
        if (chosenMines <= maxMines) {
            return chosenMines;
        } else {
            return GetCustomMines(size);
        }
    }

    // Prompt and input gathering for the standard user turn
    public String[] PromptUser() {
        System.out.println("Please enter the position of the tile you would like to reveal. \n" +
                "e.g. \"0,0\" for the top left tile, \"0,1\" for the tile to the right, and \"1,0\" for the tile below.");
        String userInput = String.valueOf(GetString());
        return userInput.split("\\s*,\\s*");
    }


}
