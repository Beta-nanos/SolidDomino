package soliddomino.game.components;

import soliddomino.game.exceptions.WrongDirectionException;
import soliddomino.game.exceptions.IncorrectMoveFormatException;
import java.util.ArrayList;
import soliddomino.game.exceptions.NoPiecesToTakeException;
import soliddomino.game.movement.Movement;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import soliddomino.game.movement.DIRECTION;

public class Player {
    private final String name;
    List<Piece> pieces;
    
    public Player(String name) {
        this.name =  name;
    }
    
    public String getName() {
        return name;
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
        List<Piece> pairs = new ArrayList<>();
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
}
