package soliddomino.game.main;

import soliddomino.game.managers.Dealer;
import soliddomino.game.managers.Board;
import soliddomino.game.components.Piece;
import soliddomino.game.components.Player;
import soliddomino.game.movement.Turn;
import soliddomino.game.movement.Movement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Domino {
    private List<Piece> pieces;
    private Board board;
    private ArrayList<Player> players;
    public static int PIECES_PER_PLAYER = 7;
    private Dealer dealer;
    private Turn turn;
    
    public Domino(){
        pieces = board.loadPieces();
        board.shuffle(pieces);
        dealer = new Dealer(players);
    }
    
    public void init(){
        createPlayers(2);
        dealer.distributePiecesToPlayers(pieces);
    }
    
    public String play(){
        Player currentPlayer = dealer.chooseStartingPlayer();
        do {
            Movement currentMove = dealer.getPlayerMovement(currentPlayer, board);
            if(currentMove.isPassed())
                dealer.agregatePieceToPlayer(currentPlayer, pieces);
            else
                board.applyMove(currentMove);
            
            currentPlayer = dealer.nextPlayerTakingTurn();
        }while(!(turn.HasWon(currentPlayer)));
        return currentPlayer.getName();
    } 
    
    private void createPlayers(int numberOfPlayers) {
        for(int i = 1; i <= numberOfPlayers; i++)
            players.add(new Player("Player " + i));
    }
    
}
