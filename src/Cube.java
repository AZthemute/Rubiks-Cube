import java.util.HashMap;

public class Cube {
    private HashMap<Rotation, xLayer> pieces;

    /**
     * Creates a solved cube.
     */
    public Cube() {
        xLayer leftLayer = new xLayer();
        xLayer middleLayer = constructXLayer(Rotation.MIDDLE);
        xLayer rightLayer = constructXLayer(Rotation.RIGHT);
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

        private EdgePiece constructEdgePiece(Color[] colors) {
            if (colors.length != 2) {
                throw new IllegalArgumentException("An edge piece can only have 2 colours.");
            }

            return new EdgePiece(buildPieceColors(colors));
        }

        private CornerPiece constructCornerPiece(Color[] colors) {
            if (colors.length != 3) {
                throw new IllegalArgumentException("A corner piece can only have 3 colours.");
            }

            return new CornerPiece(buildPieceColors(colors));
        }

        public xLayer(Rotation xCoordinate) {
            yLayer upLayer;
            yLayer equatorLayer;
            yLayer downLayer;

            switch (xCoordinate) {
                case LEFT -> {
                    // Construct UL layer pieces
                    {
                        CornerPiece UFL = new CornerPiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                                put(Rotation.FRONT, Color.GREEN);
                            }
                        });
                        EdgePiece UEL = new EdgePiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                            }
                        });
                        CornerPiece UBL = new CornerPiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                                put(Rotation.BACK, Color.BLUE);
                            }
                        });
                        upLayer = new yLayer(UFL, UEL, UBL);
                    }

                    // Construct EL layer pieces
                    {
                        EdgePiece EFL = new EdgePiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.FRONT, Color.GREEN);
                            }
                        });
                        CenterPiece ESL = new CenterPiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                            }
                        });
                        EdgePiece EBL = new EdgePiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.BACK, Color.BLUE);
                            }
                        });
                        equatorLayer = new yLayer(EFL, ESL, EBL);
                    }

                    // Construct DL layer pieces
                    {
                        CornerPiece DFL = constructCornerPiece(new Color[] {Color.ORANGE, Color.YELLOW, Color.GREEN});
                        EdgePiece DEL = constructEdgePiece(new Color[] {Color.ORANGE, Color.YELLOW});
                        CornerPiece DBL = constructCornerPiece(new Color[] {Color.ORANGE, Color.YELLOW, Color.GREEN});
                        upLayer = new yLayer(DFL, DEL, DBL);
                    }
                }
                // Construct UM layer pieces
                case MIDDLE -> {
                    EdgePiece UML = new EdgePiece(new HashMap<>() {
                        {
                            put(Rotation.LEFT, Color.ORANGE);
                            put(Rotation.UP, Color.WHITE);
                            put(Rotation.FRONT, Color.GREEN);
                        }
                    });
                    CenterPiece MFL = new CenterPiece(new HashMap<>() {
                        {
                            put(Rotation.LEFT, Color.ORANGE);
                            put(Rotation.UP, Color.WHITE);
                        }
                    });
                    EdgePiece DFL = new EdgePiece(new HashMap<>() {
                        {
                            put(Rotation.LEFT, Color.ORANGE);
                            put(Rotation.UP, Color.WHITE);
                            put(Rotation.BACK, Color.BLUE);
                        }
                    });
                    upLayer = new yLayer(UML, MFL, DFL);
                }
                // Construct UR layer pieces
                case RIGHT -> {
                    CornerPiece UFR = new CornerPiece(new HashMap<>() {
                        {
                            put(Rotation.RIGHT, Color.RED);
                            put(Rotation.UP, Color.WHITE);
                            put(Rotation.FRONT, Color.GREEN);
                        }
                    });
                    EdgePiece MFR = new EdgePiece(new HashMap<>() {
                        {
                            put(Rotation.RIGHT, Color.RED);
                            put(Rotation.UP, Color.WHITE);
                        }
                    });
                    CornerPiece DFR = new CornerPiece(new HashMap<>() {
                        {
                            put(Rotation.RIGHT, Color.RED);
                            put(Rotation.UP, Color.WHITE);
                            put(Rotation.BACK, Color.BLUE);
                        }
                    });
                    upLayer = new yLayer(UFR, MFR, DFR);
                }
            }

            layers = new HashMap<>() {
                {
                    put(Rotation.UP, upLayer);
                    put(Rotation.EQUATOR, equatorLayer);
                    put(Rotation.DOWN, downLayer);
                }
            };
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
    }


    /**
     * Helper method for constructor.
     */
    private static HashMap<Rotation, HashMap<Rotation, Piece>> constructXLayer(Rotation xCoordinate) {
        /*
         Since these are only used for the construction of the cube, hardcoding the pieces
         to construct with is okay.
        */
        switch (xCoordinate) {

        }

        HashMap<Rotation, HashMap<Rotation, Piece>> layer = new HashMap<>() {
            {
                put(Rotation.UP, constructZLayer(xCoordinate, Rotation.FRONT));
                put(Rotation.EQUATOR, equatorLayer);
                put(Rotation.DOWN, downLayer);
            }
        };

        return layer;
    }

    /**
     * Helper method for constructor.
     */
    private static HashMap<Rotation, HashMap<Rotation, Piece>> constructYLayer(Rotation xCoordinate, Rotation yCoordinate) {
        HashMap<Rotation, HashMap<Rotation, Piece>> layer;
        HashMap<Rotation, Piece> upLayer;
        HashMap<Rotation, Piece> equatorLayer;
        HashMap<Rotation, Piece> downLayer;

        // Each branch of the y co-ordinate has a branch of the x co-ordinate
        switch (yCoordinate) {
            // U layer
            case UP -> {
                switch (xCoordinate) {
                    // Construct UL layer pieces
                    case LEFT -> {
                        CornerPiece UFL = new CornerPiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                                put(Rotation.FRONT, Color.GREEN);
                            }
                        });
                        EdgePiece MFL = new EdgePiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                            }
                        });
                        CornerPiece DFL = new CornerPiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                                put(Rotation.BACK, Color.BLUE);
                            }
                        });
                        upLayer = constructZLayer(UFL, MFL, DFL);
                    }
                    // Construct UM layer pieces
                    case MIDDLE -> {
                        CornerPiece UML = new CornerPiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                                put(Rotation.FRONT, Color.GREEN);
                            }
                        });
                        EdgePiece MFL = new EdgePiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                            }
                        });
                        CornerPiece DFL = new CornerPiece(new HashMap<>() {
                            {
                                put(Rotation.LEFT, Color.ORANGE);
                                put(Rotation.UP, Color.WHITE);
                                put(Rotation.BACK, Color.BLUE);
                            }
                        });
                        upLayer = constructZLayer(UML, MFL, DFL);
                    }
                }
            }
        }
        HashMap<Rotation, HashMap<Rotation, Piece>> layer = new HashMap<>() {
            {
                put(Rotation.UP, upLayer);
                put(Rotation.EQUATOR, equatorLayer);
                put(Rotation.DOWN, downLayer);
            }
        };
        return layer;
    }

    /**
     * Helper method for constructor.
     */
    private static HashMap<Rotation, Piece> constructZLayer(Piece frontPiece, Piece standingPiece, Piece backPiece) {
        return new HashMap<>() {
            {
                put(Rotation.FRONT, frontPiece);
                put(Rotation.STANDING, standingPiece);
                put(Rotation.BACK, backPiece);
            }
        };
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
        Piece piece;
        HashMap<Rotation, HashMap<Rotation, Piece>> xLayer;
        HashMap<Rotation, Piece> yLayer;
        switch (x) {
            case LEFT, MIDDLE, RIGHT -> xLayer = pieces.get(x);
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

    public Cube move(Rotation type, boolean isPrime) {
        // todo
        return this;
    }
}
