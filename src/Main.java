public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube();
        //displayStuff(cube);
        //cube.move(Rotation.MIDDLE, false, false);
        //displayStuff(cube);

        Algorithm alg = new Algorithm("F");

        new GUI(cube);

        cube.move(Rotation.MIDDLE, false, false);
        //displayStuff(cube);
    }

    private static void displayStuff(Cube cube) {
        cube.getPiece(Rotation.LEFT, Rotation.EQUATOR, Rotation.STANDING).display();
        cube.getPiece(Rotation.RIGHT, Rotation.EQUATOR, Rotation.FRONT).display();
        cube.getPiece(Rotation.MIDDLE, Rotation.EQUATOR, Rotation.BACK).display();
    }
}