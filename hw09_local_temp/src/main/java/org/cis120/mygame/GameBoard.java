package org.cis120.mygame;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Twenty48 ttt; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ttt = new Twenty48(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();

                // updates the model given the key that was clicked
                if (c == KeyEvent.VK_UP) {
                    ttt.playTurn('u');
                } else if (c == KeyEvent.VK_DOWN) {
                    ttt.playTurn('d');
                } else if (c == KeyEvent.VK_LEFT) {
                    ttt.playTurn('l');
                } else if (c == KeyEvent.VK_RIGHT) {
                    ttt.playTurn('r');
                }

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }

            public void keyReleased(KeyEvent e) {

            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ttt.reset();
        status.setText("Start Playing!");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Undoes the most recent move made.
     */
    public void undo() {
        ttt.undoTurn();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Saves the most recent move made.
     */
    public void save() {
        ttt.saveGame();
        status.setText("Game has been saved!!");

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Resumes the game at the saved state.
     */
    public void resume() {
        ttt.resumeGame();
        status.setText("Game has been restarted at saved point!!");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (ttt.checkGameOver()) {
            status.setText("Game Over :(");
        } else if (ttt.checkGameWon()) {
            status.setText("You won! But keep playing :)");
        } else {
            status.setText("Keep Playing :)");

        }
    }

    /**
     * Helper method which returns the tile color for a given number.
     */
    private int[] getColor(int num) {
        int[] ret = new int[3];
        if (num == 0) {
            ret[0] = 205;
            ret[1] = 193;
            ret[2] = 180;
        } else if (num == 2) {
            ret[0] = 238;
            ret[1] = 228;
            ret[2] = 217;
        } else if (num == 4) {
            ret[0] = 238;
            ret[1] = 225;
            ret[2] = 201;
        } else if (num == 8) {
            ret[0] = 243;
            ret[1] = 178;
            ret[2] = 122;
        } else if (num == 16) {
            ret[0] = 246;
            ret[1] = 150;
            ret[2] = 100;
        } else if (num == 32) {
            ret[0] = 247;
            ret[1] = 125;
            ret[2] = 95;
        } else if (num == 64) {
            ret[0] = 247;
            ret[1] = 95;
            ret[2] = 59;
        } else if (num == 128) {
            ret[0] = 237;
            ret[1] = 208;
            ret[2] = 115;
        } else if (num == 256) {
            ret[0] = 237;
            ret[1] = 204;
            ret[2] = 98;
        } else if (num == 512) {
            ret[0] = 237;
            ret[1] = 200;
            ret[2] = 80;
        } else if (num == 1024) {
            ret[0] = 237;
            ret[1] = 197;
            ret[2] = 63;
        } else if (num == 2048) {
            ret[0] = 237;
            ret[1] = 194;
            ret[2] = 46;
        } else {
            ret[0] = 244;
            ret[1] = 102;
            ret[2] = 115;
        }
        return ret;
    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws tiles
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                int state = ttt.getCell(j, i);

                int[] color = getColor(state);
                Color myColor = new Color(color[0], color[1], color[2]);
                g.setColor(myColor);
                g.fillRoundRect(100 * j, 100 * i, 100, 100, 15, 15);

                // changes number placement based on state of the cell to account for number of
                // digits
                if (state != 0) {
                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.WHITE);
                    if (state < 128) {
                        g.drawString(Integer.toString(state), 100 * j + 40, 100 * i + 60);
                    } else if (state < 1000) {
                        g.drawString(Integer.toString(state), 100 * j + 30, 100 * i + 60);
                    } else {
                        g.drawString(Integer.toString(state), 100 * j + 20, 100 * i + 60);
                    }

                }

            }
        }

        // Draws board grid
        g.setColor(Color.BLACK);
        g.drawLine(100, 0, 100, 400);
        g.drawLine(200, 0, 200, 400);
        g.drawLine(300, 0, 300, 400);
        g.drawLine(0, 100, 400, 100);
        g.drawLine(0, 200, 400, 200);
        g.drawLine(0, 300, 400, 300);
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
