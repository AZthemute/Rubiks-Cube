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
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel(text);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(label, gbc);

        // Filler component to push buttons to the right
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(Box.createHorizontalGlue(), gbc);

        JButton reverseButton = new JButton("Reverse");
        reverseButton.addActionListener(AlgsMenu.this);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 10, 5);
        panel.add(reverseButton, gbc);

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(AlgsMenu.this);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        panel.add(executeButton, gbc);

        panel.setPreferredSize(new Dimension(350, 100));
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // todo
    }
}
