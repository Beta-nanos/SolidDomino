package soliddomino.game.exceptions;

public class WrongDirectionException extends Exception {

    public WrongDirectionException(String direction) {
        super("Received: " + direction + ". Expected: right, left");
    }
    
}
