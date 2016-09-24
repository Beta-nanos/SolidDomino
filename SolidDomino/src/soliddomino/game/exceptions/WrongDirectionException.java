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
public class WrongDirectionException extends Exception {

    public WrongDirectionException(String direction) {
        super("Received: " + direction + ". Expected: right, left");
    }
    
}
