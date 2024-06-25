import javax.swing.*;
import java.awt.*;

public class Face extends JPanel {
    Sticker[][] stickers;

    public Face(Sticker[][] stickers) {
        this.stickers = stickers;
    }

    public Face(Color[][] colors) {
        this.stickers = new Sticker[3][3];
        for (Color[] topLevelColors: colors) {
            for (Color color: topLevelColors) {
                if (color == null) continue;
                stickers[2][2] = new Sticker(50, color); // placeholder x. we'll change the position on the screen based on its position in the array. do something like x+i*width
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        System.out.println("Repainting face!");
        for (Sticker[] topLevelStickers: this.stickers) {
            for (Sticker sticker: topLevelStickers) {
                if (sticker == null) continue;
                sticker.paintComponent(graphics);
            }
        }
    }
}
