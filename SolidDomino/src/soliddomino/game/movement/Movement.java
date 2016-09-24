package soliddomino.game.movement;

import soliddomino.game.components.Piece;

public class Movement {
    private Piece piece;
    private DIRECTION direction;
    private boolean pass;
    
    public Movement(Piece piece, DIRECTION direction){
        this.piece = piece;
        this.direction = direction;
        this.pass = false;
    }

    public Movement(boolean pass) {
        this.pass = pass;
    }
    
    public boolean isPass(){
        return pass;
    }

    public Piece getPiece() {
        return piece;
    }

    public DIRECTION getDirection() {
        return direction;
    }
}
