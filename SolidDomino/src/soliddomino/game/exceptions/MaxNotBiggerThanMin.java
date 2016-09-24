/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soliddomino.game.exceptions;

/**
 *
 * @author VirtualChus
 */
public class MaxNotBiggerThanMin extends Exception {
    public MaxNotBiggerThanMin() {
        super("Max must be greater than min");
    }
}
