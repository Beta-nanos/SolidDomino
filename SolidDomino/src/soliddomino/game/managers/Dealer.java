package soliddomino.game.managers;

import soliddomino.game.components.Piece;
import soliddomino.game.components.Player;
import soliddomino.game.movement.Turn;
import soliddomino.game.movement.Movement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import soliddomino.game.exceptions.NoPiecesToTakeException;
import static soliddomino.game.dominos.Domino.PIECES_PER_PLAYER;
import soliddomino.game.exceptions.InvalidPiecePairingException;
import soliddomino.game.movement.MovementBuilder;

public class Dealer {
    private List<Player> players = new ArrayList<>();
    private Turn turn;
    private List<Piece> usedPieces = new ArrayList<>();
    private MovementBuilder movementBuilder;
            
    public Dealer(List<Player> players){
        this.players = players;
        this.turn = new Turn();
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
        HashMap<Player, Piece> playerPieces = new HashMap<>();
        fillHighestPair(playerPieces);
        
        if(playerPieces.isEmpty()){
            fillHighestPiece(playerPieces);
        }
        
        return getTopFromHashMap(playerPieces);
    }
    
    public Movement getPlayerMovement(Player player, Board board){
        Movement currentMove = null;
        try {
            currentMove = buildingMovement(player, currentMove, board);
        } catch (InvalidPiecePairingException ex) {
            System.out.println(ex.getMessage());
            pieceRollback(player);
            currentMove = getPlayerMovement(player, board);
        }
        return currentMove;
    }

    private Movement buildingMovement(Player player, Movement currentMove, Board board) throws InvalidPiecePairingException {
        do{
            movementBuilder.setPlayer(player);
            currentMove = movementBuilder.generateMovement(board);
            usedPieces.add(currentMove.getPiece());
        }while(!(turn.validateMove(currentMove, board)));
        return currentMove;
    }

    private void pieceRollback(Player player) {
        int lastUsedPieceIndex = usedPieces.size() - 1;
        player.getLastUsedPiece(usedPieces.get(lastUsedPieceIndex));
        usedPieces.remove(lastUsedPieceIndex);
    }
    
    private void fillHighestPair(HashMap<Player, Piece> playerPieces){
        for(Player player : players){
            Piece highestPair = player.getHighestPair();
            if(highestPair != null)
                playerPieces.put(player, highestPair);
            
            playerPieces.remove(player, null);
        }
    }
    
    private void fillHighestPiece(HashMap<Player, Piece> playerPieces){
        for(Player player : players)
            playerPieces.put(player, player.getHighestPiece());
    }
    
    private Player getTopFromHashMap(HashMap<Player, Piece> playersPieces){
        List<Piece> pieceList = new ArrayList<>(playersPieces.values());
        List<Player> playerList = new ArrayList<>(playersPieces.keySet());
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
            System.out.println(ex.getMessage());
        }
    
    }
}
