package org.example;

public class Game {
    UserInput uInp;
    boolean gameRunning;
    Board gameBoard;

    public Game() {
        gameRunning = false;
    }

    public void PlayGame() {
        Setup();
        while (gameRunning) {
            GameLoop();
        }
    }

    public void Setup() {
        String choice = GetBoardChoice();
        if (choice.equalsIgnoreCase("preset")) {
            String style = GetBoardStyle();
        } else {

            GetDimensions();
        }
        Board board = new Board(style);
        board.PopulateBoard();
    }

    private String GetBoardChoice() {
        System.out.println("Would you like to use a preset board size, or enter a custom one?" +
                "Enter 1 to choose a preset board size, or 2 to choose a custom board size.");
        int choice = Integer.parseInt(uInp.GetHandledInput("int"));
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

    private String GetBoardStyle() {
        System.out.println("Would you like to play \"Beginner\", \"Intermediate\", or \"Expert\"?" +
                "Enter 1 to choose \"Beginner\", 2 to choose \"Intermediate\", or 3 to choose \"Expert\".");
        int choice = Integer.parseInt(uInp.GetHandledInput("int"));
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

    public void GameLoop() {
        gameBoard.PrintBoard();
        PromptUser();
        gameBoard.Update();
    }

    private void PromptUser() {
    }

    public int[] GetDimensions() {
        System.out.println("How wide would you like the board to be (suggested: 10)");
        int width = Integer.parseInt(uInp.GetInt());
        System.out.println("How tall would you like the board to be (suggested: 10");
        int height = Integer.parseInt(uInp.GetInt());
        return new int[]{width, height};
    }

    public void EndGame() {
        gameRunning = false;
        System.out.println("Game ended, thank you for playing!");
    }
}
