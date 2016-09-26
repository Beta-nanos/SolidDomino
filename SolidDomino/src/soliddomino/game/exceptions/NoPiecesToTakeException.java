package soliddomino.game.exceptions;

public class NoPiecesToTakeException extends Exception {

    public NoPiecesToTakeException() {
        super("Pieces remainder of the game is zero.");
    }
    
    

}
