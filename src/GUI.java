import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GUI extends JFrame implements ActionListener {
    private Cube cube;

    // GUI components
    private JButton buttonShowCube;
    private JTextField algInput;
    private JOptionPane invalidInput;
    private Face upFace, frontFace, leftFace, rightFace, backFace, downFace;

    // The offsets are based around the front face
    private static final int faceXOffset = 160;
    private static final int faceYOffset = 160;
    private static final int faceXBase = 300;
    private static final int faceYBase = 250;
    private static final int faceWidth = 160;
    private static final int faceHeight = 160;

    public GUI(Cube cube) {
        setTitle("Rubik's Cube");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,600, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setVisible(true);

        buttonShowCube = new JButton("Execute");
        buttonShowCube.setBounds(800,20,120,40);
        buttonShowCube.addActionListener(this);
        add(buttonShowCube);

        algInput = new JTextField("Enter moves...");
        algInput.setBounds(50,20,720,40);
        algInput.addActionListener(this);
        add(algInput);

        // Drawing the cube
        this.cube = cube;
        upFace = new Face();
        upFace.setBounds(faceXBase, faceYBase - faceYOffset, faceWidth, faceHeight);
        frontFace = new Face();
        frontFace.setBounds(faceXBase, faceYBase, faceWidth, faceHeight);
        leftFace = new Face();
        leftFace.setBounds(faceXBase - faceXOffset, faceYBase, faceWidth, faceHeight);
        rightFace = new Face();
        rightFace.setBounds(faceXBase + faceXOffset, faceYBase, faceWidth, faceHeight);
        backFace = new Face();
        backFace.setBounds(faceXBase + (faceXOffset * 2), faceYBase, faceWidth, faceHeight);
        downFace = new Face();
        downFace.setBounds(faceXBase, faceYBase + faceYOffset, faceWidth, faceHeight);
        drawCube();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Execute")) {
            Algorithm alg;
            try {
                alg = new Algorithm(algInput.getText());
            }
            catch (IllegalArgumentException except) {
                except.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error in moves: " + except.getMessage(),
                        "Invalid moves", JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            alg.execute(cube);
            drawCube();
        }
    }

    public void drawCube() {
        HashMap<String, Piece> upLayer = cube.getYLayer(Rotation.UP);
        HashMap<String, Piece> equatorLayer = cube.getYLayer(Rotation.EQUATOR);
        HashMap<String, Piece> downLayer = cube.getYLayer(Rotation.DOWN);

        Color[][] upFaceStickers = {
                {
                        upLayer.get("UBL").getColors().get(Rotation.UP),
                        upLayer.get("USL").getColors().get(Rotation.UP),
                        upLayer.get("UFL").getColors().get(Rotation.UP),
                },
                {
                        upLayer.get("UBM").getColors().get(Rotation.UP),
                        upLayer.get("USM").getColors().get(Rotation.UP),
                        upLayer.get("UFM").getColors().get(Rotation.UP),
                },
                {
                        upLayer.get("UBR").getColors().get(Rotation.UP),
                        upLayer.get("USR").getColors().get(Rotation.UP),
                        upLayer.get("UFR").getColors().get(Rotation.UP),
                }
        };

        Color[][] frontFaceStickers = {
                {
                        upLayer.get("UFL").getColors().get(Rotation.FRONT),
                        equatorLayer.get("EFL").getColors().get(Rotation.FRONT),
                        downLayer.get("DFL").getColors().get(Rotation.FRONT),
                },
                {
                        upLayer.get("UFM").getColors().get(Rotation.FRONT),
                        equatorLayer.get("EFM").getColors().get(Rotation.FRONT),
                        downLayer.get("DFM").getColors().get(Rotation.FRONT),
                },
                {
                        upLayer.get("UFR").getColors().get(Rotation.FRONT),
                        equatorLayer.get("EFR").getColors().get(Rotation.FRONT),
                        downLayer.get("DFR").getColors().get(Rotation.FRONT),
                }
        };

        // This needs to be rotated some weird way, I don't really understand it.
        Color[][] leftFaceStickers = {
                {
                        upLayer.get("UBL").getColors().get(Rotation.LEFT),
                        equatorLayer.get("EBL").getColors().get(Rotation.LEFT),
                        downLayer.get("DBL").getColors().get(Rotation.LEFT),
                },
                {
                        upLayer.get("USL").getColors().get(Rotation.LEFT),
                        equatorLayer.get("ESL").getColors().get(Rotation.LEFT),
                        downLayer.get("DSL").getColors().get(Rotation.LEFT),
                },
                {
                        upLayer.get("UFL").getColors().get(Rotation.LEFT),
                        equatorLayer.get("EFL").getColors().get(Rotation.LEFT),
                        downLayer.get("DFL").getColors().get(Rotation.LEFT),
                }
        };

        // This needs to be rotated some weird way, I don't really understand it.
        Color[][] rightFaceStickers = {
                {
                        upLayer.get("UFR").getColors().get(Rotation.RIGHT),
                        equatorLayer.get("EFR").getColors().get(Rotation.RIGHT),
                        downLayer.get("DFR").getColors().get(Rotation.RIGHT),
                },
                {
                        upLayer.get("USR").getColors().get(Rotation.RIGHT),
                        equatorLayer.get("ESR").getColors().get(Rotation.RIGHT),
                        downLayer.get("DSR").getColors().get(Rotation.RIGHT),
                },
                {
                        upLayer.get("UBR").getColors().get(Rotation.RIGHT),
                        equatorLayer.get("EBR").getColors().get(Rotation.RIGHT),
                        downLayer.get("DBR").getColors().get(Rotation.RIGHT),
                }
        };

        // For this one, we must mirror the layer.
        Color[][] backFaceStickers = {
                {
                        upLayer.get("UBR").getColors().get(Rotation.BACK),
                        equatorLayer.get("EBR").getColors().get(Rotation.BACK),
                        downLayer.get("DBR").getColors().get(Rotation.BACK),
                },
                {
                        upLayer.get("UBM").getColors().get(Rotation.BACK),
                        equatorLayer.get("EBM").getColors().get(Rotation.BACK),
                        downLayer.get("DBM").getColors().get(Rotation.BACK),
                },
                {
                        upLayer.get("UBL").getColors().get(Rotation.BACK),
                        equatorLayer.get("EBL").getColors().get(Rotation.BACK),
                        downLayer.get("DBL").getColors().get(Rotation.BACK),
                }
        };

        // I don't know about this either. it gets mirrored or something.
        Color[][] downFaceStickers = {
                {
                        downLayer.get("DFL").getColors().get(Rotation.DOWN),
                        downLayer.get("DSL").getColors().get(Rotation.DOWN),
                        downLayer.get("DBL").getColors().get(Rotation.DOWN),
                },
                {
                        downLayer.get("DFM").getColors().get(Rotation.DOWN),
                        downLayer.get("DSM").getColors().get(Rotation.DOWN),
                        downLayer.get("DBM").getColors().get(Rotation.DOWN),
                },
                {
                        downLayer.get("DFR").getColors().get(Rotation.DOWN),
                        downLayer.get("DSR").getColors().get(Rotation.DOWN),
                        downLayer.get("DBR").getColors().get(Rotation.DOWN),
                }
        };

        upFace.setStickers(upFaceStickers);
        frontFace.setStickers(frontFaceStickers);
        leftFace.setStickers(leftFaceStickers);
        rightFace.setStickers(rightFaceStickers);
        backFace.setStickers(backFaceStickers);
        downFace.setStickers(downFaceStickers);

        add(upFace);
        add(frontFace);
        add(leftFace);
        add(rightFace);
        add(backFace);
        add(downFace);
        repaint();
    }
}
