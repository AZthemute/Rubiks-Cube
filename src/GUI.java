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

            // todo: find cleaner way to do

            Piece UBL = this.cube.getPiece(Rotation.LEFT, Rotation.UP, Rotation.BACK);
            Piece UBM = this.cube.getPiece(Rotation.MIDDLE, Rotation.UP, Rotation.BACK);
            Piece UBR = this.cube.getPiece(Rotation.RIGHT, Rotation.UP, Rotation.BACK);
            Piece USL = this.cube.getPiece(Rotation.LEFT, Rotation.UP, Rotation.STANDING);
            Piece USM = this.cube.getPiece(Rotation.MIDDLE, Rotation.UP, Rotation.STANDING);
            Piece USR = this.cube.getPiece(Rotation.RIGHT, Rotation.UP, Rotation.STANDING);
            Piece UFL = this.cube.getPiece(Rotation.LEFT, Rotation.UP, Rotation.FRONT);
            Piece UFM = this.cube.getPiece(Rotation.MIDDLE, Rotation.UP, Rotation.FRONT);
            Piece UFR = this.cube.getPiece(Rotation.RIGHT, Rotation.UP, Rotation.FRONT);

            Piece EFL = this.cube.getPiece(Rotation.LEFT, Rotation.EQUATOR, Rotation.FRONT);
            Piece EFM = this.cube.getPiece(Rotation.MIDDLE, Rotation.EQUATOR, Rotation.FRONT);
            Piece EFR = this.cube.getPiece(Rotation.RIGHT, Rotation.EQUATOR, Rotation.FRONT);

            Piece ESL = this.cube.getPiece(Rotation.LEFT, Rotation.EQUATOR, Rotation.STANDING);

            Piece EBL = this.cube.getPiece(Rotation.LEFT, Rotation.EQUATOR, Rotation.BACK);

            Piece DFL = this.cube.getPiece(Rotation.LEFT, Rotation.DOWN, Rotation.FRONT);
            Piece DFM = this.cube.getPiece(Rotation.MIDDLE, Rotation.DOWN, Rotation.FRONT);
            Piece DFR = this.cube.getPiece(Rotation.RIGHT, Rotation.DOWN, Rotation.FRONT);

            Piece DSL = this.cube.getPiece(Rotation.LEFT, Rotation.DOWN, Rotation.STANDING);

            Piece DBL = this.cube.getPiece(Rotation.LEFT, Rotation.DOWN, Rotation.BACK);

            Color[][] upFaceStickers = new Color[3][3];
            upFaceStickers[0][0] = UBL.getColors().get(Rotation.UP);
            upFaceStickers[1][0] = UBM.getColors().get(Rotation.UP);
            upFaceStickers[2][0] = UBR.getColors().get(Rotation.UP);
            upFaceStickers[0][1] = USL.getColors().get(Rotation.UP);
            upFaceStickers[1][1] = USM.getColors().get(Rotation.UP);
            upFaceStickers[2][1] = USR.getColors().get(Rotation.UP);
            upFaceStickers[0][2] = UFL.getColors().get(Rotation.UP);
            upFaceStickers[1][2] = UFM.getColors().get(Rotation.UP);
            upFaceStickers[2][2] = UFR.getColors().get(Rotation.UP);

            Color[][] frontFaceStickers = new Color[3][3];
            frontFaceStickers[0][0] = UFL.getColors().get(Rotation.FRONT);
            frontFaceStickers[1][0] = UFM.getColors().get(Rotation.FRONT);
            frontFaceStickers[2][0] = UFR.getColors().get(Rotation.FRONT);
            frontFaceStickers[0][1] = EFL.getColors().get(Rotation.FRONT);
            frontFaceStickers[1][1] = EFM.getColors().get(Rotation.FRONT);
            frontFaceStickers[2][1] = EFR.getColors().get(Rotation.FRONT);
            frontFaceStickers[0][2] = DFL.getColors().get(Rotation.FRONT);
            frontFaceStickers[1][2] = DFM.getColors().get(Rotation.FRONT);
            frontFaceStickers[2][2] = DFR.getColors().get(Rotation.FRONT);

            Color[][] leftFaceStickers = new Color[3][3];
            leftFaceStickers[0][0] = USL.getColors().get(Rotation.LEFT);
            leftFaceStickers[1][0] = ESL.getColors().get(Rotation.LEFT);
            leftFaceStickers[2][0] = DSL.getColors().get(Rotation.LEFT);
            leftFaceStickers[0][1] = UBL.getColors().get(Rotation.LEFT);
            leftFaceStickers[1][1] = EBL.getColors().get(Rotation.LEFT);
            leftFaceStickers[2][1] = DBL.getColors().get(Rotation.LEFT);
            leftFaceStickers[0][2] = UFL.getColors().get(Rotation.LEFT);
            leftFaceStickers[1][2] = EFL.getColors().get(Rotation.LEFT);
            leftFaceStickers[2][2] = DFL.getColors().get(Rotation.LEFT);

            Color[][] rightFaceStickers = new Color[3][3];

            Color[][] backFaceStickers = new Color[3][3];

            Color[][] downFaceStickers = new Color[3][3];

            Face upFace = new Face(upFaceStickers);
            upFace.setBounds(500, 50, 151, 151);
            Face frontFace = new Face(frontFaceStickers);
            frontFace.setBounds(500, 210, 151, 151);
            Face leftFace = new Face(leftFaceStickers);
            leftFace.setBounds(340, 210, 151, 151);
            Face rightFace = new Face(rightFaceStickers);
            rightFace.setBounds(340, 210, 151, 151);
            Face backFace = new Face(backFaceStickers);
            backFace.setBounds(340, 210, 151, 151);
            Face downFace = new Face(downFaceStickers);
            downFace.setBounds(340, 210, 151, 151);

            add(upFace);
            add(frontFace);
            add(leftFace);
            add(rightFace);
            add(backFace);
            add(downFace);
            repaint();
        }
    }
}
