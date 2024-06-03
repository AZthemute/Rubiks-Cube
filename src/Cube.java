import java.util.HashMap;

public class Cube {
    private HashMap<Rotation, xLayer> pieces;

    /**
     * Creates a solved cube.
     */
    public Cube() {
        xLayer leftLayer = new xLayer(Rotation.LEFT);
        xLayer middleLayer = new xLayer(Rotation.MIDDLE);
        xLayer rightLayer = new xLayer(Rotation.RIGHT);
        // The final cube layer: x co-ordinate.
        pieces = new HashMap<>() {
            {
                put(Rotation.LEFT, leftLayer);
                put(Rotation.MIDDLE, middleLayer);
                put(Rotation.RIGHT, rightLayer);
            }
        };
    }

    private class xLayer {
        HashMap<Rotation, yLayer> layers;

        public xLayer(Rotation xCoordinate) {
            yLayer upLayer;
            yLayer equatorLayer;
            yLayer downLayer;

            switch (xCoordinate) {
                case LEFT -> {
                    // Construct UL layer pieces
                    {
                        CornerPiece UFL = constructCornerPiece(new Color[] {Color.WHITE, Color.GREEN, Color.ORANGE});
                        EdgePiece UEL = constructEdgePiece(new Color[] {Color.WHITE, Color.ORANGE});
                        CornerPiece UBL = constructCornerPiece(new Color[] {Color.WHITE, Color.BLUE, Color.ORANGE});
                        upLayer = new yLayer(UFL, UEL, UBL);
                    }

                    // Construct EL layer pieces
                    {
                        EdgePiece EFL = constructEdgePiece(new Color[] {Color.GREEN, Color.ORANGE});
                        CenterPiece ESL = constructCenterPiece(Color.ORANGE);
                        EdgePiece EBL =constructEdgePiece(new Color[] {Color.BLUE, Color.ORANGE});
                        equatorLayer = new yLayer(EFL, ESL, EBL);
                    }

                    // Construct DL layer pieces
                    {
                        CornerPiece DFL = constructCornerPiece(new Color[] {Color.YELLOW, Color.GREEN, Color.ORANGE});
                        EdgePiece DEL = constructEdgePiece(new Color[] {Color.YELLOW, Color.ORANGE});
                        CornerPiece DBL = constructCornerPiece(new Color[] {Color.YELLOW, Color.BLUE, Color.ORANGE});
                        downLayer = new yLayer(DFL, DEL, DBL);
                    }
                }
                case MIDDLE -> {
                    // Construct UM layer pieces
                    {
                        EdgePiece UFM = constructEdgePiece(new Color[] {Color.GREEN, Color.WHITE});
                        CenterPiece UEM = constructCenterPiece(Color.WHITE);
                        EdgePiece UBM = constructEdgePiece(new Color[] {Color.BLUE, Color.WHITE});
                        upLayer = new yLayer(UFM, UEM, UBM);
                    }

                    // Construct EM layer pieces
                    {
                        CenterPiece EFM = constructCenterPiece(Color.GREEN);
                        // The ESM piece would be the core, so it'll be set to null and rejected in the getPiece() method.
                        CenterPiece EBM = constructCenterPiece(Color.BLUE);
                        equatorLayer = new yLayer(EFM, null, EBM);
                    }

                    // Construct DM layer pieces
                    {
                        EdgePiece DFM = constructEdgePiece(new Color[] {Color.GREEN, Color.YELLOW});
                        CenterPiece DEM = constructCenterPiece(Color.YELLOW);
                        EdgePiece DBM = constructEdgePiece(new Color[] {Color.BLUE, Color.YELLOW});
                        downLayer = new yLayer(DFM, DEM, DBM);
                    }
                }
                case RIGHT -> {
                    // Construct UR layer pieces
                    {
                        CornerPiece UFR = constructCornerPiece(new Color[] {Color.WHITE, Color.GREEN, Color.RED});
                        EdgePiece UER = constructEdgePiece(new Color[] {Color.WHITE, Color.RED});
                        CornerPiece UBR = constructCornerPiece(new Color[] {Color.WHITE, Color.BLUE, Color.RED});
                        upLayer = new yLayer(UFR, UER, UBR);
                    }

                    // Construct ER layer pieces
                    {
                        EdgePiece EFR = constructEdgePiece(new Color[] {Color.GREEN, Color.RED});
                        CenterPiece ESR = constructCenterPiece(Color.RED);
                        EdgePiece EBR = constructEdgePiece(new Color[] {Color.BLUE, Color.RED});
                        equatorLayer = new yLayer(EFR, ESR, EBR);
                    }

                    // Construct DR layer pieces
                    {
                        CornerPiece DFR = constructCornerPiece(new Color[] {Color.YELLOW, Color.GREEN, Color.RED});
                        EdgePiece DER = constructEdgePiece(new Color[] {Color.YELLOW, Color.RED});
                        CornerPiece DBR = constructCornerPiece(new Color[] {Color.YELLOW, Color.BLUE, Color.RED});
                        downLayer = new yLayer(DFR, DER, DBR);
                    }
                }
                default -> throw new IllegalArgumentException("Invalid x co-ordinate: Must be one of: LEFT, MIDDLE, RIGHT");
            }

            layers = new HashMap<>() {
                {
                    put(Rotation.UP, upLayer);
                    put(Rotation.EQUATOR, equatorLayer);
                    put(Rotation.DOWN, downLayer);
                }
            };
        }

