package soliddomino.game.exceptions;

public class IncorrectMoveFormatException extends Exception {

    public IncorrectMoveFormatException(String format) {
        super(format + " is not the correct format. Expected \'#number-direction(e.g left, right).\'\nExample: 4-right");
    }

}
