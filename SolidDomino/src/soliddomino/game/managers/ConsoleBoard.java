package soliddomino.game.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import soliddomino.game.components.Piece;
import soliddomino.game.components.Player;
import soliddomino.game.movement.DIRECTION;
import soliddomino.game.movement.Movement;
import soliddomino.game.exceptions.MaxNotBiggerThanMin;

public class ConsoleBoard implements Board {

    private Piece startingPiece = null;
    
    @Override
    public void setStartingPiece(Piece piece) {
        if(startingPiece == null)
            startingPiece = piece;
    }

    @Override
    public Piece getStartingPiece() {
        return startingPiece;
    }
    
    @Override
    public List<Piece> loadPieces(int maxValue) {
        System.out.println("Welcome to Domino Remix 20-16!");
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i <= maxValue; i++)
            loadSubsetPieces(i, pieces);
        return pieces;
    }

    public void loadSubsetPieces(int currentIndex, List<Piece> pieces) {
        for (int u = 0; u <= currentIndex; u++)
            pieces.add(new Piece(currentIndex, u));
    }

    @Override
    public void shuffle(List<Piece> pieces) {
        System.out.println("Shuffling pieces...");
        for (int i = 0; i < pieces.size(); i++)
            switchWithRandomPosition(i, pieces);        
    }

    private void switchWithRandomPosition(int indexToChange, List<Piece> pieces) {
        int indexToChangeWith = indexToChange;
        try {
            indexToChangeWith = getRandomNumberInRange(0, pieces.size() - 1);
        } catch (MaxNotBiggerThanMin ex) {
            System.out.println(ex.getMessage());
        }

        Piece currentPiece = pieces.get(indexToChange);
        Piece changeWithThisPiece = pieces.get(indexToChangeWith);
        pieces.set(indexToChangeWith, currentPiece);
        pieces.set(indexToChange, changeWithThisPiece);
    }

    private static int getRandomNumberInRange(int min, int max) throws MaxNotBiggerThanMin {
        if (min >= max) 
            throw new MaxNotBiggerThanMin();
        
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public void applyFirstMove(Player firstPlayer) {
        System.out.println((char)27 + "[1m Turn goes for: " + firstPlayer.getName() + (char)27 + "[0m");
        Piece gameStartingPiece = firstPlayer.getHighestPair();
        if (firstPlayer.getHighestPair() == null)
            gameStartingPiece = firstPlayer.getHighestPiece();
        
        setStartingPiece(gameStartingPiece);
        firstPlayer.getPieces().remove(gameStartingPiece);
    }

    @Override
    public void applyMove(Movement currentMove) {
        if (getStartingPiece() == null)
            setStartingPiece(currentMove.getPiece());
        else {
            Piece endingPiece = getEndPieceByDirection(currentMove.getDirection());
            appendToNewAndOldTailPieces(currentMove, endingPiece);
        }
    }

    private void appendToNewAndOldTailPieces(Movement currrentMovement, Piece oldTailPiece) {
        Piece newTailPiece = currrentMovement.getPiece();
        if (currrentMovement.getDirection() == DIRECTION.LEFT){
            if(oldTailPiece.getLeftValue() == newTailPiece.getLeftValue())
                newTailPiece.invertValues();
            oldTailPiece.setLeftPiece(newTailPiece);
            newTailPiece.setRightPiece(oldTailPiece);
        }
        else{
            if(oldTailPiece.getRightValue()== newTailPiece.getRightValue())
                newTailPiece.invertValues();
            oldTailPiece.setRightPiece(newTailPiece);
            newTailPiece.setLeftPiece(oldTailPiece);
        }
    }

    private Piece getEndPieceByDirection(DIRECTION direction) {
        if (direction == DIRECTION.LEFT) {
            return getLeftmostPiece(getStartingPiece());
        } else {
            return getRightmostPiece(getStartingPiece());
        }
    }

    private Piece getLeftmostPiece(Piece currentPiece) {
        if(currentPiece.getLeftPiece() == null)
            return currentPiece;
        
        return getLeftmostPiece(currentPiece.getLeftPiece());
    }

    private Piece getRightmostPiece(Piece currentPiece) {
        if(currentPiece.getRightPiece() == null)
            return currentPiece;
        
        return getRightmostPiece(currentPiece.getRightPiece());
    }

    @Override
    public void showPieces(List<Piece> pieces) {
        System.out.println("Piece #: left Value|right Value");
        for (int i = 0; i < pieces.size(); i++) {
            Piece tempPiece = pieces.get(i);
            System.out.println("Piece " + (i + 1) + ": " + tempPiece.getLeftValue() + "|" + tempPiece.getRightValue());
        }
    }

    @Override
    public void showCurrentTails() {
        if (getStartingPiece() == null)
            System.out.println("No starting piece, yet");
        else 
            System.out.println("Left tail: " + getLeftmostValue() + ", right tail: " + getRightmostValue());
    }

    @Override
    public int getLeftmostValue() {
        Piece currentStartingPiece = getStartingPiece();
        if (currentStartingPiece.getLeftPiece() == null)
            return getFreeValue(currentStartingPiece);
        
        Piece currentPiece = getLeftmostPiece(currentStartingPiece);
        return getFreeValue(currentPiece);
    }

    @Override
    public int getRightmostValue() {
        Piece currentStartingPiece = getStartingPiece();
        if (currentStartingPiece.getRightPiece() == null)
            return getFreeValue(currentStartingPiece);
        
        Piece currentPiece = getRightmostPiece(currentStartingPiece);
        return getFreeValue(currentPiece);
    }

    public int getFreeValue(Piece piece) {
        if (piece.getLeftPiece() == null)
            return piece.getLeftValue();
        else
            return piece.getRightValue();
    }
}
