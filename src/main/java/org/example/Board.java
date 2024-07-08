package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/*
The approach I'm using here for placing, detecting mines etc. is that the game will check the relevant
position whenever something needs to be done with it, but maybe it would be faster and/or easier to
instead store all mines in a list, then just do everything using this list instead, for example instead
of going to check the position on the board to see if it is a mine, instead just compare to the coordinates
in the mine list. Same thing for populating the board with number tiles, instead of rerunning the whole
board, instead just run through all the mines and increment the values in all of them by 1.
 */

public class Board {
    UserInput uInp;
    String style;   // "beginner" "intermediate" "expert" "custom"
    int width;
    int height;
    int totalMines;
    int currentMines;
    ArrayList<ArrayList<String>> visualBoard = new ArrayList<ArrayList<String>>();

    // Preset board constructor
    public Board(String style) {
        switch (style) {
            case "beginner":
                width = 9;
                height = 9;
                totalMines = 10;
                break;
            case "intermediate":
                width = 16;
                height = 16;
                totalMines = 40;
                break;
            case "expert":
                width = 30;
                height = 16;
                totalMines = 99;
                break;
        }
    }

    // Custom board constructor
    public Board(int[] dimensions, int maxMines) {
        width = dimensions[0];
        height = dimensions[1];
        int size = width * height;
        totalMines = maxMines;
    }

    // Initialise the board (arraylists) with empty tiles)
    public void CreateBoard() {
        for (int i = 0; i <= width; i++) {
            ArrayList<String> row = new ArrayList<String>();
            for (int j = 0; j <= height; j++) {
//                System.out.println("xPos: " + i + " yPos: " + j);
//                visualBoard.get(i).set(j, "O");
                row.add("O");
            }
            visualBoard.add(row);
        }
    }

    // Randomly place mines on board, then update other tiles to show adjacent mines
    public void PopulateBoard() {
        PlaceAllMines(totalMines);
        PlaceTiles();
    }

    // Randomly place mines on board
    private void PlaceAllMines(int maxMines) {
        for (int i = 0; i < maxMines; i++) {
            boolean success = false;
            while (!success) {
                success = PlaceMine();
            }
        }
    }

    // Randomly place a single mine on the board
    private boolean PlaceMine() {
        boolean success = false;
        Random rand = new Random();
        int randRow = rand.nextInt(width);
        int randColumn = rand.nextInt(height);
        if (visualBoard.get(randRow).get(randColumn).equalsIgnoreCase("O")) {
            visualBoard.get(randRow).set(randColumn, "X");
            success = true;
        }
        return success;
    }

    // Update tiles to show adjacent mines
    private void PlaceTiles() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
//                System.out.println("xPos: " + i + " yPos: " + j);
                visualBoard.get(i).set(j, CalculateValue(i, j));
            }
        }
    }

    // Calculate the value of a tile based on adjacent mines
    private String CalculateValue(int row, int column) {
//        System.out.println("Row: " + row + " Column: " + column);
//        System.out.println("Width: "  + width + " Height: " + height);
        if (visualBoard.get(row).get(column).equalsIgnoreCase("X")) { // position holds a mine
            return "X";
        } else {
            int value = 0;
            boolean atTop = row == 0;
            boolean atBottom = row == height - 1;
            boolean atLeft = column == 0;
            boolean atRight = column == width - 1;
            // if not at top, check above
            if (!atTop) {
                //            System.out.println(visualBoard.get(row));
                //            System.out.println(visualBoard.get(row - 1));
                if (visualBoard.get(row - 1).get(column).equals("X")) {
                    value++;
                }
                // check top left corner
                if (!atLeft && visualBoard.get(row - 1).get(column - 1).equals("X")) {
                    value++;
                }
                // check top right corner
                if (!atRight && visualBoard.get(row - 1).get(column + 1).equals("X")) {
                    value++;
                }
            }
            // if not at bottom, check below
            if (!atBottom && visualBoard.get(row + 1).get(column).equals("X")) {
                value++;
                // check bottom left corner
                if (!atLeft && visualBoard.get(row + 1).get(column - 1).equals("X")) {
                    value++;
                }
                // check bottom right corner
                if (!atRight && visualBoard.get(row + 1).get(column + 1).equals("X")) {
                    value++;
                }
            }
            // if not at left, check left
            if (!atLeft && visualBoard.get(row).get(column - 1).equals("X")) {
                value++;
            }
            // if not at right, check right
            if (!atRight && visualBoard.get(row).get(column + 1).equals("X")) {
                value++;
            }
            return String.valueOf(value);
        }
    }

    // Output the board to console
    public void PrintBoard() {
        System.out.println("\nCurrent board:");
        for (int i = 0; i < width; i++) {
            StringBuilder rowOutput = new StringBuilder();
            for (int j = 0; j < height; j++) {
                rowOutput.append(visualBoard.get(i).get(j));
                rowOutput.append("   ");
            }
            System.out.println(rowOutput);
        }
        System.out.println("\n");
    }

    // Update the board (after user has played a turn)
    public boolean Update() {
        return currentMines != 0;
    }
}
