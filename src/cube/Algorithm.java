package cube;

import types.MoveOnCube;
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
            if (move.length() > 3) throw createIllegalMoveException(move);
            if (move.length() == 0) continue; // In case of typos creating a double  space

            Rotation moveType = null;
            // because private classes are weird
            Rotation.CubeRotation rotationMoveType = null;

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
                case 'x' -> rotationMoveType = Rotation.CubeRotation.X;
                case 'y' -> rotationMoveType = Rotation.CubeRotation.Y;
                case 'z' -> rotationMoveType = Rotation.CubeRotation.Z;
                default -> throw createIllegalMoveException(move);
            }

            // Check modifiers, if any
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
            if (moveType != null) moves.add(new Move(moveType, isPrime, isDouble));
            else moves.add(new Move(rotationMoveType, isPrime, isDouble));
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
            if (move.type.getClass() == Rotation.class) cube.move((Rotation) move.type, move.isPrime, move.isDouble);
            else cube.move((Rotation.CubeRotation) move.type, move.isPrime, move.isDouble);
        }
        return cube;
    }

    public String toCubeDB() {
        StringBuilder alg = new StringBuilder("");
        for (Move move : moves) {
            // todo
            if (move.type.equals(Rotation.BACK)) alg.append('B');
            else if (move.type.equals(Rotation.RIGHT)) alg.append('R');
            else if (move.type.equals(Rotation.FRONT)) alg.append('F');
            else if (move.type.equals(Rotation.LEFT)) alg.append('L');
            else if (move.type.equals(Rotation.UP)) alg.append('U');
            else if (move.type.equals(Rotation.DOWN)) alg.append('D');
            else if (move.type.equals(Rotation.MIDDLE)) alg.append('M');
            else if (move.type.equals(Rotation.EQUATOR)) alg.append('E');
            else if (move.type.equals(Rotation.STANDING)) alg.append('S');
            else if (move.type.equals(Rotation.CubeRotation.X)) alg.append('x');
            else if (move.type.equals(Rotation.CubeRotation.Y)) alg.append('y');
            else if (move.type.equals(Rotation.CubeRotation.Z)) alg.append('z');

            if (move.isDouble) alg.append('2');
            if (move.isPrime) alg.append('-');
            alg.append('_');
        }
        return alg.toString();
    }

    /**
     * Helper record for one move. Todo: allow CubeRotation
     */
    public record Move<T extends MoveOnCube>(T type, boolean isPrime, boolean isDouble) implements MoveOnCube {}

    /**
     * Hardcoded illegal move exception.
     * @param move
     * @return
     */
    private static IllegalArgumentException createIllegalMoveException(String move) {
        return new IllegalArgumentException("Move type \"" + move + "\" is invalid.");
    }
}
