import GUI.GUI;
import cube.Cube;
import types.Rotation;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        new GUI(cube);
        ArrayList<String[]> algs = FileHandler.readIntoCSV("wv.txt");
        for (int i = 0; i < algs.size(); i++) {
            //System.out.println(Arrays.toString(algs.get(i)));
        }
    }

    private static void displayStuff(Cube cube) {
        cube.getPiece(Rotation.UP, Rotation.FRONT, Rotation.MIDDLE).display();
        cube.getPiece(Rotation.UP, Rotation.BACK, Rotation.MIDDLE).display();
        cube.getPiece(Rotation.DOWN, Rotation.FRONT, Rotation.MIDDLE).display();
    }
}