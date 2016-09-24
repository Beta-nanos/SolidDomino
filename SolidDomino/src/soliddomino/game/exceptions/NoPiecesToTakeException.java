/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soliddomino.game.exceptions;

/**
 *
 * @author lisaula
 */
public class NoPiecesToTakeException extends Exception {

    public NoPiecesToTakeException() {
        super("Pieces remainder of the game is zero.");
    }
    
    

}
