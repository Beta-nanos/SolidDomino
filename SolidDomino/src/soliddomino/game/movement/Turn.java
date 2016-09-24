package soliddomino.game.movement;

import soliddomino.game.components.Piece;
import soliddomino.game.managers.Board;
import soliddomino.game.components.Player;

public class Turn {

    public boolean hasWon(Player currentPlayer) {
        return currentPlayer.getPieces().isEmpty();
    }

    public boolean validateMove(Movement currentMove, Board board) {
        boolean validMove = false;
        if (currentMove != null) {
            Piece piece = currentMove.getPiece();

            if (currentMove.isPass()
                    || piece.getLeftValue() == board.getMostLeftValue()
                    || piece.getRightValue() == board.getMostRightValue()) {
                validMove = true;
            }
        }

        return validMove;
    }
}
