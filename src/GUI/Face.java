package GUI;

import types.Color;

import javax.swing.*;
import java.awt.*;

public class Face extends JPanel {
    protected Sticker[][] stickers;

    public Face() {
        this.stickers = new Sticker[3][3];
    }

    @Override
    public void paintComponent(Graphics graphics) {
        for (Sticker[] topLevelStickers: this.stickers) {
            for (Sticker sticker: topLevelStickers) {
                if (sticker == null) continue;
                sticker.paintComponent(graphics);
            }
        }
    }

    public void setStickers(Color[][] colors, boolean allowNull) {
        this.stickers = new Sticker[3][3];
        for (int i = 0; i <= 2; i++) {
            types.Color[] topLevelColors = colors[i];
            for (int j = 0; j <= 2; j++) {
                Color color = topLevelColors[j];
                if (color == null && !allowNull) continue;
                stickers[i][j] = new Sticker(j*50+20, i*50+20, color);
            }
        }
    }
}
