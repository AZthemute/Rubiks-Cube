import java.util.HashMap;

public class CenterPiece extends Piece {
    public CenterPiece(HashMap<Rotation, Color> colors) {
        super(colors);
        // todo: throw IllegalArgumentException if there is not 1 color present
    }

    /**
     * @param color The piece color.
     */
    public CenterPiece(Color color) {
        super(buildPieceColors(new Color[] {color}));
    }

    /**
     * Copy constructor
     */
    public CenterPiece(CenterPiece other) {
        super(new HashMap<>(other.colors));
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CENTER;
    }
}
