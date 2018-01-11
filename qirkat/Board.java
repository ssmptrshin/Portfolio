package qirkat;


import java.util.ArrayList;
import java.util.Formatter;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import static qirkat.PieceColor.*;
import static qirkat.Move.*;

/** A Qirkat board.   The squares are labeled by column (a char value between
 *  'a' and 'e') and row (a char value between '1' and '5'.

 *  Moves on this board are denoted by Moves.
 *  @author Sung Min Shin
 */
class Board extends Observable {

    /** A new, cleared board at the start of the game. */
    Board() {
        clear();
    }

    /** A copy of B. */
    Board(Board b) {
        internalCopy(b);
    }

    /** Return a constant view of me (allows any access method, but no
     *  method that modifies it). */
    Board constantView() {
        return this.new ConstantBoard();
    }

    /** Clear me to my starting state, with pieces in their initial
     *  positions. */
    void clear() {
        _whoseMove = WHITE;
        _gameOver = false;
        horizontal = new int[MAX_INDEX + 1];
        for (int i = 0; i <= MAX_INDEX; i++) {
            horizontal[i] = -1;
        }
        board = new PieceColor[] {WHITE, WHITE, WHITE, WHITE, WHITE,
                                  WHITE, WHITE, WHITE, WHITE, WHITE,
                                  BLACK, BLACK, EMPTY, WHITE, WHITE,
                                  BLACK, BLACK, BLACK, BLACK, BLACK,
                                  BLACK, BLACK, BLACK, BLACK, BLACK};

        setChanged();
        notifyObservers();
    }

    /** Copy B into me. */
    void copy(Board b) {
        internalCopy(b);
    }

    /** Copy B into me. */
    private void internalCopy(Board b) {
        PieceColor[] boardcopy = new PieceColor[MAX_INDEX + 1];
        for (int i = 0; i <= MAX_INDEX; i++) {
            boardcopy[i] = b.board[i];
        }
        board = boardcopy;
        _whoseMove = b.whoseMove();
        _gameOver = b.gameOver();
        _moves = b._moves;
        horizontal = b.horizontal;
        setChanged();
        notifyObservers();
    }

    /** Set my contents as defined by STR.  STR consists of 25 characters,
     *  each of which is b, w, or -, optionally interspersed with whitespace.
     *  These give the contents of the Board in row-major order, starting
     *  with the bottom row (row 1) and left column (column a). All squares
     *  are initialized to allow horizontal movement in either direction.
     *  NEXTMOVE indicates whose move it is.
     */
    void setPieces(String str, PieceColor nextMove) {
        if (nextMove == EMPTY || nextMove == null) {
            throw new IllegalArgumentException("bad player color");
        }
        str = str.replaceAll("\\s", "");
        if (!str.matches("[bw-]{25}")) {
            throw new IllegalArgumentException("bad board description");
        }


        for (int k = 0; k < str.length(); k += 1) {
            switch (str.charAt(k)) {
            case '-':
                set(k, EMPTY);
                break;
            case 'b': case 'B':
                set(k, BLACK);
                break;
            case 'w': case 'W':
                set(k, WHITE);
                break;
            default:
                break;
            }
        }
        _whoseMove = nextMove;

        setChanged();
        notifyObservers();
    }

    /** Return true iff the game is over: i.e., if the current player has
     *  no moves. */
    boolean gameOver() {
        return _gameOver;
    }

    /** Return the current contents of square C R, where 'a' <= C <= 'e',
     *  and '1' <= R <= '5'.  */
    PieceColor get(char c, char r) {
        assert validSquare(c, r);
        return get(index(c, r));
    }

    /** Return the current contents of the square at linearized index K. */
    PieceColor get(int k) {
        assert validSquare(k);
        return board[k];
    }

    /** Set get(C, R) to V, where 'a' <= C <= 'e', and
     *  '1' <= R <= '5'. */
    private void set(char c, char r, PieceColor v) {
        assert validSquare(c, r);
        set(index(c, r), v);
    }

    /** Set get(K) to V, where K is the linearized index of a square. */
    private void set(int k, PieceColor v) {
        assert validSquare(k);
        board[k] = v;
    }

