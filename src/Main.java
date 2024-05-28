import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Rotation, Color> colors = new HashMap<>();
        colors.put(Rotation.UP, Color.WHITE);
        colors.put(Rotation.FRONT, Color.GREEN);
        colors.put(Rotation.RIGHT, Color.RED);
        colors.put(Rotation.DOWN, null);
        colors.put(Rotation.LEFT, null);
        colors.put(Rotation.BACK, null);
        CornerPiece test = new CornerPiece(colors);
        test.display();
        test.moveR();
        test.display();

        colors = new HashMap<>();
        colors.put(Rotation.UP, Color.WHITE);
        colors.put(Rotation.FRONT, null);
        colors.put(Rotation.RIGHT, Color.RED);
        colors.put(Rotation.DOWN, null);
        colors.put(Rotation.LEFT, null);
        colors.put(Rotation.BACK, Color.BLUE);
        test = new CornerPiece(colors);
        test.display();
        test.moveR();
        test.display();

        colors = new HashMap<>();
        colors.put(Rotation.UP, null);
        colors.put(Rotation.FRONT, Color.GREEN);
        colors.put(Rotation.RIGHT, Color.RED);
        colors.put(Rotation.DOWN, Color.YELLOW);
        colors.put(Rotation.LEFT, null);
        colors.put(Rotation.BACK, null);
        test = new CornerPiece(colors);
        test.display();
        test.moveR();
        test.display();

        colors = new HashMap<>();
        colors.put(Rotation.UP, null);
        colors.put(Rotation.FRONT, null);
        colors.put(Rotation.RIGHT, Color.RED);
        colors.put(Rotation.DOWN, Color.YELLOW);
        colors.put(Rotation.LEFT, null);
        colors.put(Rotation.BACK, Color.BLUE);
        test = new CornerPiece(colors);
        test.display();
        test.moveR();
        test.display();
    }
}