package GUI;

import cube.Algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.text.*;

public class AlgsMenu extends JFrame implements ActionListener {
    private final GUI originalGuiReference;

    /**
     * @param originalGuiReference The GUI that created this menu.
     */
    public AlgsMenu(GUI originalGuiReference) {
        this.originalGuiReference = originalGuiReference;
        setTitle("Algorithms");
        setSize(600, 800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        ArrayList<String[]> algs = FileHandler.readIntoTSV("wv.tsv");
        for (String[] alg : algs) {
            SolvingAlgorithm face = new SolvingAlgorithm(alg);
            mainPanel.add(createSection(face));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Set the initial scroll position to the top
        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
        });

        add(scrollPane);

        setVisible(true);
    }

    /**
     * Creates a new section on the menu for a new algorithm.
     */
    private JPanel createSection(SolvingAlgorithm algorithm) {
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

        // text but complicated
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        Style style = doc.addStyle("Style", null);
        StyleConstants.setFontFamily(style, "Arial");
        StyleConstants.setFontSize(style, 14);
        StyleConstants.setLineSpacing(style, 0.0f);

        try {
            doc.insertString(doc.getLength(), algorithm.toString(), style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        textPane.setCaretPosition(0);
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.setBackground(null);
        textPane.setBounds(220, 85, 150, 60);

        // back to simple stuff
        JButton reverseButton = new JButton("Reverse");
        reverseButton.addActionListener(AlgsMenu.this);
        reverseButton.putClientProperty("alg", algorithm.alg);
        reverseButton.setBounds(380, 90, 90, 30);

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(AlgsMenu.this);
        executeButton.putClientProperty("alg", algorithm.alg);
        executeButton.setBounds(470, 90, 90, 30);

        panel.add(algorithm);
        panel.add(textPane);
        panel.add(reverseButton);
        panel.add(executeButton);
        panel.setPreferredSize(new Dimension(350, 210));
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        Algorithm alg = (Algorithm) button.getClientProperty("alg");

        if (e.getActionCommand().equals("Execute")) originalGuiReference.execute(alg);
        else if (e.getActionCommand().equals("Reverse")) originalGuiReference.execute(alg.getReverse());
    }
}
