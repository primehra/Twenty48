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
public class Twenty48 {

    private int[][] board;
    private int numTurns;
    private boolean gameOver;
    private List<int[][]> turns;

    /**
     * Constructor sets up game state.
     */
    public Twenty48() {
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

        // if there are no legal moves, do not play the turn
        if (!checkLegalTurns()) {
            gameOver = true;
            return false;
        }

        // plays move based on given direction
        if (direction == 'u') {
            hasMoved = updateUp();
        } else if (direction == 'd') {
            hasMoved = updateDown();
        } else if (direction == 'l') {
            hasMoved = updateLeft();
        } else if (direction == 'r') {
            hasMoved = updateRight();
        }

        // if a tile has been moved, increment number of turns and add game state to
        // turns list
        if (hasMoved) {
            numTurns++;
            turns.add(copyArray());
        }

        printGameState();
        gameOver = false;
        return true;

    }

    /**
     * undoTurn allows players to undo their most recent turn
     */
    public void undoTurn() {
        if (numTurns > 0) {
            turns.remove(turns.size() - 1);
            board = turns.get(turns.size() - 1);
            numTurns--;
            printGameState();
        }
    }

    /**
     * copyArray is a function which returns a copy of the current game state
     */
    public int[][] copyArray() {

        int[][] ret = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ret[i][j] = board[i][j];
            }
        }
        return ret;
    }

    /**
     * saveGame allows players to save the current game state to a file
     */
    public void saveGame() {
        File file = Paths.get("SaveGame.txt").toFile();
        BufferedWriter bw;

        try {
            FileWriter fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);

            // write each of the cells to file, with each line separated
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

    /**
     * resumeGame allows players to resume the game at the most recent
     * saved state
     */
    public void resumeGame() {
        File file = Paths.get("SaveGame.txt").toFile();
        BufferedReader br;
        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            // read the file in line by line and add each number to a row of the game board
            for (int i = 0; i < 4; i++) {
                String line = br.readLine();
                board[i][0] = line.charAt(0);
                board[i][1] = line.charAt(1);
                board[i][2] = line.charAt(2);
                board[i][3] = line.charAt(3);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("file writer error!");
        }
        // reset game state
        numTurns = 0;
        turns = new LinkedList<>();
        turns.add(copyArray());
        printGameState();
    }

    /**
     * addTile is a helper function which adds a random tile to the board
     */
    private void addTile() {
        // choose a random tile
        int rand = (int) (Math.random() * 16);
        int col = rand % 4;
        int row = rand / 4;

        // if chosen tile is empty, add tile. else run the function again
        if (board[row][col] == 0) {
            board[row][col] = chooseNewTile();
        } else {
            addTile();
        }
    }

    /**
     * updateRight deals with the action of executing a 'right' turn, when the right
     * arrow key is pressed.
     * It first checks for all adjacent tiles of the same number to be combined and
     * then for all non-adjacent
     * tiles which can be combined. After combining these tiles, it iterates through
     * the board again to look for
     * any tiles which have a 0 to their right and moves them the appropriate
     * amount. It also returns a boolean,
     * indicating whether any tiles were actually combined and/or moved.
     *
     * @return whether a tile was moved
     */
    public boolean updateRight() {
        boolean hasMoved = false;

        // check if any tiles need to be combined
        for (int r = 0; r < 4; r++) {
            for (int c = 3; c > 0; c--) {
                // checks whether there are two adjacent tiles to be combined in the if
                // checks whether there are two non-adjacent tiles to be combined in the else if
                if (board[r][c] == board[r][c - 1] && board[r][c] != 0) {
                    // combines in the right tile, wipes the left tile
                    board[r][c] *= 2;
                    board[r][c - 1] = 0;
                    hasMoved = true;

                } else if (board[r][c] != 0 && board[r][c - 1] == 0) {
                    int temp = c - 1;
                    // iterate through all tiles to the right of current tile
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

        // check if any tiles need to be moved
        for (int r = 0; r < 4; r++) {
            for (int c = 3; c > 0; c--) {
                int temp = c;
                // iterate through all tiles to the right of current tile and move if there is
                // space
                while (temp > 0) {
                    // if there is a zero tile to the right of a non-zero tile, move the non-zero
                    // tile right
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

    /**
     * updateLeft deals with the action of executing a 'left' turn, when the left
     * arrow key is pressed.
     * It first checks for all adjacent tiles of the same number to be combined and
     * then for all non-adjacent
     * tiles which can be combined. After combining these tiles, it iterates through
     * the board again to look for
     * any tiles which have a 0 to their left and moves them the appropriate amount.
     * It also returns a boolean,
     * indicating whether any tiles were actually combined and/or moved.
     *
     * @return whether a tile was moved
     */
    public boolean updateLeft() {
        boolean hasMoved = false;

        // check whether any tiles need to be combined
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 3; c++) {
                // check for adjacent tiles to be combined in the if
                // check for non-adjacent tiles to be combined in the else if
                if (board[r][c] == board[r][c + 1] && board[r][c] != 0) {
                    board[r][c] *= 2;
                    board[r][c + 1] = 0;
                    hasMoved = true;
                } else if (board[r][c] != 0 && board[r][c + 1] == 0) {
                    int temp = c + 1;
                    // iterate through all tiles to the left of current tile
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

        // check for tiles to be moved
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 3; c++) {
                int temp = c;
                // iterate through all tiles to the left of current tile
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

    /**
     * updateUp deals with the action of executing an 'up' turn, when the up arrow
     * key is pressed.
     * It follows the same procedure as updateRight and updateLeft
     *
     * @return whether a tile was moved
     */
    public boolean updateUp() {
        boolean hasMoved = false;

        // check whether any tiles need to be combined
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 3; r++) {
                // check whether any adjacent tiles need to be combined in the if
                // check whether any non-adjacent tiles need to be combined in the else if
                if (board[r][c] == board[r + 1][c] && board[r][c] != 0) {
                    board[r][c] *= 2;
                    board[r + 1][c] = 0;
                    hasMoved = true;
                } else if (board[r][c] != 0 && board[r + 1][c] == 0) {
                    int temp = r + 1;
                    // iterate through all tiles below current tile
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

        // check whether any tiles need to be moved
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 3; r++) {
                int temp = r;
                // iterate through all tiles below current tile
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

    /**
     * updateDown deals with the action of executing a 'down' turn, when the down
     * arrow key is pressed.
     * It follows the same procedure as all other update methods
     *
     * @return whether a tile was moved
     */
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
     * checkLegalTurns checks whether the game is over by determining whether there
     * are any moves left.
     *
     * @return true if there are legal moves left, false if none
     */
    public boolean checkLegalTurns() {
        boolean legalTurn = false;
        // check for any horizontally adjacent tiles to be combined
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == board[r][c + 1]) {
                    legalTurn = true;
                }
            }
        }
        // check for any vertically adjacent tiles to be combined
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == board[r + 1][c]) {
                    legalTurn = true;
                }
            }
        }

        // check for any empty tiles
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

    /**
     * initializeBoard creates a random game state with two starter tiles.
     */
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

    /**
     * createTestBoard sets the board based on a given 2d array.
     * createTestBoard also checks whether the values in the given array are valid.
     *
     * @param test int[][] to set game state
     */
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

    /**
     * chooseNewTile randomly chooses either 2 or 4 to be placed in a new tile.
     *
     * @return either 2 or 4, randomly chosen.
     */
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
     * checkGameOver is a getter for the current state of the game
     * 
     * @return true if there are no legal moves left,
     *         false if there are legal moves left.
     */
    public boolean checkGameOver() {
        return gameOver;
    }

    /**
     * checkGameWon checks whether a 2048 tile (or higher) is present on the board
     *
     * @return true if a 2048 tile or higher is present,
     *         false if there are no tiles higher than or equal to 2048.
     */
    public boolean checkGameWon() {
        boolean ret = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] >= 2048) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board.
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
        Twenty48 t = new Twenty48();
        t.playTurn('u');
        t.playTurn('r');
        t.saveGame();
        System.out.println();
        t.resumeGame();

    }
}
