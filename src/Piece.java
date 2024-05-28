import java.awt.*;
import java.util.HashMap;

public abstract class Piece {
    /**
     * Positions of colors on the piece.
     */
    HashMap<Rotation, Color> colors;

    /**
     * Current orientation of piece, judged by the cube when it's facing with green front and white top.
     */
    // todo: (maybe) replace with a method that determines the rotation based on the combination of pieces
    // this will be good if we only end up using the facing value for moveR()
    Rotation facing;

    public Piece(HashMap<Rotation, Color> colors, Rotation facing) {
        this.colors = colors;
        this.facing = facing;
    }

    /**
     * Method to simulate an R move on a piece.
     * <p>
     * This method only moves around the colours, since the position
     * of the piece on the cube is tracked by the Cube object.
     */
    public void moveR() {
        // Copy the old colors into a new array for moving around later
        HashMap<Rotation, Color> oldColors = new HashMap<>(colors);

        // It is easiest to understand this method if you have an actual cube on hand.
        // Pay attention to how the stickers move. This code was tested using the
        // white, green, red piece, while the cube is facing with green front and white
        // top, as reference.
        switch (getPieceType()) {
            case EDGE -> {
            }
            case CORNER -> {
                // On a solved cube with green front and white top:
                // this is the white, green, red/orange corner
                if ((oldColors.get(Rotation.UP) != null)
                        && (oldColors.get(Rotation.FRONT) != null)

                        // Compatibility with pieces on both the left and right sides
                        && ((oldColors.get(Rotation.LEFT) != null) || (oldColors.get(Rotation.RIGHT) != null))) {
                    // Move colors to their correct positions
                    colors.put(Rotation.UP, oldColors.get(Rotation.BACK));
                    colors.put(Rotation.FRONT, oldColors.get(Rotation.UP));
                    colors.put(Rotation.LEFT, oldColors.get(Rotation.LEFT));
                    colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
                }
                // On a solved cube with green front and white top:
                // this is the white, blue, red/orange corner
                else if ((oldColors.get(Rotation.UP) != null)
                        && (oldColors.get(Rotation.BACK) != null)

                        // Compatibility with pieces on both the left and right sides
                        && ((oldColors.get(Rotation.LEFT) != null) || (oldColors.get(Rotation.RIGHT) != null))) {
                    // Move colors to their correct positions
                    colors.put(Rotation.UP, oldColors.get(Rotation.BACK));
                    colors.put(Rotation.BACK, oldColors.get(Rotation.DOWN));
                    colors.put(Rotation.LEFT, oldColors.get(Rotation.LEFT));
                    colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
                }
            }
            case CENTER -> {
            }
        }

        switch (facing) {
            case UP -> {
                facing = Rotation.RIGHT;
                // Move colors to their correct positions
                colors.put(Rotation.UP, oldColors.get(Rotation.BACK));
                colors.put(Rotation.FRONT, oldColors.get(Rotation.UP));
                colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
                break;
            }
            case BACK -> {
                facing = Rotation.DOWN;
                // Move colors to their correct positions
                colors.put(Rotation.DOWN, oldColors.get(Rotation.BACK));
                colors.put(Rotation.FRONT, oldColors.get(Rotation.UP));
                colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
                break;
            }
            case RIGHT -> {
            }
            case DOWN -> {
                facing = Rotation.FRONT;
                // Move colors to their correct positions
                colors.put(Rotation.UP, oldColors.get(Rotation.BACK));
                colors.put(Rotation.FRONT, oldColors.get(Rotation.UP));
                colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
                break;
            }
            case FRONT -> {
                facing = Rotation.UP;
                // Move colors to their correct positions
                colors.put(Rotation.UP, oldColors.get(Rotation.BACK));
                colors.put(Rotation.FRONT, oldColors.get(Rotation.UP));
                colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
                break;
            }
            case LEFT -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + facing + ". An R move cannot affect those faces.");
        }
    }

    public Color getColor(Rotation face) {
        switch (face) {
            case BACK -> {
                return colors.get(Rotation.BACK);
            }
            case RIGHT -> {
                return colors.get(Rotation.RIGHT);
            }
            case FRONT -> {
                return colors.get(Rotation.FRONT);
            }
            case LEFT -> {
                return colors.get(Rotation.LEFT);
            }
            case UP -> {
                return colors.get(Rotation.UP);
            }
            case DOWN -> {
                return colors.get(Rotation.DOWN);
            }
        }
        return null;
    }

    public abstract PieceType getPieceType();

    public HashMap<Rotation, Color> getColors() {
        return colors;
    }
}