    /** Legal conditions.
     * @param mov a move
     * @return if move is legal
     */
    boolean legalConditions(Move mov) {
        if (gameOver()) {
            return false;
        }
        if (index(mov.col1(), mov.row1())
                == horizontal[index(mov.col0(), mov.row0())]) {
            return false;
        }
        if (whoseMove() == WHITE) {
            if (mov.row0() == '5' && (mov.isRightMove() || mov.isLeftMove())) {
                return false;
            }
        } else if (mov.row0()
                == '1' && (mov.isRightMove() || mov.isLeftMove())) {
            return false;
        }
        if (mov.isVestigial()) {
            return false;
        }
        if (board[index(mov.col0(), mov.row0())] == EMPTY) {
            return false;
        }
        if (board[index(mov.col1(), mov.row1())] != EMPTY) {
            return false;
        }
        if (!mov.isJump() && whoseMove() == WHITE) {
            if (mov.row1() < mov.row0()) {
                return false;
            }
        } else if (!mov.isJump() && whoseMove() == BLACK) {
            if (mov.row1() > mov.row0()) {
                return false;
            }
        }
        if (board[index(mov.jumpedCol(), mov.jumpedRow())] == whoseMove()) {
            return false;
        }
        if (mov.col0() != mov.col1() && mov.row0() != mov.row1()) {
            if (index(mov.col0(), mov.row0()) % 2 != 0) {
                return false;
            }
        }
        return true;
    }


