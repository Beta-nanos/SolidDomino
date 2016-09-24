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
public class IncorrectMoveFormatException extends Exception {

    public IncorrectMoveFormatException(String format) {
        super(format + " is not the correct format. Expected \'#number-direction(e.g left, right).\'\nExample: 4-right");
    }

}
