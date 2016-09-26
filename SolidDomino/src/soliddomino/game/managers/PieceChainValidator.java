package soliddomino.game.managers;

import java.util.List;
import soliddomino.game.components.Piece;
import soliddomino.game.dominos.Domino;

public class PieceChainValidator {
    private List<Piece> usedPieces;
    private int leftmostValue;
    private int rightmostValue;
    private static final int VALUES_TO_COUNT = Domino.MAX_PIECE_VALUE + 1;
    
    boolean checkDrawedGame() {
        boolean isDrawed = false;
        
        if(valueIsUsedUp(leftmostValue) && valueIsUsedUp(rightmostValue))
            isDrawed = true;
        
        return isDrawed;
    }

    public void setPiecesStatuses(List<Piece> usedPieces, int leftmostValue, int rightmostValue) {
        this.usedPieces = usedPieces;
        this.leftmostValue = leftmostValue;
        this.rightmostValue = rightmostValue;
    }    

    private boolean valueIsUsedUp(int value) {
        int valuesFound = 0;
        System.out.println(usedPieces.size());
        System.out.println("Lmost: " + leftmostValue);
        System.out.println("Rmost: " + rightmostValue);
        for(Piece piece : usedPieces){
            System.out.println("For left: " + piece.getLeftValue());
            System.out.println("For right: " + piece.getRightValue());
            if(piece.getLeftValue() == value || piece.getRightValue() == value)
                valuesFound++;
        }
        
        return valuesFound == VALUES_TO_COUNT;
    }
}
