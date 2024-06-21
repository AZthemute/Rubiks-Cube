import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        displayStuff(cube);
        cube.move(Rotation.CubeRotation.Z, false, false);
        displayStuff(cube);
        cube.moveR(false);
        displayStuff(cube);
        cube.move(Rotation.CubeRotation.Z, true, false);
        displayStuff(cube);
        cube.move(Rotation.CubeRotation.Z, false, false);
        displayStuff(cube);
        cube.moveR(false);
        displayStuff(cube);
        cube.move(Rotation.CubeRotation.Z, true, false);
        displayStuff(cube);

        Algorithm alg = new Algorithm("B R L' D2 B' U R' L2 F' U F2 U F2 R2 L2 F2 D' R2 B2 D B'");
    }

    public static void displayStuff(Cube cube) {
        cube.getPiece(Rotation.MIDDLE, Rotation.UP, Rotation.FRONT).display();
        cube.getPiece(Rotation.MIDDLE, Rotation.UP, Rotation.STANDING).display();
        cube.getPiece(Rotation.MIDDLE, Rotation.UP, Rotation.BACK).display();
    }
}