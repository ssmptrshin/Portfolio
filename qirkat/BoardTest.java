package qirkat;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** Tests of the Board class.
 *  @author Sung Min Shin
 */
public class BoardTest {

    private static final String INIT_BOARD =
        "  b b b b b\n  b b b b b\n  b b - w w\n  w w w w w\n  w w w w w";

    private static final String[] GAME1 =
    { "c2-c3", "c4-c2",
      "c1-c3", "a3-c1",
      "c3-a3", "c5-c4",
      "a3-c5-c3",
    };

    private static final String GAME1_BOARD =
        "  b b - b b\n  b - - b b\n  - - w w w\n  w - - w w\n  w w b w w";

    private static void makeMoves(Board b, String[] moves) {
        for (String s : moves) {
            b.makeMove(Move.parseMove(s));
        }
    }

    @Test
    public void testInit1() {
        Board b0 = new Board();
        assertEquals(INIT_BOARD, b0.toString());
    }

    @Test
    public void testMoves1() {
        Board b0 = new Board();
        makeMoves(b0, GAME1);
        assertEquals(GAME1_BOARD, b0.toString());
    }

    @Test
    public void testUndo() {
        Board b0 = new Board();
        Board b1 = new Board(b0);
        makeMoves(b0, GAME1);
        Board b2 = new Board(b0);
        for (int i = 0; i < GAME1.length; i += 1) {
            b0.undo();
        }
        assertEquals("failed to return to start", b1.toString(), b0.toString());
        makeMoves(b0, GAME1);
        assertEquals("second pass failed to reach same position",
                b2.toString(), b0.toString());

    }

    @Test
    public void testJumpPossible() {
        Board b0 = new Board();
        b0.setPieces("  w w w w w\n "
                + "w w w w w\n"
                + " b b - w w\n "
                + "b b b b b\n b b b b b\n ", PieceColor.WHITE);
        assertFalse(b0.jumpPossible());

        b0.setPieces("  w w w w w\n"
                + " w w - w w\n"
                + " b b w w w\n "
                + "b b b b b\n "
                + "b b b b b\n ", PieceColor.BLACK);
        assertTrue(b0.jumpPossible());

        Board testing = new Board();
        testing.setPieces(" w - - - -\n"
                + " - b - - -\n"
                + " - - - b -\n"
                + " - - - - -\n"
                + " - - - - -\n", PieceColor.WHITE);
        ArrayList<Move> test = new ArrayList();
        testing.getJumps(test, 0);

        Move sub = Move.move('c', '3', 'e', '3');
        Move notOK = Move.move('a', '1', 'c', '3');
        Move hello = Move.move('a', '1', 'c', '3', sub);
        assertTrue(testing.legalMove(hello));
        assertFalse(testing.legalMove(notOK));
    }

    @Test
    public void testLegalMove() {
        Board testing = new Board();
        testing.setPieces(" w - - - -\n"
                + " - b - - -\n"
                + " - - - b -\n"
                + " - - - - -\n"
                + " - - - - -\n", PieceColor.WHITE);
        testing.setPieces(" w w - w w\n"
                + " w w - w w \n"
                + " b b w w w\n"
                + " b b - b b\n"
                + " b b b b b \n", PieceColor.BLACK);
        assertFalse(testing.legalMove(Move.move('c', '5', 'c', '4')));
        testing.setPieces(" w w b w w \n"
                + " w - - w w\n"
                + " w - - w w\n"
                + " b b - b b\n"
                + " b b b b b\n", PieceColor.BLACK);
        assertFalse(testing.legalMove(Move.parseMove("e4-c2")));
        testing.setPieces(" w w - w w \n"
                + " w - - - w\n"
                + " w - b - b\n"
                + " b b - b w\n"
                + " b b b b b\n", PieceColor.WHITE);
        assertTrue(testing.legalMove(Move.parseMove("e4-c4-c2")));
        testing.setPieces(" wb---\n"
                + " -b-b-\n"
                + " -b---\n"
                + " -bb--\n"
                + " -b---\n", PieceColor.WHITE);
        assertTrue(
                testing.legalMove(Move.parseMove("a1-c1-a3-c3-a5-c5-c3-e1")));
        testing.setPieces(" -----\n --b--\n -----\n -w---\n -----",
                PieceColor.WHITE);
        testing.makeMove(Move.parseMove("b4-c4"));
        testing.makeMove(Move.parseMove("c2-b2"));
        assertFalse(testing.legalMove(Move.parseMove("c4-b4")));
        assertFalse(testing.legalMove(Move.parseMove("b2-c2")));
        testing.setPieces(" w w w w w\n"
                + " w w - w w\n"
                + " b b w w w\n"
                + " b b b b b\n"
                + " b b b b b", PieceColor.BLACK);
        assertTrue(testing.legalMove(Move.parseMove("c4-c2")));
        testing.setPieces("wwwww\n wwwww\n bb-ww\n bbbbb\n bbbbb",
                PieceColor.WHITE);
        testing.makeMove(Move.parseMove("c2-c3"));
        testing.makeMove(Move.parseMove("c4-c2"));
        testing.setPieces("b b b b b\n"
                + "  b - - b b\n"
                + "  - - w w w\n"
                + "  w - - w w\n"
                + "  w w b w w", PieceColor.BLACK);
        assertTrue(testing.legalMove(Move.parseMove("d4-b2")));
    }


    @Test
    public void testGetMoves() {
        Board newboard = new Board();
        newboard.makeMove(Move.parseMove("c2-c3"));

        ArrayList<Move> l;
        l = newboard.getMoves();

        for (Move i : l) {
            System.out.println(i.toString());
        }
    }

}
