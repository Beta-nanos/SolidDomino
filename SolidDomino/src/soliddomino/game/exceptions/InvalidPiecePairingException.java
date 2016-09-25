package soliddomino.game.exceptions;

import soliddomino.game.components.Piece;

public class InvalidPiecePairingException extends Exception {

    public InvalidPiecePairingException(Piece piece) {
        super("The piece " + piece.getLeftValue() + "|" + piece.getRightValue() + " does not pair with the board's endings.");
    }
}
