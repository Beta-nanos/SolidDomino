package soliddomino.game.dominos;

import soliddomino.game.managers.Dealer;
import soliddomino.game.boards.Board;
import soliddomino.game.components.Piece;
import soliddomino.game.components.Player;
import soliddomino.game.movement.Turn;
import soliddomino.game.movement.Movement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import soliddomino.game.boards.ConsoleBoard;
import soliddomino.game.exceptions.NoPiecesToTakeException;
import soliddomino.game.managers.PieceChain;
import soliddomino.game.movement.MovementBuilder;

public abstract class Domino {
    private List<Piece> pieces;
    private Board board;
    private List<Player> players = new ArrayList<>(); 
    public static int PIECES_PER_PLAYER = 7;
    public static int MAX_PIECE_VALUE = 6;
    private Dealer dealer;
    private Turn turn;
    
    public Domino(Board board, MovementBuilder movementBuilder){
        this.board = board;
        pieces = board.loadPieces(MAX_PIECE_VALUE);
        board.shuffle(pieces);
        dealer = new Dealer(players);
        dealer.setMovementBuilder(movementBuilder);
        this.turn  = new Turn();
    }
    
    public void init(){
        createPlayers(2);
        try {
            dealer.distributePiecesToPlayers(pieces);
        } catch (NoPiecesToTakeException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public String play(){
        Player currentPlayer = dealer.chooseStartingPlayer();
        board.applyFirstMove(currentPlayer);
        dealer.setStartingPieceUsed((board).getPieceChain().getStartingPiece());
        boolean isDrawedGame = false;
        do {
            currentPlayer = dealer.nextPlayerTakingTurn(currentPlayer);
            getMovementFromPlayer(currentPlayer);
            isDrawedGame = dealer.gameIsDrawed();
        }while(!isDrawedGame && !(turn.hasWon(currentPlayer)));
        
        return isDrawedGame ? getDrawWinner() : currentPlayer.getName() ;
    } 

    private void getMovementFromPlayer(Player currentPlayer) {
        Movement currentMove = dealer.getPlayerMovement(currentPlayer, board);
        if(currentMove.isPass())
            dealer.addPieceToPlayer(currentPlayer, pieces);
        else
            board.applyMove(currentMove);
        PieceChain pieceChain = board.getPieceChain();
        dealer.setPiecesStatues(
                pieceChain.getLeftmostValue(),
                pieceChain.getRightmostValue());
    }
    
    private void createPlayers(int numberOfPlayers) {
        for(int i = 1; i <= numberOfPlayers; i++)
            players.add(new Player("Player " + i));
    }

    private String getDrawWinner() {
        Collections.sort(players); 
        Player drawWinner = players.get(0);
        
        return drawWinner.getName() + " with " + drawWinner.getSumOfPiecesValues();
    }
    
}
