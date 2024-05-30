/**
 * Represents a set of moves on the cube.
 */
public class Algorithm {
    private Move[] moves;

    /**
     *
     * @param alg The algorithm.
     */
    public Algorithm(String alg) {
        // todo
    }

    public Cube execute(Cube cube) {
        // todo: parse moves array and call Cube functions based on what is parsed
        for (Move move : moves) {
            cube.move(move.type, move.isPrime);
        }
        return cube;
    }

    /**
     * Helper class for one move.
     */
    private static class Move {
        final Rotation type;
        final boolean isPrime;

        public Move(Rotation type, boolean isPrime) {
            this.type = type;
            this.isPrime = isPrime;
        }
    }
}
