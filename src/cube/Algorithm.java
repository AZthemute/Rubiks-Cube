package cube;

import types.Rotation;

import java.util.ArrayList;

/**
 * Represents a set of moves on the cube. May be turned into static later.
 */
public class Algorithm {
    private ArrayList<Move> moves = new ArrayList<>();

    /**
     * Construct a new Algorithm. This currently does not support wide
     * notation, such as r or 2R. Additionally, R2' is treated as R2 since
     * these result in the same thing when executed.
     * @param alg The algorithm, e.g. R' L2 D U' B2 U2
     */
    public Algorithm(String alg) {
        String[] splitAlgorithm = alg.split(" ");
        for (String move: splitAlgorithm) {
            if (move.length() > 2) throw createIllegalMoveException(move);
            if (move.length() == 0) continue; // In case of typos creating a double  space

            Rotation moveType;
            boolean isPrime = false;
            boolean isDouble = false;
            // Choose the correct move type
            switch (move.charAt(0)) {
                case 'U' -> moveType = Rotation.UP;
                case 'E' -> moveType = Rotation.EQUATOR;
                case 'D' -> moveType = Rotation.DOWN;
                case 'F' -> moveType = Rotation.FRONT;
                case 'S' -> moveType = Rotation.STANDING;
                case 'B' -> moveType = Rotation.BACK;
                case 'L' -> moveType = Rotation.LEFT;
                case 'M' -> moveType = Rotation.MIDDLE;
                case 'R' -> moveType = Rotation.RIGHT;
                default -> throw createIllegalMoveException(move);
            }

            // Check modifiers, if any
            if (move.length() == 2) {
                if (move.charAt(1) == '\'') {
                    isPrime = true;
                }
                else if (move.charAt(1) == '2') {
                    isDouble = true;
                }
                else {
                    throw createIllegalMoveException(move);
                }
            }
            // todo: treat R2' as R2, R3 = R', etc
            moves.add(new Move(moveType, isPrime, isDouble));
        }
    }

    /**
     * Executes this algorithm on a cube.
     * @param cube The cube.
     * @return The cube.
     */
    public Cube execute(Cube cube) {
        // todo: parse moves array and call Cube functions based on what is parsed
        for (Move move : moves) {
            /*
            System.out.println(move.type);
            System.out.println(move.isPrime);
            System.out.println(move.isDouble);
             */
            cube.move(move.type, move.isPrime, move.isDouble);
        }
        return cube;
    }

    public String toCubeDB() {
        StringBuilder alg = new StringBuilder("");
        for (Move move : moves) {
            // todo
            switch (move.type) {
                case BACK -> alg.append('B');
                case RIGHT -> alg.append('R');
                case FRONT -> alg.append('F');
                case LEFT -> alg.append('L');
                case UP -> alg.append('U');
                case DOWN -> alg.append('D');
                case MIDDLE -> alg.append('M');
                case EQUATOR -> alg.append('E');
                case STANDING -> alg.append('S');
            }
            if (move.isDouble) alg.append('2');
            if (move.isPrime) alg.append('-');
            alg.append('_');
        }
        return alg.toString();
    }

    /**
     * Helper record for one move. Todo: allow CubeRotation
     */
    private record Move(Rotation type, boolean isPrime, boolean isDouble) {}

    /**
     * Hardcoded illegal move exception.
     * @param move
     * @return
     */
    private static IllegalArgumentException createIllegalMoveException(String move) {
        return new IllegalArgumentException("Move type \"" + move + "\" is invalid.");
    }
}
