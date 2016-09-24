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
            DIRECTION currentDirection = currentMove.getDirection();
            if ((currentMove.isPass()
                    || (currentDirection == DIRECTION.LEFT && (piece.getLeftValue() == board.getMostLeftValue() || piece.getRightValue() == board.getMostLeftValue()))
                    || (currentDirection == DIRECTION.RIGHT && (piece.getLeftValue() == board.getMostRightValue() || piece.getRightValue() == board.getMostRightValue())))
                    && board.getStartingPiece() != null) {
                validMove = true;
            }
        }
        return validMove;
    }
}
