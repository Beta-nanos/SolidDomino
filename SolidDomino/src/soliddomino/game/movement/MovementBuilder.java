package soliddomino.game.movement;

import soliddomino.game.exceptions.WrongPieceIndexException;
import soliddomino.game.components.Player;
import soliddomino.game.exceptions.IncorrectMoveFormatException;
import soliddomino.game.exceptions.WrongDirectionException;
import soliddomino.game.boards.Board;

public interface MovementBuilder {
    public void setPlayer(Player player);
    public Movement generateMovement(Board board);
    public Movement answerValidation(String answer) throws IncorrectMoveFormatException, NumberFormatException, WrongDirectionException, WrongPieceIndexException;
    public Movement buildMovement(int index, String direction) throws WrongDirectionException, WrongPieceIndexException;
    public DIRECTION buildDirection(String direction) throws WrongDirectionException;
}
