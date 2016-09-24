package soliddomino.game.managers;

import java.util.List;
import soliddomino.game.components.Piece;
import soliddomino.game.movement.Movement;

public class ConsoleBoard implements Board {

    @Override
    public List<Piece> loadPieces() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shuffle(List<Piece> pieces) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void applyMove(Movement currentMove) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMostRightValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
