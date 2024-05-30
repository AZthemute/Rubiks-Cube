import java.util.HashMap;

public class CenterPiece extends Piece {
    public CenterPiece(HashMap<Rotation, Color> colors) {
        super(colors);
        // todo: throw IllegalArgumentException if there is not 1 color present
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CENTER;
    }
}
