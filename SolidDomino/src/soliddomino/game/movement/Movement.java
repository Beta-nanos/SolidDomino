/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soliddomino.game.movement;

import soliddomino.game.components.Piece;
import soliddomino.game.movement.DIRECTION;

/**
 *
 * @author lisaula
 */
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
    
    public boolean isPassed(){
        return pass;
    }
}
