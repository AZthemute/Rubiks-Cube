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

        ArrayList<String[]> algs = FileHandler.readIntoTSV("wv.tsv");
        for (String[] alg : algs) {
            SolvingAlgorithm face = new SolvingAlgorithm(alg);
            mainPanel.add(createSection(face.alg.toString(), face));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        setVisible(true);
    }

    private JPanel createSection(String text, SolvingAlgorithm algorithm) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawLine(0, 0, getWidth(), 0); // Top line
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); // Bottom line
            }
        };
        panel.setLayout(null);

        algorithm.setBounds(10, 10, 200, 200);

        JLabel algText = new JLabel(text);
        algText.setBounds(220, 80, 100, 50);

        JButton reverseButton = new JButton("Reverse");
        reverseButton.addActionListener(AlgsMenu.this);
        reverseButton.setBounds(380, 90, 90, 30);

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(AlgsMenu.this);
        executeButton.setBounds(470, 90, 90, 30);

        panel.add(algorithm);
        panel.add(algText);
        panel.add(reverseButton);
        panel.add(executeButton);
        panel.setPreferredSize(new Dimension(350, 210));
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // todo
    }
}
