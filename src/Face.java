import javax.swing.*;
import java.awt.*;

public class Face extends JPanel {
    Sticker[][] stickers;

    public Face(Sticker[][] stickers) {
        this.stickers = stickers;
    }

    public Face(Color[][] colors) {
        this.stickers = new Sticker[3][3];
        for (int i = 0; i <= 2; i++) {
            Color[] topLevelColors = colors[i];
            for (int j = 0; j <= 2; j++) {
                Color color = topLevelColors[j];
                if (color == null) {
                    System.out.println(i);
                    System.out.println(j);
                    continue;
                };
                stickers[i][j] = new Sticker(i*50, j*50, color);
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
