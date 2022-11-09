package org.cis120.mygame;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Twenty48Copy {

    private int[][] board;
    private int numTurns;
    private boolean gameOver;
    private List<int[][]> turns;

    /**
     * Constructor sets up game state.
     */
    public Twenty48Copy() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param direction char to determine turn direction
     * @return whether the turn was successful
     */
    public boolean playTurn(char direction) {
        boolean hasMoved = false;
        if (!checkLegalTurns()) {
            gameOver = true;
            return false;
        }

        if (direction == 'u') {
            hasMoved = updateUp();
        } else if (direction == 'd') {
            hasMoved = updateDown();
        } else if (direction == 'l') {
            hasMoved = updateLeft();
        } else if (direction == 'r') {
            hasMoved = updateRight();
        }

        if (hasMoved) {
            numTurns++;
            turns.add(copyArray());
        }

        printGameState();
        return true;

    }

    public void undoTurn() {
        if (numTurns > 0) {
            turns.remove(turns.size() - 1);
            board = turns.get(turns.size() - 1);
            numTurns--;
            printGameState();
        }
    }

    public int[][] copyArray() {
        int[][] ret = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ret[i][j] = board[i][j];
            }
        }
        return ret;
    }

    public void saveGame() {
        File file = Paths.get("SaveGame.txt").toFile();
        BufferedWriter bw;

        try {
            FileWriter fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    bw.write(board[i][j]);
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("file writer error!");
        }
    }

    public void resumeGame() {
        File file = Paths.get("SaveGame.txt").toFile();
        BufferedReader br;
        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            for (int i = 0; i < 4; i++) {
                String line = br.readLine();
                board[i][0] = (int) line.charAt(0);
                board[i][1] = (int) line.charAt(1);
                board[i][2] = (int) line.charAt(2);
                board[i][3] = (int) line.charAt(3);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("file writer error!");
        }
        numTurns = 0;
        turns = new LinkedList<>();
        turns.add(copyArray());
        printGameState();
    }

    public void addTile() {
        int rand = (int) (Math.random() * 16);
        int col = rand % 4;
        int row = rand / 4;

        if (board[row][col] == 0) {
            board[row][col] = chooseNewTile();
        } else {
            addTile();
        }
    }

    public boolean checkLegalTurnRight() {
        boolean legalTurn = false;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == board[r][c + 1] && board[r][c] != 0) {
                    legalTurn = true;
                } else if (board[r][c] != 0 && board[r][c + 1] == 0) {
                    legalTurn = true;
                }
            }
        }
        if (board[0][3] == 0 || board[1][3] == 0 || board[2][3] == 0 || board[3][3] == 0) {
            legalTurn = true;
        }
        return legalTurn;
    }

    public boolean updateRight() {
        boolean hasMoved = false;
        for (int r = 0; r < 4; r++) {
            for (int c = 3; c > 0; c--) {
                if (board[r][c] == board[r][c - 1] && board[r][c] != 0) {
                    board[r][c] *= 2;
                    board[r][c - 1] = 0;
                    hasMoved = true;

                } else if (board[r][c] != 0 && board[r][c - 1] == 0) {
                    int temp = c - 1;
                    while (temp >= 0) {
                        if (board[r][c] == board[r][temp]) {

                            board[r][c] *= 2;
                            board[r][temp] = 0;
                            hasMoved = true;
                        }
                        temp--;
                    }
                }
            }
        }

        for (int r = 0; r < 4; r++) {
            for (int c = 3; c > 0; c--) {
                int temp = c;
                while (temp > 0) {
                    if (board[r][c] == 0 && board[r][temp - 1] != 0) {
                        board[r][c] = board[r][temp - 1];
                        board[r][temp - 1] = 0;
                        hasMoved = true;
                    }
                    temp--;
                }

            }
        }

        if (hasMoved) {
            addTile();
        }
        return hasMoved;

    }

    public boolean checkLegalTurnLeft() {
        boolean legalTurn = false;
        for (int r = 0; r < 4; r++) {
            for (int c = 1; c < 4; c++) {
                if (board[r][c] == board[r][c - 1] && board[r][c] != 0) {
                    legalTurn = true;
                } else if (board[r][c] != 0 && board[r][c - 1] == 0) {
                    legalTurn = true;
                }
            }
        }
        if (board[0][3] == 0 || board[1][3] == 0 || board[2][3] == 0 || board[3][3] == 0) {
            legalTurn = true;
        }
        return legalTurn;
    }

    public boolean updateLeft() {
        boolean hasMoved = false;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == board[r][c + 1] && board[r][c] != 0) {
                    board[r][c] *= 2;
                    board[r][c + 1] = 0;
                    hasMoved = true;
                } else if (board[r][c] != 0 && board[r][c + 1] == 0) {
                    int temp = c + 1;
                    while (temp <= 3) {
                        if (board[r][c] == board[r][temp]) {

                            board[r][c] *= 2;
                            board[r][temp] = 0;
                            hasMoved = true;
                        }
                        temp++;
                    }
                }
            }
        }

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 3; c++) {
                int temp = c;
                while (temp < 3) {
                    if (board[r][c] == 0 && board[r][temp + 1] != 0) {
                        board[r][c] = board[r][temp + 1];
                        board[r][temp + 1] = 0;
                        hasMoved = true;
                    }
                    temp++;
                }

            }
        }

        if (hasMoved) {
            addTile();
        }
        return hasMoved;

    }

    public boolean checkLegalTurnUp() {
        boolean legalTurn = false;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == board[r + 1][c] && board[r][c] != 0) {
                    legalTurn = true;
                } else if (board[r][c] == 0 && board[r + 1][c] != 0) {
                    legalTurn = true;
                }
            }
        }
        if (board[0][0] == 0 || board[0][1] == 0 || board[0][2] == 0 || board[0][3] == 0) {
            legalTurn = true;
        }
        return legalTurn;
    }

    public boolean updateUp() {
        boolean hasMoved = false;
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 3; r++) {
                if (board[r][c] == board[r + 1][c] && board[r][c] != 0) {
                    board[r][c] *= 2;
                    board[r + 1][c] = 0;
                    hasMoved = true;
                } else if (board[r][c] != 0 && board[r + 1][c] == 0) {
                    int temp = r + 1;
                    while (temp <= 3) {
                        if (board[r][c] == board[temp][c]) {

                            board[r][c] *= 2;
                            board[temp][c] = 0;
                            hasMoved = true;
                        }
                        temp++;
                    }
                }
            }
        }

        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 3; r++) {
                if (c == 0) {

                }
                int temp = r;
                while (temp < 3) {
                    if (board[r][c] == 0 && board[temp + 1][c] != 0) {
                        board[r][c] = board[temp + 1][c];
                        board[temp + 1][c] = 0;
                        hasMoved = true;
                    }
                    temp++;
                }

            }
        }

        if (hasMoved) {
            addTile();
        }

        return hasMoved;
    }

    public boolean checkLegalTurnDown() {
        boolean legalTurn = false;
        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == board[r - 1][c] && board[r][c] != 0) {
                    legalTurn = true;
                } else if (board[r][c] == 0 && board[r - 1][c] != 0) {
                    legalTurn = true;
                }
            }
        }
        if (board[3][0] == 0 || board[3][1] == 0 || board[3][2] == 0 || board[3][3] == 0) {
            legalTurn = true;
        }
        return legalTurn;
    }

    public boolean updateDown() {
        boolean hasMoved = false;
        for (int c = 0; c < 4; c++) {
            for (int r = 3; r > 0; r--) {
                if (board[r][c] == board[r - 1][c] && board[r][c] != 0) {
                    board[r][c] *= 2;
                    board[r - 1][c] = 0;
                    hasMoved = true;
                } else if (board[r][c] != 0 && board[r - 1][c] == 0) {
                    int temp = r - 1;
                    while (temp >= 0) {
                        if (board[r][c] == board[temp][c]) {
                            board[r][c] *= 2;
                            board[temp][c] = 0;
                            hasMoved = true;
                        }
                        temp--;
                    }
                }
            }
        }

        for (int c = 0; c < 4; c++) {
            for (int r = 3; r > 0; r--) {
                int temp = r;
                while (temp > 0) {
                    if (board[r][c] == 0 && board[temp - 1][c] != 0) {
                        board[r][c] = board[temp - 1][c];
                        board[temp - 1][c] = 0;
                        hasMoved = true;
                    }
                    temp--;
                }
            }
        }

        if (hasMoved) {
            addTile();
        }
        return hasMoved;
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if the game hits stalemate
     */
    public boolean checkLegalTurns() {
        boolean legalTurn = false;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == board[r][c + 1]) {
                    legalTurn = true;
                }
            }
        }
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == board[r + 1][c]) {
                    legalTurn = true;
                }
            }
        }

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == 0) {
                    legalTurn = true;
                }
            }
        }
        return legalTurn;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 3) {
                    System.out.print(" | ");
                }
            }
            if (i < 3) {
                System.out.println("\n---------");
            }
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new int[4][4];
        initializeBoard();
        numTurns = 0;
        gameOver = false;
        turns = new LinkedList<>();
        turns.add(copyArray());
    }

    public void initializeBoard() {
        int count = 0;
        int rand = (int) (Math.random() * 16);
        int rand2 = (int) (Math.random() * 16);
        if (rand == rand2) {
            rand2++;
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (count == rand) {
                    board[r][c] = chooseNewTile();
                } else if (count == rand2) {
                    board[r][c] = chooseNewTile();
                }
                count++;
            }
        }

        printGameState();

    }

    public void createInvalidBoard() {
        int count = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if ((count % 2 == 1 && r % 2 == 1) || (count % 2 == 0 && r % 2 == 0)) {
                    board[r][c] = 2;
                } else if ((count % 2 == 1 && r % 2 == 0) || (count % 2 == 0 && r % 2 == 1)) {
                    board[r][c] = 4;
                }
                count++;
            }
        }
        printGameState();
    }

    public void createTestBoard(int[][] test) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // check whether given number is a power of 2
                double n = (Math.log(test[i][j]) / Math.log(2));
                if ((int) (Math.ceil(n)) == (int) (Math.floor(n))) {
                    board[i][j] = test[i][j];
                }
            }
        }
        printGameState();
    }

    public int chooseNewTile() {
        int ret = 0;
        int rand = (int) (Math.random() * 2);
        if (rand == 0) {
            ret = 2;
        } else {
            ret = 4;
        }
        return ret;
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */
    public boolean checkGameOver() {
        return gameOver;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public int getCell(int c, int r) {
        return board[r][c];
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Twenty48Copy t = new Twenty48Copy();
        t.playTurn('u');
        t.playTurn('r');
        t.saveGame();
        System.out.println();
        t.resumeGame();

    }
}
