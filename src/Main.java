import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        cube.getPiece(Rotation.RIGHT, Rotation.UP, Rotation.FRONT).display();
        cube.move(Rotation.CubeRotation.Z, true, false);
        cube.getPiece(Rotation.RIGHT, Rotation.UP, Rotation.FRONT).display();

        Algorithm alg = new Algorithm("B R L' D2 B' U R' L2 F' U F2 U F2 R2 L2 F2 D' R2 B2 D B'");
    }
}