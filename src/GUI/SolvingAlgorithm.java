package GUI;

import cube.Algorithm;
import types.Color;
import types.Rotation;

import java.awt.*;

public class SolvingAlgorithm extends Face {
    Algorithm alg;
    SideSticker[][] sideStickers = new SideSticker[4][3];

    static final int sideStickerXOffset = 20;
    static final int sideStickerYOffset = 20;
    static final int sideStickerWidth = 50;
    static final int sideStickerHeight = 50;

    // todo: the custom CSV format with the faces
    public SolvingAlgorithm(String[] rawData) {
        System.out.println(rawData[0]);
        String[] upFaceString = rawData[0].split(",");
        this.setStickers(new Color[][] {
                getColorsFromRawData(upFaceString[0]),
                getColorsFromRawData(upFaceString[1]),
                getColorsFromRawData(upFaceString[2])
        });
        // In the future, I may allow reading in multiple algorithms.
        alg = new Algorithm(rawData[5]);
        sideStickers[0] = buildSideStickers(rawData[1], Rotation.LEFT);
        sideStickers[1] = buildSideStickers(rawData[2], Rotation.FRONT);
        sideStickers[2] = buildSideStickers(rawData[3], Rotation.RIGHT);
        sideStickers[3] = buildSideStickers(rawData[4], Rotation.BACK);
    }

    private SideSticker[] buildSideStickers(String rawData, Rotation face) {
        SideSticker[] sideStickers;
        int xOffset = 0;
        int yOffset = 0;
        if (face == Rotation.RIGHT) xOffset = 170;
        if (face == Rotation.FRONT) yOffset = 170;
        switch (face) {
            case LEFT, RIGHT -> sideStickers = new SideSticker[] {
                    new SideSticker(xOffset, 20, stringToAWTColor(rawData.charAt(0)), true),
                    new SideSticker(xOffset, 70, stringToAWTColor(rawData.charAt(1)), true),
                    new SideSticker(xOffset, 120, stringToAWTColor(rawData.charAt(2)), true),
            };
            case FRONT, BACK -> sideStickers = new SideSticker[] {
                    new SideSticker(20, yOffset, stringToAWTColor(rawData.charAt(0)), false),
                    new SideSticker(70, yOffset, stringToAWTColor(rawData.charAt(1)), false),
                    new SideSticker(120, yOffset, stringToAWTColor(rawData.charAt(2)), false),
            };
            default -> throw new IllegalArgumentException("Invalid face: " + face);
        }
        return sideStickers;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        for (Sticker[] topLevelStickers: this.stickers) {
            for (Sticker sticker: topLevelStickers) {
                if (sticker == null) continue;
                sticker.paintComponent(graphics);
            }
        }
        for (SideSticker[] topLevelSideStickers: this.sideStickers) {
            for (SideSticker sticker: topLevelSideStickers) {
                if (sticker == null) continue;
                sticker.paintComponent(graphics);
            }
        }
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

    private java.awt.Color stringToAWTColor(char s) {
        return switch (s) {
            case 'W' -> java.awt.Color.WHITE;
            case 'G' -> java.awt.Color.GREEN;
            case 'R' -> java.awt.Color.RED;
            case 'O' -> java.awt.Color.ORANGE;
            case 'Y' -> java.awt.Color.YELLOW;
            case 'B' -> java.awt.Color.BLUE;
            case 'N' -> java.awt.Color.GRAY; // No sticker exists
            default -> throw new IllegalStateException("Unexpected color: " + s);
        };
    }
}
