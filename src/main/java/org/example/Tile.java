package org.example;

public class Tile {
    String state;
    String value;

    // Constructor
    public Tile() {
        state = "Hidden";
        value = "O";
    }

    // Reveal tile
    public void RevealTile() {
        state = "Visible";
    }

    // Getter and setter methods
    public String GetState() {
        return state;
    }
    public void SetState(String newState) {
        state = newState;
    }
    public String GetValue() {
        return value;
    }
    public void SetValue(String newValue) {
        value = newValue;
    }

}
