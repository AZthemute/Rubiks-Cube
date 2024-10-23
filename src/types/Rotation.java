package types;

/**
 * In cubing notation, these would be B, R, F, L, U, D, M, E, S.
 * These can be used as move notation, or to refer to faces or layers.
 * MIDDLE, EQUATOR, and STANDING should not be used to refer to faces.
 */
public enum Rotation implements MoveOnCube {
    BACK, RIGHT, FRONT, LEFT, UP, DOWN, MIDDLE, EQUATOR, STANDING;

    @Override
    public char toChar() {
        char c;
        switch (this) {
            case BACK -> c = 'B';
            case RIGHT -> c = 'R';
            case FRONT -> c = 'F';
            case LEFT -> c = 'L';
            case UP -> c = 'U';
            case DOWN -> c = 'D';
            case MIDDLE -> c = 'M';
            case EQUATOR -> c = 'E';
            case STANDING -> c = 'S';
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
        return c;
    }

    /**
     * Full cube rotations. These are only to be used as move notation when
     * rotating the full cube.
     */
    public enum CubeRotation implements MoveOnCube {
        X, Y, Z;

        @Override
        public char toChar() {
            char c;
            switch (this) {
                case X -> c = 'x';
                case Y -> c = 'y';
                case Z -> c = 'z';
                default -> throw new IllegalStateException("Unexpected value: " + this);
            }
            return c;
        }
    }
}
