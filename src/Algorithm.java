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
            if (move.length() > 2) {
                throw new IllegalArgumentException("Move " + move + " is invalid.");
            }
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
                default -> throw new IllegalArgumentException("Move type " + move.charAt(0) + " is invalid.");
            }

            // Check modifiers, if any
            if (move.length() == 2) {
                if (move.charAt(1) == '\'') {
                    isPrime = true;
                }
                else if (move.charAt(1) == '2') {
                    isDouble = true;
                }
            }
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
            cube.move(move.type, move.isPrime, move.isDouble);
        }
        return cube;
    }

        /**
         * Helper class for one move.
         */
        private record Move(Rotation type, boolean isPrime, boolean isDouble) {
    }
}
