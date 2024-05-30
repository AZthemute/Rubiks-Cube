import java.util.HashMap;

public class Cube {
    private HashMap<Rotation, HashMap<Rotation, HashMap<Rotation, Piece>>> pieces;

    /**
     * Creates a solved cube.
     */
    public Cube() {
        HashMap<Rotation, HashMap<Rotation, Piece>> leftLayer = constructYLayer();
        HashMap<Rotation, HashMap<Rotation, Piece>> middleLayer = constructYLayer();
        HashMap<Rotation, HashMap<Rotation, Piece>> rightLayer = constructYLayer();
        // The final cube layer: x co-ordinate
        pieces = new HashMap<>() {
            {
                put(Rotation.LEFT, leftLayer);
                put(Rotation.MIDDLE, middleLayer);
                put(Rotation.RIGHT, rightLayer);
            }
        };
    }

    // Helper method for constructor.
    private static HashMap<Rotation, HashMap<Rotation, Piece>> constructYLayer() {
        HashMap<Rotation, Piece> upLayer = constructZLayer();
        HashMap<Rotation, Piece> equatorLayer = constructZLayer();
        HashMap<Rotation, Piece> downLayer = constructZLayer();
        HashMap<Rotation, HashMap<Rotation, Piece>> layer = new HashMap<>() {
            {
                put(Rotation.UP, upLayer);
                put(Rotation.EQUATOR, equatorLayer);
                put(Rotation.DOWN, downLayer);
            }
        };
        return layer;
    }

    // Helper method for constructor.
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
     * @param x L/M/R: Which layer on the x co-ordinate the piece is on
     * @param y U/E/D: Which layer on the y co-ordinate the piece is on
     * @param z F/S/B: Which layer on the z co-ordinate the piece is on
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
