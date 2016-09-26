package soliddomino.game.movement;

class WrongPieceIndexException extends Exception {

    public WrongPieceIndexException(int index, int playerPiecesSize) {
        super("The index " + index + " is invalid. Value should be between 1 and " + playerPiecesSize);
    }
    
}
