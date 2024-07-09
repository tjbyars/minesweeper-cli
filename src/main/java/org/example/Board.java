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
    int currentTiles;
    ArrayList<ArrayList<Tile>> visualBoard = new ArrayList<ArrayList<Tile>>();

//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_GREEN = " \u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_MAGENTA = "\u001B[35m";
//    public static final String ANSI_CYAN = "\u001B[36m";
//    public static final String ANSI_BACKGROUND_RED = "\u001B[41m";
//    public static final String ANSI_BACKGROUND_GREEN = "\u001B[42m";
    // Wanted to try this, but I think it would need the board printing to be entirely reworked to print each tile instead of by line


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
                height = 30;
                width = 16;
                totalMines = 99;
                break;
        }
    }

    // Custom board constructor
    public Board(int[] dimensions, int maxMines) {
        width = dimensions[1];      // I have clearly swapped the width and height at some point so
        height = dimensions[0];     // currently this is a workaround and just swaps them back here
                                    // so technically height is width and width is height
        int size = width * height;
        totalMines = maxMines;
    }

    // Initialise the board (arraylists) with empty tiles)
    public void CreateBoard() {
        currentTiles = (width * height) - totalMines;
        for (int i = 0; i <= width; i++) {
            ArrayList<Tile> row = new ArrayList<Tile>();
            for (int j = 0; j <= height; j++) {
                Tile tile = new Tile(i, j);
                tile.SetState("Hidden");
                row.add(tile);
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
        if (visualBoard.get(randRow).get(randColumn).GetValue().equalsIgnoreCase("O")) {
            visualBoard.get(randRow).get(randColumn).SetValue("X");
            success = true;
        }
        return success;
    }

    // Update tiles to show adjacent mines
    private void PlaceTiles() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                visualBoard.get(i).get(j).SetValue(CalculateValue(i, j));
            }
        }
    }

    // Calculate the value of a tile based on adjacent mines
    private String CalculateValue(int row, int column) {
        if (visualBoard.get(row).get(column).GetValue().equalsIgnoreCase("X")) { // position holds a mine
            return "X";
        } else {
            int value = 0;
            boolean atTop = row == 0;
            boolean atBottom = row == height - 1;
            boolean atLeft = column == 0;
            boolean atRight = column == width - 1;
            if (!atTop) {   // check above
                if (visualBoard.get(row - 1).get(column).GetValue().equals("X")) {
                    value++;
                }
                if (!atLeft) {  // check top left corner
                    if (visualBoard.get(row - 1).get(column - 1).GetValue().equals("X")) {
                        value++;
                    }
                }
                if (!atRight) { // check top right corner
                    if (visualBoard.get(row - 1).get(column + 1).GetValue().equals("X")) {
                        value++;
                    }
                }
            }
            if (!atBottom) {    // if not at bottom, check below
                if (visualBoard.get(row + 1).get(column).GetValue().equals("X")) {
                    value++;
                }
                if (!atLeft) {  // check bottom left corner
                    if (visualBoard.get(row + 1).get(column - 1).GetValue().equals("X")) {
                        value++;
                    }
                }
                if (!atRight) { // check bottom right corner
                    if (visualBoard.get(row + 1).get(column + 1).GetValue().equals("X")) {
                        value++;
                    }
                }
            }
            if (!atLeft) {  // if not at left, check left
                if (visualBoard.get(row).get(column - 1).GetValue().equals("X")) {
                    value++;
                }
            }
            if (!atRight) { // if not at right, check right
                if (visualBoard.get(row).get(column + 1).GetValue().equals("X")) {
                    value++;
                }
            }
            return String.valueOf(value);
        }
    }

    // Output the board to console
    public void PrintBoard() {
        System.out.println("\nCurrent board:");
        StringBuilder rowNumbers = new StringBuilder();
        rowNumbers.append("     ");
        StringBuilder rowDashes = new StringBuilder();
        rowDashes.append("   ");
        for (int i = 0; i < height; i++) {
            rowNumbers.append(i).append("   ");
            rowDashes.append("----");
        }
        System.out.println(rowNumbers);
        System.out.println(rowDashes);
        for (int i = 0; i < width; i++) {
            StringBuilder rowOutput = new StringBuilder();
            rowOutput.append(i).append(" | ");
            for (int j = 0; j < height; j++) {
                if (visualBoard.get(i).get(j).GetState().equalsIgnoreCase("Hidden")) {
                    rowOutput.append("[ ]");
                } else {
                    rowOutput.append("[").append(visualBoard.get(i).get(j).GetValue()).append("]");
                }
                rowOutput.append(" ");
            }
            System.out.println(rowOutput);
        }
        System.out.println("\n");
    }

    // Output the full revealed board to console
    public void PrintRevealedBoard() {
        System.out.println("\nCurrent board:");
        StringBuilder rowNumbers = new StringBuilder();
        rowNumbers.append("     ");
        StringBuilder rowDashes = new StringBuilder();
        rowDashes.append("   ");
        for (int i = 0; i < height; i++) {
            rowNumbers.append(i).append("   ");
            rowDashes.append("----");
        }
        System.out.println(rowNumbers);
        System.out.println(rowDashes);
        for (int i = 0; i < width; i++) {
            StringBuilder rowOutput = new StringBuilder();
            rowOutput.append(i).append(" |");
            for (int j = 0; j < height; j++) {
                rowOutput.append("  ");
                rowOutput.append(visualBoard.get(i).get(j).GetValue());
                rowOutput.append(" ");
            }
            System.out.println(rowOutput);
        }
        System.out.println("\n");
    }

    // Return all adjacent tiles as a list
    public ArrayList<Tile> GetAdjacentTiles(Tile tile) {
        ArrayList<Tile> adjacents = new ArrayList<Tile>();
        int xPos = tile.GetX();     // Issue was here
        int yPos = tile.GetY();
        if (xPos > 0) {
            adjacents.add(visualBoard.get(xPos - 1).get(yPos));             // Left
            if (yPos > 0) {
                adjacents.add(visualBoard.get(xPos - 1).get(yPos - 1));     // Up left corner
            }
            if (yPos < height - 1) {
                adjacents.add(visualBoard.get(xPos - 1).get(yPos + 1));     // Down left corner
            }
        }
        if (xPos < width - 1) {
            adjacents.add(visualBoard.get(xPos + 1).get(yPos));             // Right
            if (yPos > 0) {
                adjacents.add(visualBoard.get(xPos + 1).get(yPos - 1));     // Up right corner
            }
            if (yPos < height - 1) {
                adjacents.add(visualBoard.get(xPos + 1).get(yPos + 1));     // Down right corner
            }
        }
        if (yPos > 0) {
            adjacents.add(visualBoard.get(xPos).get(yPos - 1));             // Up
        }
        if (yPos < height - 1) {
            adjacents.add(visualBoard.get(xPos).get(yPos + 1));             // Down
        }
        return adjacents;
    }

    // Check the chosen tile, and reveal all relevant adjacent tiles
    public void RevealAdjacentTiles(Tile chosenTile, ArrayList<Tile> checkedTiles) {
        checkedTiles.add(chosenTile);
        if (chosenTile.GetState().equalsIgnoreCase("Hidden")) {
            if (chosenTile.GetValue().equalsIgnoreCase("X")){
                chosenTile.RevealTile();
                currentTiles--;
            } else if (chosenTile.GetValue().equalsIgnoreCase("0")) {
                chosenTile.RevealTile();
                currentTiles--;
                ArrayList<Tile> adjacentTiles = this.GetAdjacentTiles(chosenTile);
                for (Tile tile : adjacentTiles) {
                    if (!checkedTiles.contains(tile)) {
                        RevealAdjacentTiles(tile, checkedTiles);
                    }
                }
            } else {
                chosenTile.RevealTile();
                currentTiles--;
            }
        }
    }

    // Update whether the game is finished or not
    public boolean Update() {
        return currentTiles != 0;
    }
}
