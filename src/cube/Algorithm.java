package cube;

import types.MoveOnCube;
import types.Rotation;

import java.util.ArrayList;

/**
 * Represents a set of moves on the cube. May be turned into static later.
 */
public class Algorithm {
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Move<MoveOnCube>> moves = new ArrayList<>();

    /**
     * Construct a new Algorithm. This currently does not support the 2R notation.
     * Additionally, R2' is treated as R2 since
     * these result in the same thing when executed.
     * @param alg The algorithm, e.g. R' L2 D U' B2 U2
     */
    public Algorithm(String alg) {
        String[] splitAlgorithm = alg.split(" ");
        for (String move: splitAlgorithm) {
            if (move.length() > 3) throw createIllegalMoveException(move);
            if (move.isEmpty()) continue; // In case of typos creating a double  space

            MoveOnCube moveType;

            boolean isPrime = false;
            boolean isDouble = false;
            boolean isWide = false;

            // Choose the correct move type. The character is forced to uppercase
            // in order to easily support wide moves.
            switch (Character.toUpperCase(move.charAt(0))) {
                case 'U' -> moveType = Rotation.UP;
                case 'E' -> moveType = Rotation.EQUATOR;
                case 'D' -> moveType = Rotation.DOWN;
                case 'F' -> moveType = Rotation.FRONT;
                case 'S' -> moveType = Rotation.STANDING;
                case 'B' -> moveType = Rotation.BACK;
                case 'L' -> moveType = Rotation.LEFT;
                case 'M' -> moveType = Rotation.MIDDLE;
                case 'R' -> moveType = Rotation.RIGHT;
                case 'X' -> moveType = Rotation.CubeRotation.X;
                case 'Y' -> moveType = Rotation.CubeRotation.Y;
                case 'Z' -> moveType = Rotation.CubeRotation.Z;
                default -> throw createIllegalMoveException(move);
            }

            // Check modifiers, if any
            if (Character.isLowerCase(move.charAt(0))) isWide = true;

            if (move.length() == 2) {
                if (move.charAt(1) == '\'') isPrime = true;
                else if (move.charAt(1) == '2') isDouble = true;
                else if (move.charAt(1) == '3') isPrime = true;
                else throw createIllegalMoveException(move);
            }

            // R2' is functionally the same as R'
            if (move.length() == 3) {
                if (move.startsWith("2'", 1)) isDouble = true;
                else throw createIllegalMoveException(move);
            }

            // todo: treat R2' as R2, R3 = R', etc
            moves.add(new Move<>(moveType, isPrime, isDouble, isWide));
        }
    }

    /**
     * Executes this algorithm on a cube.
     * @param cube The cube to execute the algorithm on.
     */
    public void execute(Cube cube) {
        // todo: parse moves array and call Cube functions based on what is parsed
        for (Move<MoveOnCube> move : moves) {
            if (move.type.getClass() == Rotation.class) {
                cube.move((Rotation) move.type, move.isPrime, move.isDouble);
                if (move.isWide) cube.move(Rotation.MIDDLE, move.isPrimeForWide(), move.isDouble);
            }
            else cube.move((Rotation.CubeRotation) move.type, move.isPrime, move.isDouble);
        }
    }

    /**
     * @return The Algorithm formatted for CubeDB URLs
     */
    public String toCubeDB() {
        StringBuilder alg = new StringBuilder();
        for (Move<MoveOnCube> move : moves) {
            alg.append(move.toString());
            alg.append('_');
        }
        return alg.toString();
    }

    /**
     * @return The Algorithm formatted for CubeDB URLs
     */
    public String toString() {
        StringBuilder alg = new StringBuilder();
        for (Move<MoveOnCube> move : moves) {
            alg.append(move.toString());
            alg.append(' ');
        }
        return alg.substring(0, alg.length() - 1); // Strips off the last space
    }

    /**
     * @return The reverse of this Algorithm.
     */
    public Algorithm getReverse() {
        StringBuilder algString = new StringBuilder();
        for (Move<MoveOnCube> move : moves.reversed()) {
            algString.append(move.reverse().toString()).append(' ');
        }
        return new Algorithm(algString.toString());
    }

    /**
     * Helper record for one move. The type parameter must implement the MoveOnCube interface.
     */
    public record Move<T extends MoveOnCube>(T type, boolean isPrime, boolean isDouble, boolean isWide) {
        /**
         * Helper method for executing.
         */
        public boolean isPrimeForWide() {
            if ((type == Rotation.RIGHT) || (type == Rotation.BACK) || (type == Rotation.UP)) {return !isPrime;}
            return isPrime;
        }

        /**
         * @return The representation of this move as a string.
         */
        public String toString() {
            StringBuilder s = new StringBuilder();

            if (isWide) s.append(Character.toLowerCase(this.type.toChar()));
            else s.append(this.type.toChar());

            if (isDouble) s.append('2');
            if (isPrime) s.append('\'');
            return s.toString();
        }

        /**
         * Helper for getReverse() on Algorithm.
         * @return A new Move with the isPrime variable reversed.
         */
        public Move<MoveOnCube> reverse() {
            return new Move<>(this.type, !this.isPrime, this.isDouble, this.isWide);
        }
    }

    /**
     * Not in the Move class because stupid blah blah needs to be of type T
     * switch cases suck
     * @param m
     * @return
     */
    public char toChar(Move m) {
        char c;
        switch (m.type) {
            case Rotation.BACK -> c = 'B';
            case Rotation.RIGHT -> c = 'R';
            case Rotation.FRONT -> c = 'F';
            case Rotation.LEFT -> c = 'L';
            case Rotation.UP -> c = 'U';
            case Rotation.DOWN -> c = 'D';
            case Rotation.MIDDLE -> c = 'M';
            case Rotation.EQUATOR -> c = 'E';
            case Rotation.STANDING -> c = 'S';
            case Rotation.CubeRotation.X -> c = 'x';
            case Rotation.CubeRotation.Y -> c = 'y';
            case Rotation.CubeRotation.Z -> c = 'z';
            default -> throw new IllegalStateException("Unexpected value: " + m.type);
        }
        return c;
    }

    /**
     * Hardcoded illegal move exception.
     * @param move The illegal move.
     * @return The exception to be thrown.
     */
    private static IllegalArgumentException createIllegalMoveException(String move) {
        return new IllegalArgumentException("Move type \"" + move + "\" is invalid.");
    }
}
