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

    public void execute() {
        // todo: parse moves array and call Cube functions based on what is parsed
    }

    /**
     * Helper class for one move.
     */
    private static class Move {
        Rotation type;
        boolean prime;
        public Move(Rotation type, boolean isPrime) {
            this.type = type;
            this.prime = isPrime;
        }
    }
}
