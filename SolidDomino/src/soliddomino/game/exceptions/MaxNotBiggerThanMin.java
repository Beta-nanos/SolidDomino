package soliddomino.game.exceptions;

public class MaxNotBiggerThanMin extends Exception {
    public MaxNotBiggerThanMin() {
        super("Max must be greater than min");
    }
}