        /**
         * Helper method for constructing pieces easier. It automatically
         * determines the positions of colors on a given piece.
         * This should be used in combination with a method to construct
         * @param colors Unordered array of colors that the piece contains.
         * @return HashMap of colors to use for constructing a piece.
         */
        private static HashMap<Rotation, Color> buildPieceColors(Color[] colors) {
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

        /**
         * Maybe temporary helper method for constructing pieces easier.
         * @param colors Unordered array of colors that the piece contains.
         * @return An EdgePiece.
         */
        private EdgePiece constructEdgePiece(Color[] colors) {
            if (colors.length != 2) {
                throw new IllegalArgumentException("An edge piece can only have 2 colours.");
            }

            return new EdgePiece(buildPieceColors(colors));
        }

        /**
         * Maybe temporary helper method for constructing pieces easier.
         * @param colors Unordered array of colors that the piece contains.
         * @return A CornerPiece.
         */
        private CornerPiece constructCornerPiece(Color[] colors) {
            if (colors.length != 3) {
                throw new IllegalArgumentException("A corner piece can only have 3 colours.");
            }

            return new CornerPiece(buildPieceColors(colors));
        }

        /**
         * Maybe temporary helper method for constructing pieces easier.
         * @param color The piece color.
         * @return A CenterPiece.
         */
        private CenterPiece constructCenterPiece(Color color) {
            return new CenterPiece(buildPieceColors(new Color[] {color}));
        }

        public void display() {
            for (yLayer layer: layers.values()) {
                layer.display();
            }
        }
    }

    private class yLayer {
        HashMap<Rotation, Piece> pieces;

        public yLayer(Piece frontPiece, Piece standingPiece, Piece backPiece) {
            pieces = new HashMap<>() {
                {
                    put(Rotation.FRONT, frontPiece);
                    put(Rotation.STANDING, standingPiece);
                    put(Rotation.BACK, backPiece);
                }
            };
        }

        public void display() {
            for (Piece piece: pieces.values()) {
                if (piece != null) {
                    piece.display();
                }
                else {
                    System.out.println("(Core position skipped)");
                }
            }
        }
    }

    /**
     * Get a single piece. All parameters are move notation. Blindfolded notation refers to
     * singular pieces by using the layers that the piece is on, for example UFR refers to
     * the top right piece on the front. The parameters of this function act the same way.
     * @param x Left/Middle/Right: Which layer on the x co-ordinate the piece is on
     * @param y Up/Equator/Down: Which layer on the y co-ordinate the piece is on
     * @param z Front/Standing/Back: Which layer on the z co-ordinate the piece is on
     * @return The piece
     */
    public Piece getPiece(Rotation x, Rotation y, Rotation z) {
        // Placeholder for now.
        return new CornerPiece(new HashMap<>());
        /*
        Piece piece;
        HashMap<Rotation, HashMap<Rotation, Piece>> xLayer;
        HashMap<Rotation, Piece> yLayer;
        switch (x) {
            case LEFT, MIDDLE, RIGHT -> xLayer = pieces.get(x);
            default -> throw new IllegalArgumentException("Invalid X co-ordinate: " + x);
        }
        switch (y) {
            case UP, DOWN -> yLayer = xLayer.get(y);
            case EQUATOR -> throw new IllegalArgumentException("Invalid piece: that position is the core, which is not a piece.");
            default -> throw new IllegalArgumentException("Invalid Y co-ordinate: " + y);
        }
        switch (z) {
            case FRONT, STANDING, BACK -> piece = yLayer.get(z);
            default -> throw new IllegalArgumentException("Invalid Z co-ordinate: " + z);
        }
        return piece;

         */
    }

    /**
     * Simulates one R move. This is a helper method for the move() method
     * @return The cube.
     */
    private Cube moveR() {
        // todo
        return this;
    }

    public Cube move(Rotation type, boolean isPrime, boolean isDouble) {
        // todo
        return this;
    }

    public void display() {
        for (xLayer layer: pieces.values()) {
            layer.display();
        }
    }
}
