package soliddomino.game.movement;

import soliddomino.game.components.Player;
import soliddomino.game.exceptions.IncorrectMoveFormatException;
import soliddomino.game.exceptions.WrongDirectionException;

public interface MovementBuilder {
    public Movement generateMovement(Player player);
    public Movement answerValidation(String answer, Movement movement) throws IncorrectMoveFormatException, NumberFormatException, WrongDirectionException;
    public Movement buildMovement(int index, String direction, Movement movement, Player player) throws WrongDirectionException;
    public DIRECTION buildDirection(String direction) throws WrongDirectionException;
}
