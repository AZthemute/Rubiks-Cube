import java.awt.*;
import java.util.HashMap;

public class CenterPiece extends Piece {
    public CenterPiece(HashMap<Rotation, Color> colors, Rotation facing) {
        super(colors, facing);
        // todo: throw IllegalArgumentException if there is not 1 color present
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CENTER;
    }
}
