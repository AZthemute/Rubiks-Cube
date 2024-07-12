package types;

/**
 * In cubing notation, these would be B, R, F, L, U, D, M, E, S.
 * These can be used as move notation, or to refer to faces or layers.
 * MIDDLE, EQUATOR, and STANDING should not be used to refer to faces.
 */
public enum Rotation implements MoveOnCube {
    BACK, RIGHT, FRONT, LEFT, UP, DOWN, MIDDLE, EQUATOR, STANDING;

    /**
     * Full cube rotations. These are only to be used as move notation when
     * rotating the full cube.
     */
    public enum CubeRotation implements MoveOnCube {
        X, Y, Z
    }
}
