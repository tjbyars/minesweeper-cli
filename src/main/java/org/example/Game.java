package org.example;

public class Game {
    UserInput uInp;
    boolean gameRunning;
    Board gameBoard;

    // Constructor
    public Game() {
        gameRunning = false;
        uInp = new UserInput();
    }

    // Game control
    public void PlayGame() {
        Setup();
        gameBoard.PrintBoard();
        while (gameRunning) {
            GameLoop();
        }
        EndGame();
    }

    // Setting up the board and the game
    public void Setup() {
        Board board = null;
        String choice = uInp.GetBoardChoice();
        if (choice.equalsIgnoreCase("preset")) {
            String style = uInp.GetBoardStyle();
            board = new Board(style);
        } else {
            int[] dimensions = uInp.GetDimensions();
            int maxMines = uInp.GetCustomMines(dimensions[2]);
            board = new Board(dimensions, maxMines);
        }
        board.CreateBoard();
        board.PopulateBoard();
        gameRunning = true;
        gameBoard = board;
    }

    // Gameplay loop, prints current board state, prompts the user to make a move, then updates the board
    public void GameLoop() {
        gameBoard.PrintBoard();
        String userInput = uInp.PromptUser();
        gameRunning = gameBoard.Update();
        if (userInput.equalsIgnoreCase("quit")) {
            gameRunning = false;
        }
    }

    // Quit the game (could output session stats?)
    public void EndGame() {
        gameRunning = false;
        System.out.println("Game ended, thank you for playing!");
    }
}
