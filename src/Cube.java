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
        Rotation xCoordinate;

        /**
         * Automatic constructor that fills in the pieces based
         * on the white front, green up position.
         * @param xCoordinate
         */
        public xLayer(Rotation xCoordinate) {
            this.xCoordinate = xCoordinate;

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
         * Up, Equator, Down
         * @param layers
         */
        public xLayer(HashMap<Rotation, yLayer> layers) {
            this.layers = layers;
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

        public void moveR(boolean isPrime) {
            // Hashmap of temp pieces to avoid a lot of declarations
            HashMap<String, Piece> tempPieces = new HashMap<>();
            // todo: double Z rotation if prime (could be on entire cube)
            if (this.xCoordinate == Rotation.MIDDLE) {
                // todo: move the pieces properly (they just need different instantiation)
                tempPieces.put("UFx", new EdgePiece((EdgePiece) getPiece(this.xCoordinate, Rotation.UP, Rotation.FRONT)));
                tempPieces.put("UBx", new EdgePiece((EdgePiece) getPiece(this.xCoordinate, Rotation.UP, Rotation.BACK)));
                tempPieces.put("DBx", new EdgePiece((EdgePiece) getPiece(this.xCoordinate, Rotation.DOWN, Rotation.BACK)));
                tempPieces.put("DFx", new EdgePiece((EdgePiece) getPiece(this.xCoordinate, Rotation.DOWN, Rotation.FRONT)));

                tempPieces.put("USx", new CenterPiece((CenterPiece) getPiece(this.xCoordinate, Rotation.UP, Rotation.STANDING)));
                tempPieces.put("EBx", new CenterPiece((CenterPiece) getPiece(this.xCoordinate, Rotation.EQUATOR, Rotation.BACK)));
                tempPieces.put("DSx", new CenterPiece((CenterPiece) getPiece(this.xCoordinate, Rotation.DOWN, Rotation.STANDING)));
                tempPieces.put("EFx", new CenterPiece((CenterPiece) getPiece(this.xCoordinate, Rotation.EQUATOR, Rotation.FRONT)));
            }
            else {
                tempPieces.put("UFx", new CornerPiece((CornerPiece) getPiece(this.xCoordinate, Rotation.UP, Rotation.FRONT)));
                tempPieces.put("UBx", new CornerPiece((CornerPiece) getPiece(this.xCoordinate, Rotation.UP, Rotation.BACK)));
                tempPieces.put("DBx", new CornerPiece((CornerPiece) getPiece(this.xCoordinate, Rotation.DOWN, Rotation.BACK)));
                tempPieces.put("DFx", new CornerPiece((CornerPiece) getPiece(this.xCoordinate, Rotation.DOWN, Rotation.FRONT)));

                tempPieces.put("USx", new EdgePiece((EdgePiece) getPiece(this.xCoordinate, Rotation.UP, Rotation.STANDING)));
                tempPieces.put("EBx", new EdgePiece((EdgePiece) getPiece(this.xCoordinate, Rotation.EQUATOR, Rotation.BACK)));
                tempPieces.put("DSx", new EdgePiece((EdgePiece) getPiece(this.xCoordinate, Rotation.DOWN, Rotation.STANDING)));
                tempPieces.put("EFx", new EdgePiece((EdgePiece) getPiece(this.xCoordinate, Rotation.EQUATOR, Rotation.FRONT)));
            }

            // Cycle pieces around
            // rightLayer.get(yTarget).pieces.put(zTarget, x) // yzx
            layers.get(Rotation.UP).pieces.put(Rotation.BACK, tempPieces.get("UFx")); // UFx -> UBx
            layers.get(Rotation.DOWN).pieces.put(Rotation.BACK, tempPieces.get("UBx")); // UBx -> DBx
            layers.get(Rotation.DOWN).pieces.put(Rotation.FRONT, tempPieces.get("DBx")); // DBx -> DFx
            layers.get(Rotation.UP).pieces.put(Rotation.FRONT, tempPieces.get("DFx")); // DFx -> UFx

            layers.get(Rotation.EQUATOR).pieces.put(Rotation.BACK, tempPieces.get("USx")); // USx -> EBx
            layers.get(Rotation.DOWN).pieces.put(Rotation.STANDING, tempPieces.get("EBx")); // EBx -> DSx
            layers.get(Rotation.EQUATOR).pieces.put(Rotation.FRONT, tempPieces.get("DSx")); // DSx -> EFx
            layers.get(Rotation.UP).pieces.put(Rotation.STANDING, tempPieces.get("EFx")); // EFx -> USx
            for (yLayer layer: layers.values()) {
                layer.moveR(isPrime);
            }
        }

        public void move(Rotation.CubeRotation move, boolean isPrime, boolean isDouble) {
            for (yLayer layer: layers.values()) {
                layer.move(move, isPrime, isDouble);
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

        public void moveR(boolean isPrime) {
            for (Piece piece: pieces.values()) {
                if (piece != null) {
                    piece.moveR(isPrime);
                }
            }
        }

        public void move(Rotation.CubeRotation move, boolean isPrime, boolean isDouble) {
            for (Piece piece: pieces.values()) {
                if (piece != null) {
                    piece.move(move, isPrime, isDouble);
                }
            }
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
    public Cube moveR(boolean isPrime) {
        if (isPrime) {
            move(Rotation.CubeRotation.Z, true, true);
        }
        layers.get(Rotation.RIGHT).moveR(isPrime);
        return this;
    }

    /**
     * Simulate a move on a single piece.
     * @param move The type of move, except rotations (this has an overloaded method).
     * @param isPrime If the move is prime or not.
     * @param isDouble If the move is double or not.
     */
    public Cube move(Rotation move, boolean isPrime, boolean isDouble) {
        // todo
        if (isDouble) {
            move(move, isPrime, false);
        }
        switch (move) {
            case BACK -> {
            }
            case RIGHT -> moveR(isPrime);
            case FRONT -> {
            }
            case LEFT -> {
            }
            case UP -> {
                if (!isPrime) {
                    move(Rotation.CubeRotation.Z, false, isDouble);
                    moveR(false);
                    move(Rotation.CubeRotation.Z, true, isDouble);
                }
                else {
                    move(Rotation.CubeRotation.Z, true, isDouble);
                    moveR(true);
                    move(Rotation.CubeRotation.Z, false, isDouble);
                }
            }
            case DOWN -> {
                if (!isPrime) {
                    move(Rotation.CubeRotation.Z, true, isDouble);
                    moveR(false);
                    move(Rotation.CubeRotation.Z, false, isDouble);
                }
                else {
                    move(Rotation.CubeRotation.Z, false, isDouble);
                    moveR(true);
                    move(Rotation.CubeRotation.Z, true, isDouble);
                }
            }
            case MIDDLE -> {
            }
            case EQUATOR -> {
            }
            case STANDING -> {
            }
        }
        return this;
    }

    /**
     * Rotate the entire cube.
     * @param move X/Y/Z
     * @param isPrime If the move is prime or not.
     * @param isDouble If the move is double or not.
     */
    public Cube move(Rotation.CubeRotation move, boolean isPrime, boolean isDouble) {
        if (isDouble) {
            move(move, isPrime, false);
        }
        HashMap<Rotation, xLayer> newLayers = new HashMap<>();
        xLayer cubeLeftLayer = layers.get(Rotation.LEFT);
        xLayer cubeMiddleLayer = layers.get(Rotation.MIDDLE);
        xLayer cubeRightLayer = layers.get(Rotation.RIGHT);
        switch (move) {
            case X -> {
                for (xLayer layer: layers.values()) {
                    layer.moveR(isPrime);
                }
            }
            case Y -> {
                // Do a U', E, D move if not prime
                move(Rotation.UP, !isPrime, isDouble);
                move(Rotation.EQUATOR, isPrime, isDouble);
                move(Rotation.DOWN, isPrime, isDouble);
            }
            case Z -> {
                if (!isPrime) {
                    newLayers.put(Rotation.LEFT, new xLayer(new HashMap<>() {
                        {
                            put(Rotation.UP, cubeLeftLayer.get(Rotation.DOWN)); // DL -> UL
                            put(Rotation.EQUATOR, cubeMiddleLayer.get(Rotation.DOWN)); // DM -> EL
                            put(Rotation.DOWN, cubeRightLayer.get(Rotation.DOWN)); // DR -> DL
                        }
                    }));
                    newLayers.put(Rotation.MIDDLE, new xLayer(new HashMap<>() {
                        {
                            put(Rotation.UP, cubeLeftLayer.get(Rotation.EQUATOR)); // EL -> UM
                            put(Rotation.EQUATOR, cubeMiddleLayer.get(Rotation.EQUATOR)); // EM -> EM
                            put(Rotation.DOWN, cubeRightLayer.get(Rotation.EQUATOR)); // ER -> DM
                        }
                    }));
                    newLayers.put(Rotation.RIGHT, new xLayer(new HashMap<>() {
                        {
                            put(Rotation.UP, cubeLeftLayer.get(Rotation.UP)); // UL -> UR
                            put(Rotation.EQUATOR, cubeMiddleLayer.get(Rotation.UP)); // UM -> ER
                            put(Rotation.DOWN, cubeRightLayer.get(Rotation.UP)); // UR -> DR
                        }
                    }));
                }
                else {
                    newLayers.put(Rotation.LEFT, new xLayer(new HashMap<>() {
                        {
                            put(Rotation.UP, cubeRightLayer.get(Rotation.UP)); // UR -> UL
                            put(Rotation.EQUATOR, cubeMiddleLayer.get(Rotation.UP)); // UM -> EL
                            put(Rotation.DOWN, cubeLeftLayer.get(Rotation.UP)); // UL -> DL
                        }
                    }));
                    newLayers.put(Rotation.MIDDLE, new xLayer(new HashMap<>() {
                        {
                            put(Rotation.UP, cubeRightLayer.get(Rotation.EQUATOR)); // ER -> UM
                            put(Rotation.EQUATOR, cubeMiddleLayer.get(Rotation.EQUATOR)); // EM -> EM
                            put(Rotation.DOWN, cubeLeftLayer.get(Rotation.EQUATOR)); // EL -> DM
                        }
                    }));
                    newLayers.put(Rotation.RIGHT, new xLayer(new HashMap<>() {
                        {
                            put(Rotation.UP, cubeRightLayer.get(Rotation.DOWN)); // DR -> UR
                            put(Rotation.EQUATOR, cubeMiddleLayer.get(Rotation.DOWN)); // DM -> ER
                            put(Rotation.DOWN, cubeLeftLayer.get(Rotation.DOWN)); // DL -> DR
                        }
                    }));
                }
            }
        }
        // Rotate method the pieces
        for (xLayer layer: newLayers.values()) {
            layer.move(move, isPrime, isDouble);
        }
        this.layers = newLayers;
        return this;
    }

    public void display() {
        for (xLayer layer: layers.values()) {
            layer.display();
        }
    }
}
