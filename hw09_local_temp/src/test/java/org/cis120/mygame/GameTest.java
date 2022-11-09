package org.cis120.mygame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests implementation of methods in Twenty48.java class
 */

public class GameTest {

    /* **** ****** **** checkLegalTurns tests **** ****** **** */

    @Test
    public void testLegalTurnsFilledBoard() {
        int[][] test = new int[4][4];

        int count = 0;
        for (int r = 0; r < test.length; r++) {
            for (int c = 0; c < test[0].length; c++) {
                if ((count % 2 == 1 && r % 2 == 1) || (count % 2 == 0 && r % 2 == 0)) {
                    test[r][c] = 2;
                } else if ((count % 2 == 1 && r % 2 == 0) || (count % 2 == 0 && r % 2 == 1)) {
                    test[r][c] = 4;
                }
                count++;
            }
        }

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);

        assertFalse(t.checkLegalTurns());

    }

    @Test
    public void testLegalTurnsUnfilledBoard() {
        int[][] test = new int[4][4];

        test[0][2] = 2;
        test[3][1] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);

        assertTrue(t.checkLegalTurns());
    }

    @Test
    public void testLegalTurnsFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        int count = 0;
        for (int r = 0; r < test.length; r++) {
            for (int c = 0; c < test[0].length; c++) {
                if ((count % 2 == 1 && r % 2 == 1) || (count % 2 == 0 && r % 2 == 0)) {
                    test[r][c] = 2;
                } else if ((count % 2 == 1 && r % 2 == 0) || (count % 2 == 0 && r % 2 == 1)) {
                    test[r][c] = 4;
                }
                count++;
            }
        }
        test[1][0] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);

        assertTrue(t.checkLegalTurns());
    }

    /* **** ****** **** checkGameWon tests **** ****** **** */

    @Test
    public void testCheckGameWonYes() {
        int[][] test = new int[4][4];

        test[0][2] = 2048;
        test[3][1] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);

        assertTrue(t.checkGameWon());
    }

    @Test
    public void testCheckGameWonNo() {
        int[][] test = new int[4][4];

        test[0][2] = 1024;
        test[3][1] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);

        assertFalse(t.checkGameWon());
    }

    /* **** ****** **** updateRight tests **** ****** **** */

    @Test
    public void testUpdateRightFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        int count = 0;
        for (int r = 0; r < test.length; r++) {
            for (int c = 0; c < test[0].length; c++) {
                if ((count % 2 == 1 && r % 2 == 1) || (count % 2 == 0 && r % 2 == 0)) {
                    test[r][c] = 2;
                } else if ((count % 2 == 1 && r % 2 == 0) || (count % 2 == 0 && r % 2 == 1)) {
                    test[r][c] = 4;
                }
                count++;
            }
        }
        test[0][1] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateRight();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][2]);
    }

    @Test
    public void testUpdateRightUnFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        test[0][1] = 2;
        test[0][0] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateRight();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][3]);
    }

    @Test
    public void testUpdateRightUnFilledBoardNonAdjacentNumbers() {
        int[][] test = new int[4][4];

        test[0][1] = 2;
        test[0][3] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateRight();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][3]);
    }

    @Test
    public void testUpdateRightUnFilledBoardNonAdjacentNumbersDontCombine() {
        int[][] test = new int[4][4];

        test[0][1] = 2;
        test[0][3] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateRight();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][3]);
        assertEquals(2, result[0][2]);
    }

    @Test
    public void testUpdateRightUnFilledBoardAllMoveRight() {
        int[][] test = new int[4][4];

        test[0][0] = 8;
        test[1][0] = 2;
        test[2][0] = 16;
        test[3][0] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateRight();
        int[][] result = t.copyArray();

        assertEquals(4, result[3][3]);
        assertEquals(2, result[1][3]);
        assertEquals(8, result[0][3]);
        assertEquals(16, result[2][3]);
    }

    /* **** ****** **** updateLeft tests **** ****** **** */

    @Test
    public void testUpdateLeftFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        int count = 0;
        for (int r = 0; r < test.length; r++) {
            for (int c = 0; c < test[0].length; c++) {
                if ((count % 2 == 1 && r % 2 == 1) || (count % 2 == 0 && r % 2 == 0)) {
                    test[r][c] = 2;
                } else if ((count % 2 == 1 && r % 2 == 0) || (count % 2 == 0 && r % 2 == 1)) {
                    test[r][c] = 4;
                }
                count++;
            }
        }
        test[0][1] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateLeft();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][0]);
    }

    @Test
    public void testUpdateLeftUnFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        test[0][1] = 2;
        test[0][0] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateLeft();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][0]);
    }

    @Test
    public void testUpdateLeftUnFilledBoardNonAdjacentNumbers() {
        int[][] test = new int[4][4];

        test[0][1] = 2;
        test[0][3] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateLeft();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][0]);
    }

    @Test
    public void testUpdateLeftUnFilledBoardNonAdjacentNumbersDontCombine() {
        int[][] test = new int[4][4];

        test[0][1] = 2;
        test[0][3] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateLeft();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][1]);
        assertEquals(2, result[0][0]);
    }

    @Test
    public void testUpdateLeftUnFilledBoardAllMoveLeft() {
        int[][] test = new int[4][4];

        test[0][3] = 8;
        test[1][3] = 2;
        test[2][3] = 16;
        test[3][3] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateLeft();
        int[][] result = t.copyArray();

        assertEquals(4, result[3][0]);
        assertEquals(2, result[1][0]);
        assertEquals(8, result[0][0]);
        assertEquals(16, result[2][0]);
    }

    /* **** ****** **** updateDown tests **** ****** **** */

    @Test
    public void testUpdateDownFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        int count = 0;
        for (int r = 0; r < test.length; r++) {
            for (int c = 0; c < test[0].length; c++) {
                if ((count % 2 == 1 && r % 2 == 1) || (count % 2 == 0 && r % 2 == 0)) {
                    test[r][c] = 2;
                } else if ((count % 2 == 1 && r % 2 == 0) || (count % 2 == 0 && r % 2 == 1)) {
                    test[r][c] = 4;
                }
                count++;
            }
        }
        test[1][0] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateDown();
        int[][] result = t.copyArray();

        assertEquals(4, result[2][0]);
    }

    @Test
    public void testUpdateDownUnFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        test[1][0] = 2;
        test[0][0] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateDown();
        int[][] result = t.copyArray();

        assertEquals(4, result[3][0]);
    }

    @Test
    public void testUpdateDownUnFilledBoardNonAdjacentNumbers() {
        int[][] test = new int[4][4];

        test[2][1] = 2;
        test[0][1] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateDown();
        int[][] result = t.copyArray();

        assertEquals(4, result[3][1]);
    }

    @Test
    public void testUpdateDownUnFilledBoardNonAdjacentNumbersDontCombine() {
        int[][] test = new int[4][4];

        test[3][1] = 2;
        test[0][1] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateDown();
        int[][] result = t.copyArray();

        assertEquals(2, result[3][1]);
        assertEquals(4, result[2][1]);
    }

    @Test
    public void testUpdateDownUnFilledBoardAllMoveLeft() {
        int[][] test = new int[4][4];

        test[0][0] = 8;
        test[0][1] = 2;
        test[0][2] = 16;
        test[0][3] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateDown();
        int[][] result = t.copyArray();

        assertEquals(4, result[3][3]);
        assertEquals(16, result[3][2]);
        assertEquals(2, result[3][1]);
        assertEquals(8, result[3][0]);
    }

    /* **** ****** **** updateUp tests **** ****** **** */

    @Test
    public void testUpdateUpFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        int count = 0;
        for (int r = 0; r < test.length; r++) {
            for (int c = 0; c < test[0].length; c++) {
                if ((count % 2 == 1 && r % 2 == 1) || (count % 2 == 0 && r % 2 == 0)) {
                    test[r][c] = 2;
                } else if ((count % 2 == 1 && r % 2 == 0) || (count % 2 == 0 && r % 2 == 1)) {
                    test[r][c] = 4;
                }
                count++;
            }
        }
        test[1][0] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateUp();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][0]);
    }

    @Test
    public void testUpdateUpUnFilledBoardAdjacentNumbers() {
        int[][] test = new int[4][4];

        test[1][0] = 2;
        test[0][0] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateUp();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][0]);
    }

    @Test
    public void testUpdateUpUnFilledBoardNonAdjacentNumbers() {
        int[][] test = new int[4][4];

        test[2][1] = 2;
        test[0][1] = 2;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateUp();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][1]);
    }

    @Test
    public void testUpdateUpUnFilledBoardNonAdjacentNumbersDontCombine() {
        int[][] test = new int[4][4];

        test[3][1] = 2;
        test[0][1] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateUp();
        int[][] result = t.copyArray();

        assertEquals(2, result[1][1]);
        assertEquals(4, result[0][1]);
    }

    @Test
    public void testUpdateUpUnFilledBoardAllMoveLeft() {
        int[][] test = new int[4][4];

        test[3][0] = 8;
        test[3][1] = 2;
        test[3][2] = 16;
        test[3][3] = 4;

        Twenty48 t = new Twenty48();
        t.createTestBoard(test);
        t.updateUp();
        int[][] result = t.copyArray();

        assertEquals(4, result[0][3]);
        assertEquals(16, result[0][2]);
        assertEquals(2, result[0][1]);
        assertEquals(8, result[0][0]);
    }

    /* **** ****** **** undo tests **** ****** **** */

    /**
     * NOTE: due to random nature of initializing the board, sometimes
     * these tests may give a false failure. For example if the board is
     * updated up but there are no valid moves in that specific case,
     * the undo functionality may still work but will not output the expected
     * result, as no moves were made.
     */

    @Test
    public void testUndoNoMove() {

        Twenty48 t = new Twenty48();
        int[][] test = t.copyArray();
        t.undoTurn();
        int[][] result = t.copyArray();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(result[i][j], test[i][j]);
            }
        }
    }

    @Test
    public void testUndoOneMove() {

        Twenty48 t = new Twenty48();
        int[][] test = t.copyArray();
        t.playTurn('d');
        t.undoTurn();
        int[][] result = t.copyArray();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(result[i][j], test[i][j]);
            }
        }
    }

    @Test
    public void testUndoMultipleMoves() {

        Twenty48 t = new Twenty48();
        t.playTurn('d');
        int[][] test = t.copyArray();
        t.playTurn('u');
        t.playTurn('r');
        t.undoTurn();
        t.undoTurn();
        int[][] result = t.copyArray();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(result[i][j], test[i][j]);
            }
        }
    }

    /* **** ****** **** save/resume tests **** ****** **** */

    @Test
    public void testSaveResumeNoReset() {
        Twenty48 t = new Twenty48();
        t.playTurn('d');
        t.saveGame();
        int[][] test = t.copyArray();
        t.resumeGame();
        int[][] result = t.copyArray();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(result[i][j], test[i][j]);
            }
        }
    }

    @Test
    public void testSaveResumeNoResetMovesMade() {
        Twenty48 t = new Twenty48();
        t.playTurn('d');
        t.saveGame();
        int[][] test = t.copyArray();
        t.playTurn('r');
        t.playTurn('l');
        t.resumeGame();
        int[][] result = t.copyArray();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(result[i][j], test[i][j]);
            }
        }
    }

    @Test
    public void testSaveResumeReset() {
        Twenty48 t = new Twenty48();
        t.playTurn('d');
        t.saveGame();
        int[][] test = t.copyArray();
        t.reset();
        t.resumeGame();
        int[][] result = t.copyArray();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(result[i][j], test[i][j]);
            }
        }
    }

    @Test
    public void testSaveResumeResetMovesMade() {
        Twenty48 t = new Twenty48();
        t.playTurn('d');
        t.saveGame();
        int[][] test = t.copyArray();
        t.reset();
        t.playTurn('l');
        t.resumeGame();
        int[][] result = t.copyArray();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(result[i][j], test[i][j]);
            }
        }
    }

}
