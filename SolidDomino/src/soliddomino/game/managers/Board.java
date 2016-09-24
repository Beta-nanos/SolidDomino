package soliddomino.game.managers;

import soliddomino.game.components.Piece;
import soliddomino.game.movement.Movement;
import java.util.List;

public interface Board {
    public List<Piece> loadPieces();
    public void shuffle(List<Piece> pieces);
    public void applyMove(Movement currentMove);
    public void showPieces(List<Piece> pieces);
    public int getMostLeftValue();
    public int getMostRightValue();
}
