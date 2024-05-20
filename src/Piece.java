import java.awt.*;
import java.util.HashMap;

public class Piece {
    // array of colours
    Color up = null;
    Color down = null;
    Color north = null;
    Color east = null;
    Color south = null;
    Color west = null;

    HashMap<Rotation, Color> colors = new HashMap<>();

    // current orientation of piece
    Rotation facing;

    public Piece(HashMap<Rotation, Color> colors, Rotation facing) {
        this.colors = colors;
        this.facing = facing;
    }

    public void moveR() {
        switch (facing) {
            case UP -> {
                facing = Rotation.EAST;
                colors.put(Rotation.UP, colors.get(Rotation.SOUTH));
                break;
            }
            case EAST -> {
                facing = Rotation.DOWN;
                break;
            }
            case DOWN -> {
                facing = Rotation.WEST;
                break;
            }
            case WEST -> {
                facing = Rotation.UP;
                break;
            }
        }
    }

    public Color getUp() {
        if (this.facing == Rotation.UP) {
            return up;
        }
        else {
            return null;
        }
    }

    public Color getColor(Rotation face) {
        switch (face) {
            case UP -> {

            }
        }
        return null;
    }
}

enum Rotation {
    NORTH, EAST, SOUTH, WEST, UP, DOWN
}
