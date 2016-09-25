package soliddomino.game.movement;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import soliddomino.game.components.Piece;
import soliddomino.game.components.Player;
import soliddomino.game.exceptions.IncorrectMoveFormatException;
import soliddomino.game.exceptions.WrongDirectionException;
import soliddomino.game.managers.Board;
import soliddomino.game.managers.ConsoleBoard;

public class ConsoleMovementBuilder implements MovementBuilder{
    private Player player;
    
    @Override
    public void setPlayer(Player player){
        this.player = player;
    }
    
    @Override
    public Movement generateMovement(Board board) {
        Movement movement = null;
        System.out.println("-----------------------------------------------");
        ((ConsoleBoard)board).showCurrentTails();
        System.out.println("Turn goes for: " + player.getName());
        ((ConsoleBoard)board).showPieces(player.getPieces());
        String message = "Which piece would you move in what direction?\nOptions:\n-Number of Piece-direction(e.g left, right)\n-Pass";
        String answer = questionAndGetAnswer(message);
        try {
            movement = answerValidation(answer);
        } catch (IncorrectMoveFormatException | NumberFormatException | WrongDirectionException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return movement;
    }

    private String questionAndGetAnswer(String message) {
        System.out.println(message);
        Scanner scan = new Scanner(System.in);
        return scan.next().toLowerCase().trim();
    }

    @Override
    public Movement answerValidation(String answer) throws IncorrectMoveFormatException, NumberFormatException, WrongDirectionException {
        Movement movement = null;
        if (answer.equalsIgnoreCase("pass")) {
            movement = new Movement(true);
        } else {
            String[] array = answer.split("-");
            if (array.length < 2) {
                throw new IncorrectMoveFormatException(answer);
            }
            int index = Integer.parseInt(array[0]);
            movement = buildMovement(index - 1, array[1]);
        }
        return movement;
    }

    @Override
    public Movement buildMovement(int index, String direction) throws WrongDirectionException {
        Movement movement = null;
        List<Piece> playerPieces = player.getPieces();
        if (index >= 0 && index < playerPieces.size()) {
            movement = new Movement(playerPieces.get(index), 
                    buildDirection(direction));
            playerPieces.remove(index);
        }
        return movement;
    }

    @Override
    public DIRECTION buildDirection(String direction) throws WrongDirectionException {
        DIRECTION chosenDirection = null;
        switch (direction) {
            case "right":
            case "r":
                chosenDirection = DIRECTION.RIGHT;
                break;
            case "left":
            case "l":
                chosenDirection = DIRECTION.LEFT;
                break;
            default:
                throw new WrongDirectionException(direction);
        }
        return chosenDirection;
    }    
}
