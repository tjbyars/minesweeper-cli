package org.example;

import java.util.ArrayList;

public class Board {
    String style;   // "beginner" "intermediate" "expert" "custom"
    int width;
    int height;
    int totalMines;
    int currentMines;
    ArrayList<ArrayList<Character>> visualBoard = new ArrayList<ArrayList<Character>>();

    public Board(String style) {
        switch (style) {
            case "beginner":
                //
                break;
            case "intermediate":
                //
                break;
            case "expert":
                //
                break;
            case "custom":
                //
                break;
        }
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                visualBoard.get(i).set(j, 'o');
            }
        }

    }

    public void PopulateBoard() {
        PlaceMines(totalMines);
        PlaceTiles();
    }

    private void PlaceTiles() {
    }

    private void PlaceMines(int maxMines) {
    }

    public void PrintBoard() {
    }

    public void Update() {
    }
}
