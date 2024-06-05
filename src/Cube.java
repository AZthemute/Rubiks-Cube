import java.util.HashMap;

public class Cube {
    private HashMap<Rotation, xLayer> layers;

    /**
     * Creates a solved cube.
     */
    public Cube() {
        xLayer leftLayer = new xLayer(Rotation.LEFT);
        xLayer middleLayer = new xLayer(Rotation.MIDDLE);
        xLayer rightLayer = new xLayer(Rotation.RIGHT);
        // The final cube layer: x co-ordinate.
        layers = new HashMap<>() {
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
                        CornerPiece UFL = new CornerPiece(new Color[] {Color.WHITE, Color.GREEN, Color.ORANGE});
                        EdgePiece UEL = new EdgePiece(new Color[] {Color.WHITE, Color.ORANGE});
                        CornerPiece UBL = new CornerPiece(new Color[] {Color.WHITE, Color.BLUE, Color.ORANGE});
                        upLayer = new yLayer(UFL, UEL, UBL);
                    }

                    // Construct EL layer pieces
                    {
                        EdgePiece EFL = new EdgePiece(new Color[] {Color.GREEN, Color.ORANGE});
                        CenterPiece ESL = new CenterPiece(Color.ORANGE);
                        EdgePiece EBL =new EdgePiece(new Color[] {Color.BLUE, Color.ORANGE});
                        equatorLayer = new yLayer(EFL, ESL, EBL);
                    }

                    // Construct DL layer pieces
                    {
                        CornerPiece DFL = new CornerPiece(new Color[] {Color.YELLOW, Color.GREEN, Color.ORANGE});
                        EdgePiece DEL = new EdgePiece(new Color[] {Color.YELLOW, Color.ORANGE});
                        CornerPiece DBL = new CornerPiece(new Color[] {Color.YELLOW, Color.BLUE, Color.ORANGE});
                        downLayer = new yLayer(DFL, DEL, DBL);
                    }
                }
                case MIDDLE -> {
                    // Construct UM layer pieces
                    {
                        EdgePiece UFM = new EdgePiece(new Color[] {Color.GREEN, Color.WHITE});
                        CenterPiece UEM = new CenterPiece(Color.WHITE);
                        EdgePiece UBM = new EdgePiece(new Color[] {Color.BLUE, Color.WHITE});
                        upLayer = new yLayer(UFM, UEM, UBM);
                    }

                    // Construct EM layer pieces
                    {
                        CenterPiece EFM = new CenterPiece(Color.GREEN);
                        // The ESM piece would be the core, so it'll be set to null and rejected in the getPiece() method.
                        CenterPiece EBM = new CenterPiece(Color.BLUE);
                        equatorLayer = new yLayer(EFM, null, EBM);
                    }

                    // Construct DM layer pieces
                    {
                        EdgePiece DFM = new EdgePiece(new Color[] {Color.GREEN, Color.YELLOW});
                        CenterPiece DEM = new CenterPiece(Color.YELLOW);
                        EdgePiece DBM = new EdgePiece(new Color[] {Color.BLUE, Color.YELLOW});
                        downLayer = new yLayer(DFM, DEM, DBM);
                    }
                }
                case RIGHT -> {
                    // Construct UR layer pieces
                    {
                        CornerPiece UFR = new CornerPiece(new Color[] {Color.WHITE, Color.GREEN, Color.RED});
                        EdgePiece UER = new EdgePiece(new Color[] {Color.WHITE, Color.RED});
                        CornerPiece UBR = new CornerPiece(new Color[] {Color.WHITE, Color.BLUE, Color.RED});
                        upLayer = new yLayer(UFR, UER, UBR);
                    }

                    // Construct ER layer pieces
                    {
                        EdgePiece EFR = new EdgePiece(new Color[] {Color.GREEN, Color.RED});
                        CenterPiece ESR = new CenterPiece(Color.RED);
                        EdgePiece EBR = new EdgePiece(new Color[] {Color.BLUE, Color.RED});
                        equatorLayer = new yLayer(EFR, ESR, EBR);
                    }

                    // Construct DR layer pieces
                    {
                        CornerPiece DFR = new CornerPiece(new Color[] {Color.YELLOW, Color.GREEN, Color.RED});
                        EdgePiece DER = new EdgePiece(new Color[] {Color.YELLOW, Color.RED});
                        CornerPiece DBR = new CornerPiece(new Color[] {Color.YELLOW, Color.BLUE, Color.RED});
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
         * Get a single yLayer of this xLayer.
         * @param layer Up/Equator/Down
         * @return The yLayer.
         */
        public yLayer get(Rotation layer) {
            if ((layer != Rotation.UP) && (layer != Rotation.EQUATOR) && (layer != Rotation.DOWN)) {
                throw new IllegalArgumentException("layer must be one of: UP, EQUATOR, DOWN");
            }
            return layers.get(layer);
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
                    System.out.println("(Core position skipped)"); // The only null position will be the core
                }
            }
        }