    /** Return true iff MOV is legal on the current board. */
    boolean legalMove(Move mov) {
        if (!legalConditions(mov)) {
            return false;
        }
        if (jumpPossible()) {
            if (!mov.isJump()) {
                return false;
            }
            Board boardcopy = new Board(this);
            boardcopy.set(index(mov.jumpedCol(), mov.jumpedRow()), EMPTY);
            boardcopy.set(index(mov.col1(), mov.row1()), whoseMove());
            boardcopy.set(index(mov.col0(), mov.row0()), EMPTY);
            boardcopy.setWhoseMove(whoseMove());
            if (mov.jumpTail() != null) {
                return boardcopy.legalMove(mov.jumpTail());
            } else {
                if (!boardcopy.jumpPossible()) {
                    return true;
                } else {
                    ArrayList<Move> jumps = new ArrayList<>();
                    boardcopy.getJumps(jumps, index(mov.col1(), mov.row1()));
                    if (jumps.size() == 1
                            && jumps.get(0).col1() != mov.col0()
                            && jumps.get(0).row1() != mov.row0()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /** Return a list of all legal moves from the current position. */
    ArrayList<Move> getMoves() {
        ArrayList<Move> result = new ArrayList<>();
        getMoves(result);
        return result;
    }

    /** Add all legal moves from the current position to MOVES. */
    void getMoves(ArrayList<Move> moves) {
        if (gameOver()) {
            return;
        }
        if (jumpPossible()) {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                getJumps(moves, k);
            }
        } else {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                getMoves(moves, k);
            }
        }
    }

    /** Add all legal non-capturing moves from the position
     *  with linearized index K to MOVES. */
    private void getMoves(ArrayList<Move> moves, int k) {
        ArrayList<Integer> emptySpaces = new ArrayList();
        for (int i = -6; i < -3; i++) {
            if (k + i <= MAX_INDEX && k + i >= 0
                    && board[k] == whoseMove()
                    && board[k + i] == EMPTY
                    && Math.abs(((k + i) % 5) - k % 5) <= 1) {
                emptySpaces.add(k + i);
            }
        }
        for (int i = 4; i < 7; i++) {
            if (k + i <= MAX_INDEX && k + i >= 0
                    && board[k] == whoseMove()
                    && board[k + i] == EMPTY
                    && Math.abs(((k + i) % 5) - k % 5) <= 1) {
                emptySpaces.add(k + i);
            }
        }
        if (k - 1 <= MAX_INDEX && k - 1 >= 0
                && board[k] == whoseMove()
                && board[k - 1] == EMPTY
                && Math.abs(((k - 1) % 5) - k % 5) <= 1) {
            emptySpaces.add(k - 1);
        }
        if (k + 1 <= MAX_INDEX && k + 1 >= 0
                && board[k] == whoseMove()
                && board[k + 1] == EMPTY
                && Math.abs(((k + 1) % 5) - k % 5) <= 1) {
            emptySpaces.add(k + 1);
        }
        for (int index : emptySpaces) {
            moves.add(Move.move(Move.col(k), Move.row(k),
                    Move.col(index), Move.row(index)));
        }
    }

    /** Add all legal captures from the position with linearized index K
     *  to MOVES. */
    public void getJumps(ArrayList<Move> moves, int k) {
        ArrayList<Integer> spacesNotEmpty = new ArrayList();
        for (int i = -6; i < -3; i++) {
            if (k + i <= MAX_INDEX && k + i >= 0
                    && board[k] == whoseMove()
                    && board[k + i] == board[k].opposite()
                    && Math.abs(((k + i) % 5) - k % 5) <= 1) {
                spacesNotEmpty.add(k + i);
            }
        }
        for (int i = 4; i < 7; i++) {
            if (k + i <= MAX_INDEX && k + i >= 0
                    && board[k] == whoseMove()
                    && board[k + i] == board[k].opposite()
                    && Math.abs(((k + i) % 5) - k % 5) <= 1) {
                spacesNotEmpty.add(k + i);
            }
        }
        if (k - 1 <= MAX_INDEX && k - 1 >= 0
                && board[k] == whoseMove()
                && board[k - 1] == board[k].opposite()
                && Math.abs(((k - 1) % 5) - k % 5) <= 1) {
            spacesNotEmpty.add(k - 1);
        }
        if (k + 1 <= MAX_INDEX && k + 1 >= 0
                && board[k] == whoseMove()
                && board[k + 1] == board[k].opposite()
                && Math.abs(((k + 1) % 5) - k % 5) <= 1) {
            spacesNotEmpty.add(k + 1);
        }
        for (int index : spacesNotEmpty) {
            if ((index - k) * 2 + k <= MAX_INDEX
                    && (index - k) * 2 + k >= 0
                    && board[(index - k) * 2 + k] == EMPTY
                    && (Math.abs(((k + index) % 5) - k % 5) == 2
                    || Math.abs(((k + index) % 5) - k % 5) == 1)) {
                try {
                    Move newMove = Move.move(Move.col(k), Move.row(k),
                            Move.col(k + (2 * index) - (2 * k)),
                            Move.row(k + (2 * index) - (2 * k)));
                    Board boardcopy = new Board(this);
                    boardcopy.makeMove(newMove);
                    boardcopy.setWhoseMove(whoseMove());
                    if (!boardcopy.jumpPossible(2 * index - k)) {
                        moves.add(newMove);
                    } else {
                        ArrayList<Move> potentialJumps = new ArrayList();
                        boardcopy.getJumps(potentialJumps,
                                k + (2 * index) - (2 * k));
                        for (Move jump : potentialJumps) {
                            moves.add(move(newMove.col0(), newMove.row0(),
                                    newMove.col1(), newMove.row1(), jump));
                        }
                    }
                } catch (AssertionError e) {
                    break;
                }
            }
        }
    }

    /** Return true iff MOV is a valid jump sequence on the current board.
     *  MOV must be a jump or null.  If ALLOWPARTIAL, allow jumps that
     *  could be continued and are valid as far as they go.  */
    boolean checkJump(Move mov, boolean allowPartial) {
        if (mov == null) {
            return true;
        }
        if (!allowPartial) {
            if (board[mov.jumpedIndex()] != whoseMove().opposite()) {
                return false;
            }
            if (board[index(mov.col1(), mov.row1())] != EMPTY) {
                return false;
            }
            return true;
        } else {
            while (mov.jumpTail() != null) {
                if (board[mov.jumpedIndex()] != whoseMove().opposite()) {
                    return false;
                }
                if (board[index(mov.col1(), mov.row1())] != EMPTY) {
                    return false;
                }
                mov = mov.jumpTail();
            }
        }
        return true;
    }

    /** Return true iff a jump is possible for a piece at position C R. */
    boolean jumpPossible(char c, char r) {
        return jumpPossible(index(c, r));
    }

    /** Return true iff a jump is possible for a piece at position with
     *  linearized index K. */
    boolean jumpPossible(int k) {
        ArrayList<Integer> spacesNotEmpty = new ArrayList();
        for (int i = -6; i < -3; i++) {
            if (k + i <= MAX_INDEX && k + i >= 0
                    && board[k] == whoseMove()
                    && board[k + i] == board[k].opposite()
                    && Math.abs(((k + i) % 5) - k % 5) <= 1) {
                spacesNotEmpty.add(k + i);
            }
        }
        for (int i = 4; i < 7; i++) {
            if (k + i <= MAX_INDEX && k + i >= 0
                    && board[k] == whoseMove()
                    && board[k + i] == board[k].opposite()
                    && Math.abs(((k + i) % 5) - k % 5) <= 1) {
                spacesNotEmpty.add(k + i);
            }
        }
        if (k - 1 <= MAX_INDEX && k - 1 >= 0
                && board[k] == whoseMove()
                && board[k - 1] == board[k].opposite()
                && Math.abs(((k - 1) % 5) - k % 5) <= 1) {
            spacesNotEmpty.add(k - 1);
        }
        if (k + 1 <= MAX_INDEX && k + 1 >= 0
                && board[k] == whoseMove()
                && board[k + 1] == board[k].opposite()
                && Math.abs(((k + 1) % 5) - k % 5) <= 1) {
            spacesNotEmpty.add(k + 1);
        }

        for (int index : spacesNotEmpty) {
            if ((2 * index) - k > MAX_INDEX
                    || (2 * index) - k < 0) {
                continue;
            }

            if (k % 2 != 0
                    && ((Math.abs(2 * index - k - k) == 12)
                    || Math.abs(2 * index - k - k) == 8)) {
                continue;
            }

            if (board[(index - k) * 2 + k] != EMPTY) {
                continue;
            }

            if (Math.abs(((k + index) % 5) - k % 5) != 2
                    && Math.abs(((k + index) % 5) - k % 5) != 1) {
                continue;
            }
            return true;
        }
        return false;
    }

    /** Return true iff a jump is possible from the current board. */
    boolean jumpPossible() {
        for (int k = 0; k <= MAX_INDEX; k += 1) {
            if (jumpPossible(k)) {
                return true;
            }
        }
        return false;
    }

    /** Return the color of the player who has the next move.  The
     *  value is arbitrary if gameOver(). */
    PieceColor whoseMove() {
        return _whoseMove;
    }

    /** Perform the move C0R0-C1R1, or pass if C0 is '-'.  For moves
     *  other than pass, assumes that legalMove(C0, R0, C1, R1). */
    void makeMove(char c0, char r0, char c1, char r1) {
        makeMove(Move.move(c0, r0, c1, r1, null));
    }

    /** Make the multi-jump C0 R0-C1 R1..., where NEXT is C1R1....
     *  Assumes the result is legal. */
    void makeMove(char c0, char r0, char c1, char r1, Move next) {
        makeMove(Move.move(c0, r0, c1, r1, next));
    }

    /** Make the Move MOV on this Board, assuming it is legal. */
    void makeMove(Move mov) {
        if (!isMove()) {
            _gameOver = true;
        }
        assert legalMove(mov);

        if (!mov.isJump()) {
            board[index(mov.col1(), mov.row1())] = whoseMove();
            board[index(mov.col0(), mov.row0())] = EMPTY;
            horizontal[index(mov.col1(), mov.row1())]
                    = index(mov.col0(), mov.row0());
            _whoseMove = whoseMove().opposite();

        } else {
            if (mov.jumpTail() == null) {
                board[mov.jumpedIndex()] = EMPTY;
                board[index(mov.col0(), mov.row0())] = EMPTY;
                board[index(mov.col1(), mov.row1())] = whoseMove();
                _whoseMove = whoseMove().opposite();
                if (mov != null) {
                    horizontal[index(mov.col0(), mov.row0())] = -1;
                }


            } else {
                while (mov != null) {
                    board[mov.jumpedIndex()] = EMPTY;
                    board[index(mov.col0(), mov.row0())] = EMPTY;
                    board[index(mov.col1(), mov.row1())] = whoseMove();
                    mov = mov.jumpTail();
                    if (mov != null) {
                        horizontal[index(mov.col0(), mov.row0())] = -1;
                    }
                }
                _whoseMove = whoseMove().opposite();
            }
        }
        _moves.push(new Board(this));

        setChanged();
        notifyObservers();
    }

    /** Undo the last move, if any. */
    void undo() {
        _moves.pop();
        if (_moves.empty()) {
            clear();
        } else {
            board = _moves.peek().board;
            _whoseMove = _moves.peek().whoseMove();
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return toString(false);
    }

    /** Return a text depiction of the board.  If LEGEND, supply row and
     *  column numbers around the edges. */
    String toString(boolean legend) {
        Formatter out = new Formatter();
        if (!legend) {
            out.format("  %s %s %s %s %s\n",
                    board[MAX_INDEX - 4].shortName(),
                    board[MAX_INDEX - 3].shortName(),
                    board[MAX_INDEX - 2].shortName(),
                    board[MAX_INDEX - 1].shortName(),
                    board[MAX_INDEX].shortName());

            out.format("  %s %s %s %s %s\n",
                    board[15].shortName(), board[16].shortName(),
                    board[MAX_INDEX - 7].shortName(),
                    board[MAX_INDEX - 6].shortName(),
                    board[MAX_INDEX - 5].shortName());

            out.format("  %s %s %s %s %s\n",
                    board[10].shortName(), board[11].shortName(),
                    board[12].shortName(),
                    board[13].shortName(), board[14].shortName());

            out.format("  %s %s %s %s %s\n",
                    board[5].shortName(), board[6].shortName(),
                    board[7].shortName(),
                    board[8].shortName(), board[9].shortName());

            out.format("  %s %s %s %s %s",
                    board[0].shortName(), board[1].shortName(),
                    board[2].shortName(),
                    board[3].shortName(), board[4].shortName());

        }
        return out.toString();
    }

    /** Return true iff there is a move for the current player. */
    public boolean isMove() {
        for (int k = 0; k <= MAX_INDEX; k++) {
            ArrayList<Move> moves = new ArrayList<>();
            getMoves(moves, k);
            if (board[k] == whoseMove()
                    && jumpPossible(k) || !moves.isEmpty()) {
                return true;
            }
        }
        _gameOver = true;
        return false;
    }

    /** set whosemove to the specified color.
     * @param color whosemove
     * */
    void setWhoseMove(PieceColor color) {
        _whoseMove = color;
    }

    /** returns the board.*/
    public PieceColor[] getBoard() {
        return board;
    }

    /** The game board. */
    private PieceColor[] board;

    /** keeps track of horizontal moves.*/
    private int[] horizontal;


    /** keeps track of all the moves made.*/
    private Stack<Board> _moves = new Stack<>();




    /** Player that is on move. */
    private PieceColor _whoseMove;

    /** Set true when game ends. */
    private boolean _gameOver;

    /** Convenience value giving values of pieces at each ordinal position. */
    static final PieceColor[] PIECE_VALUES = PieceColor.values();

    /** One cannot create arrays of ArrayList<Move>, so we introduce
     *  a specialized private list type for this purpose. */
    private static class MoveList extends ArrayList<Move> {
    }

    /** A read-only view of a Board. */
    private class ConstantBoard extends Board implements Observer {
        /** A constant view of this Board. */
        ConstantBoard() {
            super(Board.this);
            Board.this.addObserver(this);
        }

        @Override
        void copy(Board b) {
            assert false;
        }

        @Override
        void clear() {
            assert false;
        }

        @Override
        void makeMove(Move move) {
            assert false;
        }

        /** Undo the last move. */
        @Override
        void undo() {
            assert false;
        }

        @Override
        public void update(Observable obs, Object arg) {
            super.copy((Board) obs);
            setChanged();
            notifyObservers(arg);
        }
    }
}
