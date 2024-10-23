package GUI;

import cube.Algorithm;
import types.Color;
import types.Rotation;

import java.awt.*;

public class SolvingAlgorithm extends Face {
    private final Algorithm alg;
    private SideSticker[][] sideStickers = new SideSticker[4][3];

    static final int sideStickerXOffset = 20;
    static final int sideStickerYOffset = 20;
    static final int sideStickerWidth = 50;
    static final int sideStickerHeight = 50;

    public SolvingAlgorithm(String[] rawData) {
        String[] upFaceString = rawData[0].split(",");
        this.setStickers(new Color[][] {
                getColorsFromRawData(upFaceString[0]),
                getColorsFromRawData(upFaceString[1]),
                getColorsFromRawData(upFaceString[2])
        }, true);
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
        if (face == Rotation.RIGHT) xOffset = sideStickerXOffset + 3 * sideStickerWidth;
        if (face == Rotation.FRONT) yOffset = sideStickerYOffset + 3 * sideStickerHeight;
        switch (face) {
            case LEFT -> sideStickers = new SideSticker[] {
                    new SideSticker(xOffset, sideStickerYOffset, stringToAWTColor(rawData.charAt(0)), true),
                    new SideSticker(xOffset, sideStickerYOffset + sideStickerHeight, stringToAWTColor(rawData.charAt(1)), true),
                    new SideSticker(xOffset, sideStickerYOffset + 2 * sideStickerHeight, stringToAWTColor(rawData.charAt(2)), true),
            };
            case RIGHT -> sideStickers = new SideSticker[] {
                    new SideSticker(xOffset, sideStickerYOffset + 2 * sideStickerHeight, stringToAWTColor(rawData.charAt(0)), true),
                    new SideSticker(xOffset, sideStickerYOffset + sideStickerHeight, stringToAWTColor(rawData.charAt(1)), true),
                    new SideSticker(xOffset, sideStickerYOffset, stringToAWTColor(rawData.charAt(2)), true),
            };
            case FRONT -> sideStickers = new SideSticker[] {
                    new SideSticker(sideStickerXOffset, yOffset, stringToAWTColor(rawData.charAt(0)), false),
                    new SideSticker(sideStickerXOffset + sideStickerWidth, yOffset, stringToAWTColor(rawData.charAt(1)), false),
                    new SideSticker(sideStickerXOffset + 2 * sideStickerWidth, yOffset, stringToAWTColor(rawData.charAt(2)), false),
            };
            case BACK -> sideStickers = new SideSticker[] {
                    new SideSticker(sideStickerXOffset + 2 * sideStickerWidth, yOffset, stringToAWTColor(rawData.charAt(0)), false),
                    new SideSticker(sideStickerXOffset + sideStickerWidth, yOffset, stringToAWTColor(rawData.charAt(1)), false),
                    new SideSticker(sideStickerXOffset, yOffset, stringToAWTColor(rawData.charAt(2)), false),
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

    public String toString() {
        return this.alg.toString();
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
            case 'N' -> null; // No sticker exists
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

    public Algorithm alg() {
        return this.alg;
    }
}
