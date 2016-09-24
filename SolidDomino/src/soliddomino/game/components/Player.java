/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soliddomino.game.components;

import soliddomino.game.exceptions.WrongDirectionException;
import soliddomino.game.exceptions.IncorrectMoveFormatException;
import java.util.ArrayList;
import soliddomino.game.exceptions.NoPiecesToTakeException;
import soliddomino.game.movement.Movement;
import java.util.List;
import java.util.Scanner;
import soliddomino.game.movement.DIRECTION;

/**
 *
 * @author lisaula
 */
public class Player {
    private final String name;
    List<Piece> pieces;
    public Player(String name) {
        this.name =  name;
    }
    
    public String getName() {
        return name;
    }

    public Movement move() throws IncorrectMoveFormatException, WrongDirectionException {
        Movement movement = null;
        printPieces();
        Scanner scan = new Scanner(System.in);
        System.out.println("Which piece would you move in what direction?");
        System.out.println("options:\n-Number of Piece - direction(e.g left, right)\n-Pass");
        String answer = scan.next().toLowerCase().trim();
        movement = answerValidation(answer, movement);
        return movement;
    }

    private Movement answerValidation(String answer, Movement movement) throws IncorrectMoveFormatException, NumberFormatException, WrongDirectionException {
        if(answer.equalsIgnoreCase("pass"))
            movement = new Movement(true);
        else{
            String[] array = answer.split("-");
            if(array.length<2)
                throw new IncorrectMoveFormatException(answer);
            int index = Integer.parseInt(array[0]);
            movement = buildMovement(index, array, movement);
        }
        return movement;
    }

    private Movement buildMovement(int index, String[] array, Movement movement) throws WrongDirectionException {
        if(index > 0 && index <= pieces.size()){
            DIRECTION chosenDirection = null;
            chosenDirection = buildDirection(array, chosenDirection);
            movement = new Movement(pieces.get(index),chosenDirection);
            pieces.remove(index);
        }
        return movement;
    }

    private DIRECTION buildDirection(String[] array, DIRECTION chosenDirection) throws WrongDirectionException {
        switch (array[1]){
            case "right":
            case "r":
                chosenDirection = DIRECTION.RIGHT;
                break;
            case "left":
            case "l":
                chosenDirection = DIRECTION.LEFT;
                break;
            default:
                throw new WrongDirectionException(array[1]);
        }
        return chosenDirection;
    }

    public void takePieces(int piecesToTake, List<Piece> pieces) throws NoPiecesToTakeException {
        if(pieces.isEmpty())
            throw new NoPiecesToTakeException();
        
        for(int i =0; i < piecesToTake; i++){    
            this.pieces.add(pieces.get(i));
            pieces.remove(i);
        }
    }

    public Piece getHighestPair() {
        List<Piece> pairs = new ArrayList<Piece>();
        getPairs(pairs);
        Piece highestPair = pairs.get(0);
        highestPair = comparePieces(pairs, highestPair);
        return highestPair;
    }

    private Piece comparePieces(List<Piece> pieces, Piece highesPair) {
        for(int i =1; i < pieces.size(); i++){
            Piece tempPiece = pieces.get(i);
            if(highesPair.getSumOfValues() < tempPiece.getSumOfValues())
                highesPair = tempPiece;
        }
        return highesPair;
    }

    private void getPairs(List<Piece> pairs) {
        for(Piece piece : pieces){
            if(piece.isPair())
                pairs.add(piece);
        }
    }

    public Piece getHighestPiece() {
        Piece highestPiece = pieces.get(0);
        comparePieces(pieces, highestPiece);
        return highestPiece;
    }    

    private void printPieces() {
        System.out.println("# - piece");
        for(int i =0; i < pieces.size(); i ++){
            Piece tempPiece = pieces.get(i);
            System.out.printf(" - %d|%d\n", (i+1), tempPiece.getLeftValue(), tempPiece.getRightValue());
        }
    }
}
