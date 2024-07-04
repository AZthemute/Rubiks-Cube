package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMenu extends JFrame implements ActionListener {
    public SettingsMenu() {
        setTitle("Settings Menu");
        setBounds(0,0,500, 650);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // todo, only if client needs settings
    }
}
