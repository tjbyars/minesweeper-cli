package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    UserInput uInp;
    boolean gameRunning;
    Board gameBoard;

    // Constructor
    public Game() {
        uInp = new UserInput();
    }

    // Game control
    public void PlayGame() {
        Setup();
//        gameBoard.PrintRevealedBoard();
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
        String[] userInput = uInp.PromptUser();
        if (userInput[0].equalsIgnoreCase("quit")) {
            gameRunning = false;
            EndGame();
        } else {
            int xPos = Integer.parseInt(userInput[0]);
            int yPos = Integer.parseInt(userInput[1]);
            Tile chosenTile = gameBoard.visualBoard.get(xPos).get(yPos);
            ArrayList<Tile> checkedTiles = new ArrayList<Tile>();
            if (chosenTile.GetValue().equalsIgnoreCase("X")) {
                gameRunning = false;
                System.out.println("You hit a mine!");
            }
            gameBoard.RevealAdjacentTiles(chosenTile, checkedTiles);
        }
        if (gameRunning) {
            gameRunning = gameBoard.Update();
        }
    }

    // Quit the game
    public void EndGame() {
        gameBoard.PrintBoard();
        gameBoard.PrintRevealedBoard();
        gameRunning = false;
        System.out.println("Game ended, thank you for playing!");
    }
}
