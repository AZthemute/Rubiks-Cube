import java.util.HashMap;

public abstract class Piece {
    /**
     * Positions of colors on the piece.
     */
    protected HashMap<Rotation, Color> colors;
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

    public Piece(HashMap<Rotation, Color> colors) {
        this.colors = colors;
        colors.putIfAbsent(Rotation.BACK, null);
        colors.putIfAbsent(Rotation.RIGHT, null);
        colors.putIfAbsent(Rotation.FRONT, null);
        colors.putIfAbsent(Rotation.LEFT, null);
        colors.putIfAbsent(Rotation.UP, null);
        colors.putIfAbsent(Rotation.DOWN, null);
    }

    /**
     * Method to simulate an R move on a piece.
     * <p>
     * This method only moves around the colours, since the position
     * of the piece on the cube is tracked by the Cube object.
     */
    public HashMap<Rotation, Color> moveR() {
        // Copy the old colors into a new array for moving around later
        HashMap<Rotation, Color> oldColors = new HashMap<>(colors);

        // Reset the piece's colours
        this.colors = new HashMap<>(defaultColors);

        /*
         It is easiest to understand this method if you have an actual cube on hand.
         Pay attention to how the stickers move. This code was tested using the
         white, green, red piece, while the cube is facing with green front and white
         top, as reference.

         The following if else chains check which colours are on the piece and
         then moves them accordingly.

         To aid understanding, comments will explain what pieces were used as a reference.
         Keep in mind these are all assuming green front and white top.
        */
        switch (getPieceType()) {
            case EDGE -> {
                // Top layer
                if ((oldColors.get(Rotation.UP) != null)) {
                    colors.put(Rotation.BACK, oldColors.get(Rotation.UP));
                }
                // Bottom layer
                else if ((oldColors.get(Rotation.DOWN) != null)) {
                    colors.put(Rotation.FRONT, oldColors.get(Rotation.DOWN));
                }
                // Middle front layer
                else if ((oldColors.get(Rotation.FRONT) != null)) {
                    colors.put(Rotation.UP, oldColors.get(Rotation.FRONT));
                }
                // Middle back layer
                else {
                    colors.put(Rotation.DOWN, oldColors.get(Rotation.BACK));
                }

                // These colours will always stay in the same place. If there is no colour
                // for one of these, it will simply be null.
                colors.put(Rotation.LEFT, oldColors.get(Rotation.LEFT));
                colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
            }
            case CORNER -> {
                // Top layer
                if ((oldColors.get(Rotation.UP) != null)) {
                    // This is the white, green, red/orange corner.
                    colors.put(Rotation.UP, oldColors.get(Rotation.FRONT));
                    colors.put(Rotation.BACK, oldColors.get(Rotation.UP));
                }
                // Bottom layer
                else if ((oldColors.get(Rotation.DOWN) != null)) {
                    // This is the yellow, green, red/orange corner.
                    colors.put(Rotation.UP, oldColors.get(Rotation.FRONT));
                    colors.put(Rotation.FRONT, oldColors.get(Rotation.DOWN));
                }

                // These colours will always stay in the same place. If there is no colour
                // for one of these, it will simply be null.
                colors.put(Rotation.LEFT, oldColors.get(Rotation.LEFT));
                colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
            }
            case CENTER -> {
                // Simply don't move the piece.
                colors.put(Rotation.LEFT, oldColors.get(Rotation.LEFT));
                colors.put(Rotation.RIGHT, oldColors.get(Rotation.RIGHT));
            }
        }
        return colors;
    }

    /**
     * Helper method for constructing pieces easier. It automatically
     * determines the positions of colors on a given piece.
     * This should be used in combination with a method to construct
     * @param colors Unordered array of colors that the piece contains.
     * @return HashMap of colors to use for constructing a piece.
     */
    protected static HashMap<Rotation, Color> buildPieceColors(Color[] colors) {
        // Initialise empty HashMap
        HashMap<Rotation, Color> pieceColors = new HashMap<>();

        for (Color color : colors) {
            switch (color) {
                case WHITE -> pieceColors.put(Rotation.UP, Color.WHITE);
                case GREEN -> pieceColors.put(Rotation.FRONT, Color.GREEN);
                case RED -> pieceColors.put(Rotation.RIGHT, Color.RED);
                case ORANGE -> pieceColors.put(Rotation.LEFT, Color.ORANGE);
                case YELLOW -> pieceColors.put(Rotation.DOWN, Color.YELLOW);
                case BLUE -> pieceColors.put(Rotation.BACK, Color.BLUE);
            }
        }
        return pieceColors;
    }

    // To make other moves: Rotate around the colours (look at cube to understand how to rotate)
    // then do moveR(), then rotate around the colours back to normal (again, look at cube)

    public abstract PieceType getPieceType();

    public HashMap<Rotation, Color> getColors() {
        return colors;
    }

    public void display() {
        System.out.println(getPieceType());
        System.out.println(colors);
    }
}
