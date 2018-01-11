package qirkat;

import java.util.ArrayList;
import static qirkat.PieceColor.*;

/** A Player that computes its own moves.
 *  @author Sung Min Shin
 */
class AI extends Player {

    /** Maximum minimax search depth before going to static evaluation. */
    private static final int MAX_DEPTH = 8;
    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI for GAME that will play MYCOLOR. */
    AI(Game game, PieceColor myColor) {
        super(game, myColor);
    }

    @Override
    Move myMove() {
        Main.startTiming();
        Move move = findMove();
        Main.endTiming();

        return move;
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        if (myColor() == WHITE) {
            findMove(b, MAX_DEPTH, true, 1, -INFTY, INFTY);
        } else {
            findMove(b, MAX_DEPTH, true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {
        if (depth == 0 || board.gameOver()) {
            return  staticScore(board());
        }
        Move best = null;
        int value = -INFTY;
        if (sense == -1) {
            value = INFTY;
        }
        ArrayList<Board> boardPossibilities = new ArrayList<>();
        ArrayList<Move> moves = board.getMoves();
        ArrayList<Move> removeTheseMoves = new ArrayList<>();
        for (Move move : moves) {
            Board boardPossibility = new Board(board);
            if (boardPossibility.legalMove(move)) {
                boardPossibility.makeMove(move);
                boardPossibilities.add(boardPossibility);
            } else {
                removeTheseMoves.add(move);
            }
        }
        for (Move move : removeTheseMoves) {
            moves.remove(move);
        }
        if (sense == 1) {
            for (int i = 0; i < boardPossibilities.size(); i++) {
                int max = findMove(boardPossibilities.get(i),
                        depth - 1, false, sense * -1, alpha, beta);
                if (value <= max) {
                    best = moves.get(i);
                    value = max;
                    alpha = Math.max(alpha, value);
                    if (alpha > beta) {
                        break;
                    }
                }
            }
            if (saveMove) {
                _lastFoundMove = best;
            }
        } else {
            for (int i = 0; i < boardPossibilities.size(); i++) {
                int min = findMove(boardPossibilities.get(i),
                        depth - 1, false, sense * -1, alpha, beta);
                if (value >= min) {
                    best = moves.get(i);
                    value = min;
                    beta = Math.min(beta, value);
                    if (alpha > beta) {
                        break;
                    }
                }
            }
            if (saveMove) {
                _lastFoundMove = best;
            }
        }
        return value;
    }

    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        int score = 0;
        for (PieceColor color : board.getBoard()) {
            if (color == WHITE) {
                score++;
            } else {
                score--;
            }
        }
        return score;
    }
}
