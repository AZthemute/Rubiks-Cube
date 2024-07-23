package GUI;

import cube.Algorithm;
import types.Color;

import java.awt.*;

public class SolvingAlgorithm extends Face {
    Algorithm alg;
    SideSticker sticker;
    SideSticker sticker2;

    // todo: the custom CSV format with the faces
    public SolvingAlgorithm(String[] rawData) {
        String[] upFaceString = rawData[0].split(",");
        this.setStickers(new Color[][] {
                getColorsFromRawData(upFaceString[0]),
                getColorsFromRawData(upFaceString[1]),
                getColorsFromRawData(upFaceString[2])
        });
        alg = new Algorithm(rawData[1]);
        System.out.println(alg.toCubeDB());
        sticker = new SideSticker(120, 170, java.awt.Color.RED, false);
        // note: everything draws relative to the top left corner. to fix.
        sticker2 = new SideSticker(0, 20, java.awt.Color.RED, true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        for (Sticker[] topLevelStickers: this.stickers) {
            for (Sticker sticker: topLevelStickers) {
                if (sticker == null) continue;
                sticker.paintComponent(graphics);
            }
        }
        sticker.paintComponent(graphics);
        sticker2.paintComponent(graphics);
    }

    private Color[] getColorsFromRawData(String face) {
        Color[] colors = new Color[3];
        char[] charsColors = face.toCharArray();
        colors[0] = stringToColor(charsColors[0]);
        colors[1] = stringToColor(charsColors[1]);
        colors[2] = stringToColor(charsColors[2]);
        return colors;
    }

    private Color stringToColor(char s) {
        return switch (s) {
            case 'W' -> Color.WHITE;
            case 'G' -> Color.GREEN;
            case 'R' -> Color.RED;
            case 'O' -> Color.ORANGE;
            case 'Y' -> Color.YELLOW;
            case 'B' -> Color.BLUE;
            default -> throw new IllegalStateException("Unexpected color: " + s);
        };
    }
}
