package soliddomino.game.movement;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import soliddomino.game.components.Player;
import soliddomino.game.exceptions.IncorrectMoveFormatException;
import soliddomino.game.exceptions.WrongDirectionException;

public class ConsoleMovementBuilder implements MovementBuilder{
    
    @Override
    public Movement generateMovement(Player player) {
        Movement movement = null;
        player.printPieces();
        System.out.println("Which piece would you move in what direction?");
        System.out.println("options:\n-Number of Piece - direction(e.g left, right)\n-Pass");
        Scanner scan = new Scanner(System.in);
        String answer = scan.next().toLowerCase().trim();
        try {
            movement = answerValidation(answer, movement);
        } catch (IncorrectMoveFormatException | NumberFormatException | WrongDirectionException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return movement;
    }

    @Override
    public Movement answerValidation(String answer, Movement movement) throws IncorrectMoveFormatException, NumberFormatException, WrongDirectionException {
        if (answer.equalsIgnoreCase("pass")) {
            movement = new Movement(true);
        } else {
            String[] array = answer.split("-");
            if (array.length < 2) {
                throw new IncorrectMoveFormatException(answer);
            }
            int index = Integer.parseInt(array[0]);
            movement = buildMovement(index, answer, movement);
        }
        return movement;
    }

    @Override
    public Movement buildMovement(int index, String direction, Movement movement, Player player) throws WrongDirectionException {
        if (index > 0 && index <= player.pieces.size()) {
            DIRECTION chosenDirection = null;
            chosenDirection = buildDirection(direction);
            movement = new Movement(player.pieces.get(index), chosenDirection);
            player.pieces.remove(index);
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
