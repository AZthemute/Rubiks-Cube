import java.util.HashMap;

public class EdgePiece extends Piece {
    public EdgePiece(HashMap<Rotation, Color> colors) {
        super(colors);
        // todo: throw IllegalArgumentException if there are not 2 colors present
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.EDGE;
    }
}
