import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        displayStuff(cube);
        cube.move(Rotation.MIDDLE, false, false);
        displayStuff(cube);

        Algorithm alg = new Algorithm("B R L' D2 B' U R' L2 F' U F2 U F2 R2 L2 F2 D' R2 B2 D B'");
    }

    private static void displayStuff(Cube cube) {
        cube.getPiece(Rotation.LEFT, Rotation.UP, Rotation.FRONT).display();
        cube.getPiece(Rotation.LEFT, Rotation.UP, Rotation.STANDING).display();
        cube.getPiece(Rotation.LEFT, Rotation.UP, Rotation.BACK).display();
    }
}