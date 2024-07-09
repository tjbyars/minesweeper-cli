package org.example;

public class Tile {
    String state;
    String value;
    int xPos;
    int yPos;

    // Constructor
    public Tile(int x, int y) {
        state = "Hidden";
        value = "O";
        xPos = x;
        yPos = y;
    }

    // Reveal tile
    public void RevealTile() {
        state = "Visible";
    }

    // Getter and setter methods
    public String GetState() {
        return state;
    }
    public void SetState(String state) {
        this.state = state;
    }
    public String GetValue() {
        return value;
    }
    public void SetValue(String value) {
        this.value = value;
    }
    public int GetX() {
        return xPos;
    }
    public void SetX(int xPos) {
        this.xPos = xPos;
    }
    public int GetY() {
        return yPos;
    }
    public void SetY(int yPos) {
        this.yPos = yPos;
    }

}
