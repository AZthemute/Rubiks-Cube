import java.util.HashMap;

public class CornerPiece extends Piece {
    public CornerPiece(HashMap<Rotation, Color> colors) {
        super(colors);
        // todo: throw IllegalArgumentException if there are not 3 colors present
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CORNER;
    }
}
