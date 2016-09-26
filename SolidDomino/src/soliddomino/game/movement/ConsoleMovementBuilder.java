package soliddomino.game.movement;

import java.util.List;
import java.util.Scanner;
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
        System.out.println((char)27 + "[1m Turn goes for: " + player.getName() + (char)27 + "[0m");
        ((ConsoleBoard)board).showPieces(player.getPieces());
        String message = "Which piece would you move in what direction?\nOptions:\n-Number of Piece-direction(e.g left, right)\n-Pass";
        String answer = questionAndGetAnswer(message);
        try {
            movement = answerValidation(answer);
        } catch (IncorrectMoveFormatException | NumberFormatException | WrongDirectionException | WrongPieceIndexException ex) {
            System.out.println(ex.getMessage());
            movement = generateMovement(board);
        } 
        
        return movement;
    }

    private String questionAndGetAnswer(String message) {
        System.out.println(message);
        Scanner scan = new Scanner(System.in);
        return scan.next().toLowerCase().trim();
    }

    @Override
    public Movement answerValidation(String answer) throws IncorrectMoveFormatException, NumberFormatException, WrongDirectionException, WrongPieceIndexException {
        Movement movement = null;
        if (answer.equalsIgnoreCase("pass") || answer.toLowerCase().charAt(0) == 'p') {
            movement = new Movement(true);
        } else {
            String[] array = answer.split("-");
            if (array.length < 2)
                throw new IncorrectMoveFormatException(answer);
            
            int index = Integer.parseInt(array[0]);
            movement = buildMovement(index - 1, array[1]);
        }
        return movement;
    }

    @Override
    public Movement buildMovement(int index, String direction) throws WrongDirectionException, WrongPieceIndexException {
        Movement movement = null;
        List<Piece> playerPieces = player.getPieces();
        int playerPiecesSize = playerPieces.size();
        if (index >= 0 && index < playerPiecesSize) {
            movement = new Movement(playerPieces.get(index), 
                    buildDirection(direction));
            playerPieces.remove(index);
        }else
            throw new WrongPieceIndexException(index, playerPiecesSize);
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
