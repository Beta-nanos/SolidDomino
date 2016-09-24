package soliddomino.game.main;

import soliddomino.game.managers.Dealer;
import soliddomino.game.managers.Board;
import soliddomino.game.components.Piece;
import soliddomino.game.components.Player;
import soliddomino.game.movement.Turn;
import soliddomino.game.movement.Movement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import soliddomino.game.exceptions.NoPiecesToTakeException;
import soliddomino.game.managers.ConsoleBoard;
import soliddomino.game.movement.ConsoleMovementBuilder;

public class Domino {
    private List<Piece> pieces;
    private Board board;
    private ArrayList<Player> players;
    public static int PIECES_PER_PLAYER = 7;
    private Dealer dealer;
    private Turn turn;
    
    public Domino(){
        board = new ConsoleBoard();
        pieces = board.loadPieces();
        board.shuffle(pieces);
        dealer = new Dealer(players);
        dealer.setMovementBuilder(new ConsoleMovementBuilder());
    }
    
    public void init(){
        createPlayers(2);
        try {
            dealer.distributePiecesToPlayers(pieces);
        } catch (NoPiecesToTakeException ex) {
            Logger.getLogger(Domino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String play(){
        Player currentPlayer = dealer.chooseStartingPlayer();
        do {
            getMovementFromPlayer(currentPlayer);
            currentPlayer = dealer.nextPlayerTakingTurn();
        }while(!(turn.HasWon(currentPlayer)));
        return currentPlayer.getName();
    } 

    private void getMovementFromPlayer(Player currentPlayer) {
        Movement currentMove = dealer.getPlayerMovement(currentPlayer, board);
        if(currentMove.isPassed())
            dealer.addPieceToPlayer(currentPlayer, pieces);
        else
            board.applyMove(currentMove);
    }
    
    private void createPlayers(int numberOfPlayers) {
        for(int i = 1; i <= numberOfPlayers; i++)
            players.add(new Player("Player " + i));
    }
    
}
