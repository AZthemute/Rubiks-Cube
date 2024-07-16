package GUI;

import cube.Algorithm;
import cube.Cube;
import cube.Piece;
import types.Color;
import types.Rotation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class GUI extends JFrame implements ActionListener {
    private Cube cube;

    /**
     * The first set of moves provided.
     */
    private Algorithm scramble = null;

    /**
     * All moves provided after the first set
     */
    private String movesString = "";
    private Algorithm moves = null;

    // GUI components
    private JTextField algInput;
    private JLabel instructionText;
    private JComboBox<String> solveChoicesBox, exportChoicesBox;
    private JButton solveButton, exportButton, resetButton, buttonShowCube, algsMenuButton;
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
        setBounds(0,0,1100, 650);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setVisible(true);

        buttonShowCube = new JButton("Execute");
        buttonShowCube.setBounds(850,20,120,40);
        buttonShowCube.addActionListener(this);
        add(buttonShowCube);

        algInput = new JTextField("");
        algInput.setBounds(120,20,720,40);
        algInput.addActionListener(this);
        add(algInput);

        instructionText = new JLabel("Enter moves...");
        instructionText.setBounds(20, 20, 100, 40);
        add(instructionText);

        String[] solveChoices = {"Cross", "Winter Variation", "COLL"};
        solveChoicesBox = new JComboBox<>(solveChoices);
        solveChoicesBox.setBounds(20, 600, 150, 40);
        add(solveChoicesBox);

        String[] exportChoices = {"Export only the scramble", "Export all moves done", "Export only the solution"};
        exportChoicesBox = new JComboBox<>(exportChoices);
        exportChoicesBox.setBounds(20, 650, 310, 40);
        add(exportChoicesBox);

        solveButton = new JButton("Solve");
        solveButton.setBounds(180, 600, 150, 40);
        solveButton.addActionListener(this);
        add(solveButton);

        exportButton = new JButton("Export to CubeDB");
        exportButton.setBounds(340, 650, 200, 40);
        exportButton.addActionListener(this);
        add(exportButton);

        resetButton = new JButton("Reset cube to solved state");
        resetButton.setBounds(340, 600, 200, 40);
        resetButton.addActionListener(this);
        add(resetButton);

        algsMenuButton = new JButton("Algorithms");
        algsMenuButton.setBounds(20, 550, 200, 40);
        algsMenuButton.addActionListener(this);
        add(algsMenuButton);

        SolvingAlgorithm test = new SolvingAlgorithm(new String[] {"YRG,BYW,GOY", "R U R'"});
        test.setBounds(800, 300, 200, 200);
        add(test);

        SideSticker testSide = new SideSticker(800, 500, java.awt.Color.YELLOW, true);
        testSide.setBounds(800, 500, 200, 200);
        add(testSide);

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
        switch (e.getActionCommand()) {
            case "Execute" -> {
                if (algInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(this,
                            "Please input some moves. ",
                            "Invalid moves", JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                Algorithm thisMoves;
                String oldMovesString = movesString;
                try {
                    thisMoves = new Algorithm(algInput.getText());
                    // Set the initial scramble if not done
                    if (scramble == null) scramble = thisMoves;

                    movesString += algInput.getText() + " ";
                    moves = new Algorithm(movesString);
                    System.out.println(movesString);
                    System.out.println(moves.toCubeDB());
                    System.out.println(thisMoves.toCubeDB());
                    System.out.println(scramble.toCubeDB());
                }
                catch (IllegalArgumentException | StringIndexOutOfBoundsException except) {
                    JOptionPane.showMessageDialog(this,
                            "Error in moves: " + except.getMessage(),
                            "Invalid moves", JOptionPane.ERROR_MESSAGE
                    );
                    movesString = oldMovesString;
                    return;
                }
                thisMoves.execute(cube);
                drawCube();
            }
            case "Solve" -> {
                Algorithm solveAlg = cube.solve(solveChoicesBox.getSelectedItem().toString());
            }
            case "Export to CubeDB" -> {
                Runtime rt = Runtime.getRuntime();
                Algorithm alg;
                switch (exportChoicesBox.getSelectedItem().toString()) {
                    case "Export only the scramble" -> alg = scramble;
                    case "Export all moves done" -> alg = moves;
                    case "Export only the solution" -> alg = scramble; // todo
                    default -> throw new IllegalArgumentException("Export choice was invalid");
                }
                try {
                    rt.exec("rundll32 url.dll,FileProtocolHandler "
                            + "https://cubedb.net?puzzle=3x3&scramble=" + alg.toCubeDB());
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Please input some moves. ",
                            "Invalid moves", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            case "Reset cube to solved state" -> {
                int toReset = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to reset the cube to being solved?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (toReset == JOptionPane.YES_OPTION) {
                    this.cube = new Cube();
                    this.scramble = null;
                    this.movesString = null;
                    this.moves = null;
                    drawCube();
                }
            }
            case "Algorithms" -> new AlgsMenu();
        }
    }

    public void drawCube() {
        HashMap<String, Piece> upLayer = getYLayer(Rotation.UP);
        HashMap<String, Piece> equatorLayer = getYLayer(Rotation.EQUATOR);
        HashMap<String, Piece> downLayer = getYLayer(Rotation.DOWN);

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

    /**
     * Used for the GUI. Gets a hashmap representing a yLayer.
     * @param layer Up/Equator/Down
     * @return The yLayer
     */
    public HashMap<String, Piece> getYLayer(Rotation layer) {
        String layerNotation = switch(layer) {
            case UP -> "U";
            case EQUATOR -> "E";
            case DOWN -> "D";
            default -> throw new IllegalArgumentException("layer must be one of: UP, EQUATOR, DOWN");
        };

        // To counteract ESM
        Piece XSM = null;
        if (layer != Rotation.EQUATOR) {
            XSM = cube.getPiece(layer, Rotation.STANDING, Rotation.MIDDLE);
        }
        Piece finalXSM = XSM;

        return new HashMap<>() {
            {
                put(layerNotation + "FL", cube.getPiece(layer, Rotation.FRONT, Rotation.LEFT));
                put(layerNotation + "SL", cube.getPiece(layer, Rotation.STANDING, Rotation.LEFT));
                put(layerNotation + "BL", cube.getPiece(layer, Rotation.BACK, Rotation.LEFT));
                put(layerNotation + "FM", cube.getPiece(layer, Rotation.FRONT, Rotation.MIDDLE));
                put(layerNotation + "SM", finalXSM);
                put(layerNotation + "BM", cube.getPiece(layer, Rotation.BACK, Rotation.MIDDLE));
                put(layerNotation + "FR", cube.getPiece(layer, Rotation.FRONT, Rotation.RIGHT));
                put(layerNotation + "SR", cube.getPiece(layer, Rotation.STANDING, Rotation.RIGHT));
                put(layerNotation + "BR", cube.getPiece(layer, Rotation.BACK, Rotation.RIGHT));
            }
        };
    }
}
