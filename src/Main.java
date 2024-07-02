import GUI.GUI;
import cube.Cube;
import types.Rotation;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        new GUI(cube);
    }

    private static void displayStuff(Cube cube) {
        cube.getPiece(Rotation.UP, Rotation.FRONT, Rotation.MIDDLE).display();
        cube.getPiece(Rotation.UP, Rotation.BACK, Rotation.MIDDLE).display();
        cube.getPiece(Rotation.DOWN, Rotation.FRONT, Rotation.MIDDLE).display();
    }
}