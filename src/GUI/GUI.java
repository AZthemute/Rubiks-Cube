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
     * The first set of moves done.
     */
    private Algorithm scramble = null;

    /**
     * All moves done after the first set.
     */
    private Algorithm moves = null;

    /**
     * The last set of moves done.
     */
    private Algorithm lastMoves = null;

    // GUI components
    private final JTextField algInput;
    private final JComboBox<String> exportChoicesBox, algsMenuChoicesBox;
    private final Face upFace, frontFace, leftFace, rightFace, backFace, downFace;

    // The offsets are based around the front face being at 0,0
    // slightly bigger than the actual face sizes to account for SolvingAlgorithm
    private static final int faceXOffset = 160;
    private static final int faceYOffset = 160;
    private static final int faceXBase = 270;
    private static final int faceYBase = 220;
    private static final int faceWidth = 190;
    private static final int faceHeight = 190;

    public GUI(Cube cube) {
        setTitle("Rubik's Cube");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0,0,1100, 650);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        setVisible(true);

        JButton executeButton = new JButton("Execute");
        executeButton.setBounds(850,20,120,40);
        executeButton.addActionListener(this);
        add(executeButton);

        algInput = new JTextField("");
        algInput.setBounds(120,20,720,40);
        algInput.addActionListener(this);
        add(algInput);

        JLabel instructionText = new JLabel("Enter moves...");
        instructionText.setBounds(20, 20, 100, 40);
        add(instructionText);

        String[] exportChoices = {"Export only the scramble", "Export all moves done"};
        exportChoicesBox = new JComboBox<>(exportChoices);
        exportChoicesBox.setBounds(20, 620, 200, 40);
        add(exportChoicesBox);

        String[] algsMenuChoices = {"Winter Variation", "COLL", "Custom"};
        algsMenuChoicesBox = new JComboBox<>(algsMenuChoices);
        algsMenuChoicesBox.setBounds(20, 570, 200, 40);
        add(algsMenuChoicesBox);

        JButton exportButton = new JButton("Export to CubeDB");
        exportButton.setBounds(230, 620, 200, 40);
        exportButton.addActionListener(this);
        add(exportButton);

        JButton resetButton = new JButton("Reset cube to solved state");
        resetButton.setBounds(440, 620, 200, 40);
        resetButton.addActionListener(this);
        add(resetButton);

        JButton algsMenuButton = new JButton("Algorithms");
        algsMenuButton.setBounds(230, 570, 200, 40);
        algsMenuButton.addActionListener(this);
        add(algsMenuButton);

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
                if (algInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please input some moves. ",
                            "Invalid moves", JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                try {
                    this.execute(new Algorithm(algInput.getText()));
                }
                catch (IllegalArgumentException | StringIndexOutOfBoundsException except) {
                    JOptionPane.showMessageDialog(this,
                            "Error in moves: " + except.getMessage(),
                            "Invalid moves", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            case "Export to CubeDB" -> {
                Runtime rt = Runtime.getRuntime();
                Algorithm alg;
                switch (exportChoicesBox.getSelectedItem().toString()) {
                    case "Export only the scramble" -> alg = scramble;
                    case "Export all moves done" -> alg = moves;
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
                    this.moves = null;
                    drawCube();
                }
            }
            case "Algorithms" -> new AlgsMenu(algsMenuChoicesBox.getSelectedItem().toString(), this);
        }
    }

    public void execute(Algorithm alg) {
        // Clean up moves when doing the reverse.
        Algorithm reverse = alg.reverse();

        if (lastMoves != null && lastMoves.toString().equals(reverse.toString())) {
            int initialSize = moves.getMoves().size();
            System.out.println(initialSize);
            for (int i = 0; i < reverse.getMoves().size() ; i++) {
                moves.getMoves().remove(initialSize - i - 1);
            }
            // to avoid things like R R' R being flagged and completely removed
            lastMoves = null;
        }

        // Else, add it to moves.
        else {
            try {
                if (scramble == null) scramble = alg;
                if (moves == null || moves.getMoves().isEmpty()) moves = new Algorithm(alg.toString());
                else moves = new Algorithm(moves + " " + alg);
            }
            catch (IllegalArgumentException | StringIndexOutOfBoundsException except) {
                JOptionPane.showMessageDialog(this,
                        "Error in moves: " + except.getMessage(),
                        "Invalid moves", JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            lastMoves = alg;
        }
        alg.execute(cube);
        drawCube();
    }

    /**
     * Draws the cube.
     */
    public void drawCube() {
        HashMap<String, Piece> upLayer = getYLayer(Rotation.UP);
        HashMap<String, Piece> equatorLayer = getYLayer(Rotation.EQUATOR);
        HashMap<String, Piece> downLayer = getYLayer(Rotation.DOWN);

        Color[][] upFaceStickers = {
                {
                        upLayer.get("UBL").getColors().get(Rotation.UP),
                        upLayer.get("UBM").getColors().get(Rotation.UP),
                        upLayer.get("UBR").getColors().get(Rotation.UP),
                },
                {
                        upLayer.get("USL").getColors().get(Rotation.UP),
                        upLayer.get("USM").getColors().get(Rotation.UP),
                        upLayer.get("USR").getColors().get(Rotation.UP),
                },
                {
                        upLayer.get("UFL").getColors().get(Rotation.UP),
                        upLayer.get("UFM").getColors().get(Rotation.UP),
                        upLayer.get("UFR").getColors().get(Rotation.UP),
                }
        };

        Color[][] frontFaceStickers = {
                {
                        upLayer.get("UFL").getColors().get(Rotation.FRONT),
                        upLayer.get("UFM").getColors().get(Rotation.FRONT),
                        upLayer.get("UFR").getColors().get(Rotation.FRONT),
                },
                {
                        equatorLayer.get("EFL").getColors().get(Rotation.FRONT),
                        equatorLayer.get("EFM").getColors().get(Rotation.FRONT),
                        equatorLayer.get("EFR").getColors().get(Rotation.FRONT),
                },
                {
                        downLayer.get("DFL").getColors().get(Rotation.FRONT),
                        downLayer.get("DFM").getColors().get(Rotation.FRONT),
                        downLayer.get("DFR").getColors().get(Rotation.FRONT),
                }
        };

        // This needs to be rotated some weird way, I don't really understand it.
        Color[][] leftFaceStickers = {
                {
                        upLayer.get("UBL").getColors().get(Rotation.LEFT),
                        upLayer.get("USL").getColors().get(Rotation.LEFT),
                        upLayer.get("UFL").getColors().get(Rotation.LEFT),
                },
                {
                        equatorLayer.get("EBL").getColors().get(Rotation.LEFT),
                        equatorLayer.get("ESL").getColors().get(Rotation.LEFT),
                        equatorLayer.get("EFL").getColors().get(Rotation.LEFT),
                },
                {
                        downLayer.get("DBL").getColors().get(Rotation.LEFT),
                        downLayer.get("DSL").getColors().get(Rotation.LEFT),
                        downLayer.get("DFL").getColors().get(Rotation.LEFT),
                }
        };

        // This needs to be rotated some weird way, I don't really understand it.
        Color[][] rightFaceStickers = {
                {
                        upLayer.get("UFR").getColors().get(Rotation.RIGHT),
                        upLayer.get("USR").getColors().get(Rotation.RIGHT),
                        upLayer.get("UBR").getColors().get(Rotation.RIGHT),
                },
                {
                        equatorLayer.get("EFR").getColors().get(Rotation.RIGHT),
                        equatorLayer.get("ESR").getColors().get(Rotation.RIGHT),
                        equatorLayer.get("EBR").getColors().get(Rotation.RIGHT),
                },
                {
                        downLayer.get("DFR").getColors().get(Rotation.RIGHT),
                        downLayer.get("DSR").getColors().get(Rotation.RIGHT),
                        downLayer.get("DBR").getColors().get(Rotation.RIGHT),
                }
        };

        // For this one, we must mirror the layer.
        Color[][] backFaceStickers = {
                {
                        upLayer.get("UBR").getColors().get(Rotation.BACK),
                        upLayer.get("UBM").getColors().get(Rotation.BACK),
                        upLayer.get("UBL").getColors().get(Rotation.BACK),
                },
                {
                        equatorLayer.get("EBR").getColors().get(Rotation.BACK),
                        equatorLayer.get("EBM").getColors().get(Rotation.BACK),
                        equatorLayer.get("EBL").getColors().get(Rotation.BACK),
                },
                {
                        downLayer.get("DBR").getColors().get(Rotation.BACK),
                        downLayer.get("DBM").getColors().get(Rotation.BACK),
                        downLayer.get("DBL").getColors().get(Rotation.BACK),
                }
        };

        // I don't know about this either. it gets mirrored or something.
        Color[][] downFaceStickers = {
                {
                        downLayer.get("DFL").getColors().get(Rotation.DOWN),
                        downLayer.get("DFM").getColors().get(Rotation.DOWN),
                        downLayer.get("DFR").getColors().get(Rotation.DOWN),
                },
                {
                        downLayer.get("DSL").getColors().get(Rotation.DOWN),
                        downLayer.get("DSM").getColors().get(Rotation.DOWN),
                        downLayer.get("DSR").getColors().get(Rotation.DOWN),
                },
                {
                        downLayer.get("DBL").getColors().get(Rotation.DOWN),
                        downLayer.get("DBM").getColors().get(Rotation.DOWN),
                        downLayer.get("DBR").getColors().get(Rotation.DOWN),
                }
        };

        upFace.setStickers(upFaceStickers, false);
        frontFace.setStickers(frontFaceStickers, false);
        leftFace.setStickers(leftFaceStickers, false);
        rightFace.setStickers(rightFaceStickers, false);
        backFace.setStickers(backFaceStickers, false);
        downFace.setStickers(downFaceStickers, false);

        add(upFace);
        add(frontFace);
        add(leftFace);
        add(rightFace);
        add(backFace);
        add(downFace);
        repaint();
    }

    /**
     * Used for the GUI. Gets a HashMap representing a yLayer.
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
