package soliddomino.game.managers;

import soliddomino.game.boards.ConsoleBoard;
import soliddomino.game.components.Piece;
import soliddomino.game.movement.DIRECTION;
import soliddomino.game.movement.Movement;

public class PieceChain {
    private Piece startingPiece;
    
    public PieceChain(){
        startingPiece = null;
    }
    
    public int getLeftmostValue() {
        Piece currentStartingPiece = getStartingPiece();
        if (currentStartingPiece.getLeftPiece() == null)
            return currentStartingPiece.getFreeValue();
        
        Piece currentPiece = getLeftmostPiece(currentStartingPiece);
        return currentPiece.getFreeValue();
    }

    public int getRightmostValue() {
        Piece currentStartingPiece = getStartingPiece();
        if (currentStartingPiece.getRightPiece() == null)
            return currentStartingPiece.getFreeValue();
        
        Piece currentPiece = getRightmostPiece(currentStartingPiece);
        return currentPiece.getFreeValue();
    }
    
    private Piece getLeftmostPiece(Piece currentPiece) {
        if (currentPiece.getLeftPiece() == null)
            return currentPiece;
        
        return getLeftmostPiece(currentPiece.getLeftPiece());
    }

    public void appendToNewAndOldTailPieces(Movement currrentMovement, Piece oldTailPiece) {
        Piece newTailPiece = currrentMovement.getPiece();
        if (currrentMovement.getDirection() == DIRECTION.LEFT) {
            if (oldTailPiece.getLeftValue() == newTailPiece.getLeftValue()) {
                newTailPiece.invertValues();
            }
            oldTailPiece.setLeftPiece(newTailPiece);
            newTailPiece.setRightPiece(oldTailPiece);
        } else {
            if (oldTailPiece.getRightValue() == newTailPiece.getRightValue()) {
                newTailPiece.invertValues();
            }
            oldTailPiece.setRightPiece(newTailPiece);
            newTailPiece.setLeftPiece(oldTailPiece);
        }
    }  
    
    private  Piece getRightmostPiece(Piece currentPiece) {
        if (currentPiece.getRightPiece() == null)
            return currentPiece;
        
        return getRightmostPiece(currentPiece.getRightPiece());
    }

    public Piece getEndPieceByDirection(DIRECTION direction, ConsoleBoard consoleBoard) {
        if (direction == DIRECTION.LEFT) {
            return getLeftmostPiece(getStartingPiece());
        } else {
            return getRightmostPiece(getStartingPiece());
        }
    }

    public Piece getStartingPiece() {
        return startingPiece;
    }

    public void setStartingPiece(Piece piece) {
        if (startingPiece == null)
            startingPiece = piece;
    }    
}
