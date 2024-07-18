package GUI;

import javax.swing.*;
import java.awt.*;

public class Sticker extends JPanel {

    static final int baseWidth = 50;
    static final int baseHeight = 50;
    int x;
    int y;
    int width;
    int height;
    java.awt.Color color;

    public Sticker(int x, int y, java.awt.Color color) {
        this.x = x;
        this.y = y;
        this.width = baseWidth;
        this.height = baseHeight;
        this.color = color;
    }

    public Sticker(int x, int y, types.Color color) {
        this.x = x;
        this.y = y;
        this.width = baseWidth;
        this.height = baseHeight;
        switch (color) {
            case WHITE -> this.color = java.awt.Color.WHITE;
            case GREEN -> this.color = java.awt.Color.GREEN;
            case RED -> this.color = java.awt.Color.RED;
            case ORANGE -> this.color = java.awt.Color.ORANGE;
            case YELLOW -> this.color = java.awt.Color.YELLOW;
            case BLUE -> this.color = java.awt.Color.BLUE;
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        System.out.println("Drawing!");
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(java.awt.Color.blue);
        // Set the position and size of the rectangle

        // Draw the filled square
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Draw the black outline
        g.setStroke(new BasicStroke(1));
        g.setColor(java.awt.Color.BLACK);
        g.drawRect(x, y, width, height);
    }
}