        /**
         * Get a single piece of this yLayer.
         * @param layer Front/Standing/Back
         * @return The piece.
         */
        public Piece get(Rotation layer) {
            if ((layer != Rotation.FRONT) && (layer != Rotation.STANDING) && (layer != Rotation.BACK)) {
                throw new IllegalArgumentException("layer must be one of: FRONT, STANDING, BACK");
            }
            return pieces.get(layer);
        }
    }

    /**
     * Get a single piece. All parameters are move notation. Refer to singular pieces by
     * using the layers that the piece is on, for example UFR refers to the top right
     * piece on the front. The parameters of this function act the same way, although
     * importantly this uses an XYZ system, so UFR would actually be RUF. This may be
     * changed in the future.
     * @param x Left/Middle/Right: Which layer on the x co-ordinate the piece is on
     * @param y Up/Equator/Down: Which layer on the y co-ordinate the piece is on
     * @param z Front/Standing/Back: Which layer on the z co-ordinate the piece is on
     * @return The piece
     */
    public Piece getPiece(Rotation x, Rotation y, Rotation z) {
        Piece piece;
        xLayer xLayer;
        yLayer yLayer;
        switch (x) {
            case LEFT, RIGHT -> xLayer = layers.get(x);
            case MIDDLE -> {
                // Check if the core is being chosen
                if ((y == Rotation.EQUATOR) && (z == Rotation.STANDING)) {
                    throw new IllegalArgumentException("tried to get the core at MIDDLE, EQUATOR, STANDING, which is not a piece.");
                }
                else {
                    xLayer = layers.get(x);
                }
            }
            default -> throw new IllegalArgumentException("Invalid X co-ordinate: " + x);
        }
        switch (y) {
            case UP, EQUATOR, DOWN -> yLayer = xLayer.get(y);
            default -> throw new IllegalArgumentException("Invalid Y co-ordinate: " + y);
        }
        switch (z) {
            case FRONT, STANDING, BACK -> piece = yLayer.get(z);
            default -> throw new IllegalArgumentException("Invalid Z co-ordinate: " + z);
        }
        return piece;
    }

    /**
     * Simulates one R move. This is a helper method for the move() method.
     * @return The cube.
     */
    public Cube moveR() {
        // todo
        xLayer rightLayer = layers.get(Rotation.RIGHT);

        // Get pieces
        Piece UFR = new CornerPiece((CornerPiece) getPiece(Rotation.RIGHT, Rotation.UP, Rotation.FRONT));
        Piece UBR = new CornerPiece((CornerPiece) getPiece(Rotation.RIGHT, Rotation.UP, Rotation.BACK));
        Piece DBR = new CornerPiece((CornerPiece) getPiece(Rotation.RIGHT, Rotation.DOWN, Rotation.BACK));
        Piece DFR = new CornerPiece((CornerPiece) getPiece(Rotation.RIGHT, Rotation.DOWN, Rotation.FRONT));

        Piece USR = new EdgePiece((EdgePiece) getPiece(Rotation.RIGHT, Rotation.UP, Rotation.STANDING));
        Piece EBR = new EdgePiece((EdgePiece) getPiece(Rotation.RIGHT, Rotation.EQUATOR, Rotation.BACK));
        Piece DSR = new EdgePiece((EdgePiece) getPiece(Rotation.RIGHT, Rotation.DOWN, Rotation.STANDING));
        Piece EFR = new EdgePiece((EdgePiece) getPiece(Rotation.RIGHT, Rotation.EQUATOR, Rotation.FRONT));

        // Call move methods on them
        UFR.moveR();
        UBR.moveR();
        DBR.moveR();
        DFR.moveR();
        USR.moveR();
        EBR.moveR();
        DSR.moveR();
        EFR.moveR();

        // Cycle pieces around
        // rightLayer.get(yTarget).pieces.put(zTarget, x) // yzx
        rightLayer.get(Rotation.UP).pieces.put(Rotation.BACK, UFR); // UFR -> UBR
        rightLayer.get(Rotation.DOWN).pieces.put(Rotation.BACK, UBR); // UBR -> DBR
        rightLayer.get(Rotation.DOWN).pieces.put(Rotation.FRONT, DBR); // DBR -> DFR
        rightLayer.get(Rotation.UP).pieces.put(Rotation.FRONT, DFR); // DFR -> UFR

        rightLayer.get(Rotation.EQUATOR).pieces.put(Rotation.BACK, USR); // USR -> EBR
        rightLayer.get(Rotation.DOWN).pieces.put(Rotation.STANDING, EBR); // EBR -> DSR
        rightLayer.get(Rotation.EQUATOR).pieces.put(Rotation.FRONT, DSR); // DSR -> EFR
        rightLayer.get(Rotation.UP).pieces.put(Rotation.STANDING, EFR); // EFR -> USR

        return this;
    }

    public Cube move(Rotation type, boolean isPrime, boolean isDouble) {
        // todo
        if (isDouble) {
            move(type, isPrime, false);
        }
        return this;
    }

    public void display() {
        for (xLayer layer: layers.values()) {
            layer.display();
        }
    }
}
