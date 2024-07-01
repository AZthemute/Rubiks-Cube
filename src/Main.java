public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        //displayStuff(cube);
        //cube.move(Rotation.MIDDLE, false, false);
        //displayStuff(cube);

        Algorithm alg = new Algorithm("R");

        // alg.execute(cube);

        new GUI(cube);
        //displayStuff(cube);
    }

    private static void displayStuff(Cube cube) {
        cube.getPiece(Rotation.MIDDLE, Rotation.UP, Rotation.FRONT).display();
        cube.getPiece(Rotation.MIDDLE, Rotation.UP, Rotation.BACK).display();
        cube.getPiece(Rotation.MIDDLE, Rotation.DOWN, Rotation.FRONT).display();
    }
}