package cube;

import types.Color;
import types.PieceType;

import java.util.HashMap;

public class CenterPiece extends Piece {
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
