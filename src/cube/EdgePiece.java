package cube;

import types.Color;
import types.PieceType;

import java.util.HashMap;

public class EdgePiece extends Piece {
    /**
     * @param colors Unordered array of colors that the piece contains.
     */
    public EdgePiece(Color[] colors) {
        super(buildPieceColors(colors));
        if (colors.length != 2) {
            throw new IllegalArgumentException("An edge piece can only have 2 colours.");
        }
    }

    /**
     * Copy constructor
     */
    public EdgePiece(EdgePiece other) {
        super(new HashMap<>(other.colors));
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.EDGE;
    }
}
