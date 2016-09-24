package soliddomino.game.managers;

import soliddomino.game.components.Piece;
import soliddomino.game.components.Player;
import soliddomino.game.movement.Turn;
import soliddomino.game.movement.Movement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import soliddomino.game.exceptions.NoPiecesToTakeException;
import static soliddomino.game.dominos.Domino.PIECES_PER_PLAYER;
import soliddomino.game.movement.MovementBuilder;

public class Dealer {
    private ArrayList<Player> players = new ArrayList<Player>();
    private Turn turn;
    private MovementBuilder movementBuilder;
            
    public Dealer(ArrayList<Player> players){
        this.players =players;
    }
    
    public void setMovementBuilder(MovementBuilder movementBuilder){
        this.movementBuilder = movementBuilder;
    }
    
    public Player nextPlayerTakingTurn(Player currentPlayer) {
        int indexCurrentPlayer = players.indexOf(currentPlayer);
        if(indexCurrentPlayer == players.size()-1)
            return players.get(0);
        else
            return players.get(indexCurrentPlayer + 1);
    }
    
    public void distributePiecesToPlayers(List<Piece> pieces) throws NoPiecesToTakeException {
        for(Player player : players)
            player.takePieces(PIECES_PER_PLAYER, pieces);        
    }

    public Player chooseStartingPlayer() {
        HashMap<Player, Piece> playerPieces = new HashMap<Player, Piece>();
        fillHighestPair(playerPieces);
        
        if(playerPieces.isEmpty()){
            fillHighestPiece(playerPieces);
        }
        
        return getTopFromHashMap(playerPieces);
    }
    
    public Movement getPlayerMovement(Player player, Board board){
        Movement currentMove = null;
        do{
            movementBuilder.setPlayer(player);
            currentMove = movementBuilder.generateMovement(board);
        }while(!(turn.validateMove(currentMove, board)));
        return currentMove;
    }
    
    private void fillHighestPair(HashMap<Player, Piece> playerPieces){
        for(Player player : players){
            playerPieces.put(player, player.getHighestPair());
            playerPieces.remove(player, null);
        }
    }
    
    private void fillHighestPiece(HashMap<Player, Piece> playerPieces){
        for(Player player : players)
            playerPieces.put(player, player.getHighestPiece());
    }
    
    private Player getTopFromHashMap(HashMap<Player, Piece> playersPieces){
        List<Piece> pieceList = new ArrayList<Piece>(playersPieces.values());
        List<Player> playerList = new ArrayList<Player>(playersPieces.keySet());
        int index = 0;
        for(int i = 0; i < playerList.size() ; i++){
            if(pieceList.get(index).getSumOfValues() < pieceList.get(i).getSumOfValues()){
                index = i;
            }
        }
        return playerList.get(index);
    }

    public void addPieceToPlayer(Player currentPlayer, List<Piece> pieces) {
        try {   
            currentPlayer.takePieces(1, pieces);
        } catch (NoPiecesToTakeException ex) {
            Logger.getLogger(Dealer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    
    }
}
