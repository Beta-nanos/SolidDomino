package soliddomino.game.movement;

import soliddomino.game.components.Player;
import soliddomino.game.exceptions.IncorrectMoveFormatException;
import soliddomino.game.exceptions.WrongDirectionException;
import soliddomino.game.managers.Board;

public interface MovementBuilder {
    public void setPlayer(Player player);
    public Movement generateMovement(Board board);
    public Movement answerValidation(String answer) throws IncorrectMoveFormatException, NumberFormatException, WrongDirectionException;
    public Movement buildMovement(int index, String direction) throws WrongDirectionException;
    public DIRECTION buildDirection(String direction) throws WrongDirectionException;
}
