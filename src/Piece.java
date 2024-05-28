import java.util.HashMap;

public abstract class Piece {
    /**
     * Positions of colors on the piece.
     */
    private HashMap<Rotation, Color> colors;
    private static final HashMap<Rotation, Color> defaultColors = new HashMap<>() {
        {
            put(Rotation.BACK, null);
            put(Rotation.RIGHT, null);
            put(Rotation.FRONT, null);
            put(Rotation.LEFT, null);
            put(Rotation.UP, null);
            put(Rotation.DOWN, null);
        }
    };

    /**
     * Current orientation of piece, judged by the cube when it's facing with green front and white top.
     */

    public Piece(HashMap<Rotation, Color> colors) {
        this.colors = colors;
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

        // Reset the piece's colours
        this.colors = new HashMap<>(defaultColors);

        // It is easiest to understand this method if you have an actual cube on hand.
        // Pay attention to how the stickers move. This code was tested using the
        // white, green, red piece, while the cube is facing with green front and white
        // top, as reference.
        //
        // The following if else chains check which colours are on the piece and
        // then moves them accordingly.
        //
        // To aid understanding, comments will explain what pieces were used as a reference.
        // Keep in mind these are all assuming green front and white top.
        switch (getPieceType()) {
            case EDGE -> {
            }
            case CORNER -> {
                if ((oldColors.get(Rotation.UP) != null)) {
                    // This is the white, green, red/orange corner.
                    if (oldColors.get(Rotation.FRONT) != null) {
                        colors.put(Rotation.BACK, oldColors.get(Rotation.UP));
                        colors.put(Rotation.UP, oldColors.get(Rotation.FRONT));
                    }
                    // This is the white, blue, red/orange corner.
                    else if (oldColors.get(Rotation.BACK) != null) {
                        colors.put(Rotation.DOWN, oldColors.get(Rotation.BACK));
                        colors.put(Rotation.BACK, oldColors.get(Rotation.UP));
                    }
                }
                else if ((oldColors.get(Rotation.DOWN) != null)) {
                    // This is the yellow, green, red/orange corner.
                    if (oldColors.get(Rotation.FRONT) != null) {
                        colors.put(Rotation.UP, oldColors.get(Rotation.FRONT));
                        colors.put(Rotation.FRONT, oldColors.get(Rotation.DOWN));
                    }
                    // This is the yellow, blue, red/orange corner.
                    else if (oldColors.get(Rotation.BACK) != null) {
                        colors.put(Rotation.DOWN, oldColors.get(Rotation.BACK));
                        colors.put(Rotation.FRONT, oldColors.get(Rotation.DOWN));
                    }
                }

                // These colours will always stay in the same place. If there is no colour
                // for one of these, it will simply be null.
                colors.put(Rotation.LEFT, oldColors.get(Rotation.LEFT));
                colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
            }
            case CENTER -> {
            }
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

    public void display() {
        System.out.println(colors);
        System.out.println();
    }
}
