package qirkat;

import static qirkat.PieceColor.*;
import static qirkat.Command.Type.*;

/** A Player that receives its moves from its Game's getMoveCmnd method.
 *  @author Sung Min Shin
 */
class Manual extends Player {

    /** A Player that will play MYCOLOR on GAME, taking its moves from
     *  GAME. */
    Manual(Game game, PieceColor myColor) {
        super(game, myColor);
        _prompt = myColor + ": ";
    }

    @Override
    Move myMove() {
        Command commandInput = game().getMoveCmnd(_prompt);
        if (commandInput != null) {
            String prompt = commandInput.operands()[0];
            return Move.parseMove(prompt);
        } else {
            return null;
        }

    }

    /** Identifies the player serving as a source of input commands. */
    private String _prompt;
}

