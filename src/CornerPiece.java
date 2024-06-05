import java.util.HashMap;

public class CornerPiece extends Piece {
    public CornerPiece(HashMap<Rotation, Color> colors) {
        super(colors);
        // todo: throw IllegalArgumentException if there are not 3 colors present
    }

    /**
     * @param colors Unordered array of colors that the piece contains.
     */
    public CornerPiece(Color[] colors) {
        super(buildPieceColors(colors));
        if (colors.length != 3) {
            throw new IllegalArgumentException("A corner piece can only have 3 colours.");
        }
    }

    /**
     * Copy constructor
     */
    public CornerPiece(CornerPiece other) {
        super(new HashMap<>(other.colors));
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CORNER;
    }
}
