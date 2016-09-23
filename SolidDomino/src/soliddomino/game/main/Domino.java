package soliddomino.game.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Domino {
    private List<Piece> pieces;
    private Board board;
    private ArrayList<Player> players;
    public static int PIECES_PER_PLAYER = 7;
    
    public Domino(){
        pieces = board.loadPieces();
        board.shuffle(pieces);
    }
    
    public void init(){
        createPlayers(2);
        distributePiecesToPlayers();
    }
    
    public String play(){
        
        Player startingPlayer = chooseStartingPlayer();
        return "";
    } 

    private void createPlayers(int numberOfPlayers) {
        for(int i = 1; i <= numberOfPlayers; i++)
            players.add(new Player("Player " + i));
    }

    private void distributePiecesToPlayers() {
        for(Player player : players)
            player.takePieces(PIECES_PER_PLAYER, pieces);        
    }

    private Player chooseStartingPlayer() {
        HashMap<Player, Piece> playerPieces = new HashMap<Player, Piece>();
        fillHighestPair(playerPieces);
        
        if(playerPieces.isEmpty()){
            fillHighestPiece(playerPieces);
        }
        
        return getTopFromHashMap(playerPieces);
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
}
