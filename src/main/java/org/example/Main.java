package org.example;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.PlayGame();
    }
}

/*
potential improvements
	calculateValue and getAdjacents both check adjacents, but differently
	offset double digit numbers correctly
	colourful outputs
	input limits
features
	error handling
	separate mines and flags into subclasses of tile
	safe start
	flagging mines
 */