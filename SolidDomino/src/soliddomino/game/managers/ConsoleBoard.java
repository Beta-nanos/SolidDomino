package soliddomino.game.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import soliddomino.game.components.Piece;
import soliddomino.game.components.Player;
import soliddomino.game.movement.Movement;
import soliddomino.game.exceptions.MaxNotBiggerThanMin;

public class ConsoleBoard implements Board {
    private PieceChain pieceChain;

    public ConsoleBoard() {
        pieceChain = new PieceChain();
    }    
    
    public PieceChain getPieceChain(){
        return pieceChain;
    }
    
    @Override
    public List<Piece> loadPieces(int maxValue) {
        System.out.println("Welcome to Domino Remix 20-16!");
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i <= maxValue; i++)
            loadSubsetPieces(i, pieces);
        return pieces;
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
        if (pieceChain.getStartingPiece() == null)
            System.out.println("No starting piece, yet");
        else 
            System.out.println("Left tail: " + pieceChain.getLeftmostValue() 
                    + ", right tail: " + pieceChain.getRightmostValue());
    }

    private void loadSubsetPieces(int currentIndex, List<Piece> pieces) {
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
        
        pieceChain.setStartingPiece(gameStartingPiece);
        firstPlayer.getPieces().remove(gameStartingPiece);
    }

    @Override
    public void applyMove(Movement currentMove) {
        if (pieceChain.getStartingPiece() == null)
            pieceChain.setStartingPiece(currentMove.getPiece());
        else {
            Piece endingPiece = pieceChain.getEndPieceByDirection(currentMove.getDirection(), this);
            pieceChain.appendToNewAndOldTailPieces(currentMove, endingPiece);
        }
    }
}
