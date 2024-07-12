package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class AlgsMenu extends JFrame implements ActionListener {
    public AlgsMenu() {
        setTitle("Algorithms");
        setSize(600, 800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        ArrayList<String[]> algs = FileHandler.readIntoCSV("wv.txt");
        for (String[] alg : algs) {
            mainPanel.add(createSection(Arrays.toString(alg)));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        setVisible(true);
    }

    private JPanel createSection(String text) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawLine(0, 0, getWidth(), 0); // Top line
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); // Bottom line
            }
        };
        panel.setLayout(new GridBagLayout());
        JLabel label = new JLabel(text);
        panel.add(label);
        panel.setPreferredSize(new Dimension(350, 100));
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // todo
    }
}
