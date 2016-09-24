package soliddomino.game.managers;

import java.util.List;
import java.util.Random;
import soliddomino.game.components.Piece;
import soliddomino.game.movement.DIRECTION;
import soliddomino.game.movement.Movement;

public class ConsoleBoard implements Board {
    public Piece startingPiece = null;

    @Override
    public List<Piece> loadPieces(int maxValue) {
        List<Piece> pieces = null;
        for(int i = 0; i <= maxValue; i++){
            loadSubsetPieces(i, pieces);
        }
        return pieces;
    }
    
    public void loadSubsetPieces(int currentIndex, List<Piece> pieces){
        for(int u = 0; u <= currentIndex; u++){
                pieces.add(new Piece(currentIndex,u));
            }
    }
    
    @Override
    public void shuffle(List<Piece> pieces) {
        for(int i = 0; i < pieces.size(); i++){
            switchWithRandomPosition(i,pieces);
        }
    }
    
    private void switchWithRandomPosition(int indexToChange, List<Piece> pieces) {
        int indexToChangeWith = getRandomNumberInRange(0, pieces.size());
        Piece currentPiece = pieces.get(indexToChange);
        Piece changeWithThisPiece = pieces.get(indexToChangeWith);
        pieces.set(indexToChangeWith, currentPiece);
        pieces.set(indexToChange, changeWithThisPiece);
    }
    
    private static int getRandomNumberInRange(int min, int max) {

	if (min >= max) {
		throw new IllegalArgumentException("Max must be greater than min");
	}

	Random r = new Random();
	return r.nextInt((max - min) + 1) + min;
}

    @Override
    public void applyMove(Movement currentMove) {
        if(getStartingPiece() == null)
            setStartingPiece(currentMove.getPiece());
        else{
            Piece pieceToAddTo = getPieceToAddTo(currentMove.getDirection());
            if(pieceToAddTo.getLeftPiece() == null)
                pieceToAddTo.setLeftPiece(currentMove.getPiece());
            else
                pieceToAddTo.setRightPiece(currentMove.getPiece());
        }
    }
    
    @Override
    public void setStartingPiece(Piece piece) {
        startingPiece = piece;
    }
    
    @Override
    public Piece getStartingPiece() {
        return startingPiece;
    }
    
    private Piece getPieceToAddTo(DIRECTION direction) {
        if(direction == DIRECTION.LEFT)
            return getMostLeftPiece(getStartingPiece());
        else
            return getMostRightPiece(getStartingPiece());
    }
    
    private Piece getMostLeftPiece(Piece currentPiece) {
        if(currentPiece == getStartingPiece() && currentPiece.getLeftPiece() != null)
            return getMostLeftPiece(currentPiece.getLeftPiece());
        if(currentPiece == getStartingPiece() && currentPiece.getLeftPiece() == null)
            return currentPiece;
        return getLastPiece(currentPiece, getStartingPiece());
    }

    private Piece getMostRightPiece(Piece currentPiece) {
        if(currentPiece == getStartingPiece() && currentPiece.getRightPiece() != null)
            return getMostRightPiece(currentPiece.getRightPiece());
        if(currentPiece == getStartingPiece() && currentPiece.getRightPiece() == null)
            return currentPiece;
        return getLastPiece(currentPiece, getStartingPiece());
    }
    
    private Piece getLastPiece(Piece currentPiece, Piece previousPiece){
        if(currentPiece.getLeftPiece() == null || currentPiece.getRightPiece() == null)
            return currentPiece;
        if(currentPiece.getLeftPiece() == previousPiece)
            return getLastPiece(currentPiece.getRightPiece(), currentPiece);
        else
            return getLastPiece(currentPiece.getLeftPiece(), currentPiece);
    }

    @Override
    public void showPieces(List<Piece> pieces) {
        System.out.println("# - piece");
        for (int i = 0; i < pieces.size(); i++) {
            Piece tempPiece = pieces.get(i);
            System.out.printf(" - %d|%d\n", (i + 1), tempPiece.getLeftValue(), tempPiece.getRightValue());
        }
    }

    @Override
    public int getMostLeftValue() {
        if(getStartingPiece().getLeftPiece() == null)
            return getStartingPiece().getLeftValue();
        Piece currentPiece = getMostLeftPiece(getStartingPiece());
        return getFreeValue(currentPiece);
    }

    @Override
    public int getMostRightValue() {
        if(getStartingPiece().getRightPiece() == null)
            return getStartingPiece().getRightValue();
        Piece currentPiece = getMostRightPiece(getStartingPiece());
        return getFreeValue(currentPiece);
    }
    
    public int getFreeValue(Piece piece){
        if(piece.getLeftPiece() == null)
            return piece.getLeftValue();
        else
            return piece.getRightValue();
    }
}
