/**
 * In cubing notation, these would be B, R, F, L, U, D, M, E, S.
 * These can be used as move notation, or to refer to faces.
 * MIDDLE, EQUATOR, and STANDING should only be used as move/layer notation.
 */
public enum Rotation {
    BACK, RIGHT, FRONT, LEFT, UP, DOWN, MIDDLE, EQUATOR, STANDING;

    /**
     * Full cube rotations. These are only to be used as move notation when
     * rotating the full cube.
     */
    public enum CubeRotation {
        X, Y, Z
    }
}
