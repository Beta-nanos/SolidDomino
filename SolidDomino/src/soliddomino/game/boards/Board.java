package soliddomino.game.boards;

import soliddomino.game.components.Piece;
import soliddomino.game.movement.Movement;
import java.util.List;
import soliddomino.game.components.Player;
import soliddomino.game.managers.PieceChain;

public interface Board {
    public List<Piece> loadPieces(int maxValue);
    public void shuffle(List<Piece> pieces);
    public void applyMove(Movement currentMove);
    public void showPieces(List<Piece> pieces);
    public PieceChain getPieceChain();
    public void showCurrentTails();
    public void applyFirstMove(Player firstPlayer);
    public void showPieceChain(Piece piece);
}
