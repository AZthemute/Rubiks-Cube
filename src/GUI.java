import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    private Cube cube;

    private JButton butShowGraph;

    public GUI(Cube cube) {
        setTitle("Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,600, 400);
        setLayout(null);
        setVisible(true);

        butShowGraph = new JButton("Show Graph");
        butShowGraph.setBounds(50,50,120,40);
        butShowGraph.addActionListener(this);
        add(butShowGraph);

        this.cube = cube;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Show Graph")) {
            System.out.println("click");
            Sticker g = new Sticker(50, java.awt.Color.ORANGE);
            Sticker g1 = new Sticker(100, java.awt.Color.WHITE);
            g.setBounds(200, 50, 200, 200);
            g1.setBounds(200, 50, 200, 200);

            Face face = new Face(new Sticker[][] {{g, g1}});
            face.setBounds(200, 50, 200, 200);

            Piece UFR = this.cube.getPiece(Rotation.RIGHT, Rotation.UP, Rotation.FRONT);

            Color[][] upFaceStickers = new Color[3][3];
            upFaceStickers[2][2] = UFR.getColors().get(Rotation.UP);

            Face upFace = new Face(upFaceStickers);
            upFace.setBounds(200, 50, 200, 200);

            add(upFace);
            repaint();
        }
    }
}
