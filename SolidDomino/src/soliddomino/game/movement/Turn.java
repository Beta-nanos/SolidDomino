package soliddomino.game.movement;

import soliddomino.game.exceptions.InvalidPiecePairingException;
import soliddomino.game.components.Piece;
import soliddomino.game.managers.Board;
import soliddomino.game.components.Player;

public class Turn {

    public boolean hasWon(Player currentPlayer) {
        return currentPlayer.getPieces().isEmpty();
    }

    public boolean validateMove(Movement currentMove, Board board) throws InvalidPiecePairingException {
        boolean validMove = false;
        if (currentMove != null) {
            Piece piece = currentMove.getPiece();
            DIRECTION currentDirection = currentMove.getDirection();
            validMove = piecePairingValidation(board, currentMove, currentDirection, piece);
        }
        
        return validMove;
    }

    private boolean piecePairingValidation(Board board, Movement currentMove, DIRECTION currentDirection, Piece piece) throws InvalidPiecePairingException {
        boolean validMove = false;
        
        if(!currentMove.isPass())
            validMove = isValidPair(currentDirection, piece.getLeftValue(), 
                    board.getLeftmostValue(), piece.getRightValue(), board.getRightmostValue(), piece);
        else
            validMove = true;
        
        return validMove;
    }

    private boolean isValidPair(DIRECTION currentDirection, int pieceLeftValue, int boardLeftValue, int pieceRightValue, int boardRightValue, Piece piece) throws InvalidPiecePairingException {
        boolean validMove = false;
        
        if ((currentDirection == DIRECTION.LEFT && (pieceLeftValue == boardLeftValue
                || pieceRightValue == boardLeftValue))
                || (currentDirection == DIRECTION.RIGHT && (pieceLeftValue == boardRightValue
                || pieceRightValue == boardRightValue)))
            validMove = true;
        else
            throw new InvalidPiecePairingException(piece);
        
        return validMove;
    }
}
