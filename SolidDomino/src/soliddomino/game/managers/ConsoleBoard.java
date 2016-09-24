package soliddomino.game.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import soliddomino.game.components.Piece;
import soliddomino.game.movement.DIRECTION;
import soliddomino.game.movement.Movement;
import soliddomino.game.exceptions.MaxNotBiggerThanMin;

public class ConsoleBoard implements Board {
    private Piece startingPiece = null;

    @Override
    public List<Piece> loadPieces(int maxValue) {
        List<Piece> pieces = new ArrayList<Piece>();
        for(int i = 0; i <= maxValue; i++){
            loadSubsetPieces(i, pieces);
        }
        return pieces;
    }
    
    public void loadSubsetPieces(int currentIndex, List<Piece> pieces) {
        for (int u = 0; u <= currentIndex; u++) {
            pieces.add(new Piece(currentIndex, u));
        }
    }
    
    @Override
    public void shuffle(List<Piece> pieces) {
        for(int i = 0; i < pieces.size(); i++){
            switchWithRandomPosition(i,pieces);
        }
    }
    
    private void switchWithRandomPosition(int indexToChange, List<Piece> pieces) {
        int indexToChangeWith =indexToChange;
        try{
            indexToChangeWith = getRandomNumberInRange(0, pieces.size()-1);
        }catch(MaxNotBiggerThanMin ex){
            Logger.getLogger(Dealer.class.getName()).log(Level.WARNING, null, ex);
            System.out.println(ex.getMessage());
        }
        
        Piece currentPiece = pieces.get(indexToChange);
        Piece changeWithThisPiece = pieces.get(indexToChangeWith);
        pieces.set(indexToChangeWith, currentPiece);
        pieces.set(indexToChange, changeWithThisPiece);
    }
    
    private static int getRandomNumberInRange(int min, int max) throws MaxNotBiggerThanMin {

	if (min >= max) {
		throw new MaxNotBiggerThanMin();
	}

	Random r = new Random();
	return r.nextInt((max - min) + 1) + min;
}

    @Override
    public void applyMove(Movement currentMove) {
        if(getStartingPiece() == null)
            setStartingPiece(currentMove.getPiece());
        else{
            Piece endPieceByDirection = getEndPieceByDirection(currentMove.getDirection());
            appendToOldTailPiece(currentMove, endPieceByDirection);
        }
    }
    
    private void appendToOldTailPiece(Movement currentMove, Piece pieceToAddTo){
        Piece newTailPiece = currentMove.getPiece();
        appendToNewTailPiece(newTailPiece, pieceToAddTo);
        if(pieceToAddTo.getLeftPiece() == null){
            pieceToAddTo.setLeftPiece(newTailPiece);
        }else{
            pieceToAddTo.setRightPiece(newTailPiece);
        }               
    }
    
    private void appendToNewTailPiece(Piece newTailPiece, Piece oldTailPiece){
        if(newTailPiece.getLeftValue() == oldTailPiece.getLeftValue() || newTailPiece.getLeftValue() == oldTailPiece.getRightValue())
            newTailPiece.setLeftPiece(oldTailPiece);
        else
            newTailPiece.setRightPiece(oldTailPiece);
    }
    
    @Override
    public void setStartingPiece(Piece piece) {
        startingPiece = piece;
    }
    
    @Override
    public Piece getStartingPiece() {
        return startingPiece;
    }
    
    private Piece getEndPieceByDirection(DIRECTION direction) {
        if(direction == DIRECTION.LEFT)
            return getMostLeftPiece(getStartingPiece());
        else
            return getMostRightPiece(getStartingPiece());
    }
    
    private Piece getMostLeftPiece(Piece currentPiece) {
        Piece currentStartingPiece = getStartingPiece();
        if(currentPiece == currentStartingPiece && currentPiece.getLeftPiece() != null)
            return getMostLeftPiece(currentPiece.getLeftPiece());
        if(currentPiece == currentStartingPiece && currentPiece.getLeftPiece() == null)
            return currentPiece;
        return getLastPiece(currentPiece, currentStartingPiece);
    }

    private Piece getMostRightPiece(Piece currentPiece) {
        Piece currentStartingPiece = getStartingPiece();
        if(currentPiece == currentStartingPiece && currentPiece.getRightPiece() != null)
            return getMostRightPiece(currentPiece.getRightPiece());
        if(currentPiece == currentStartingPiece && currentPiece.getRightPiece() == null)
            return currentPiece;
        return getLastPiece(currentPiece, currentStartingPiece);
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
            System.out.println("Piece " + (i+1) + ": " + tempPiece.getLeftValue() + "|" + tempPiece.getRightValue());
        }
    }

    @Override
    public int getMostLeftValue() {
        Piece currentStartingPiece = getStartingPiece();
        if(currentStartingPiece.getLeftPiece() == null)
            return currentStartingPiece.getLeftValue();
        Piece currentPiece = getMostLeftPiece(currentStartingPiece);
        return getFreeValue(currentPiece);
    }

    @Override
    public int getMostRightValue() {
        Piece currentStartingPiece = getStartingPiece();
        if(currentStartingPiece.getRightPiece() == null)
            return currentStartingPiece.getRightValue();
        Piece currentPiece = getMostRightPiece(currentStartingPiece);
        return getFreeValue(currentPiece);
    }
    
    public int getFreeValue(Piece piece){
        if(piece.getLeftPiece() == null)
            return piece.getLeftValue();
        else
            return piece.getRightValue();
    }
}
