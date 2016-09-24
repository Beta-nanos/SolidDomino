/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soliddomino.game.components;

/**
 *
 * @author lisaula
 */
public class Piece {
    private int leftValue, rightValue;
    private Piece leftPiece, rightPiece;
    
    public Piece(int lvalue, int rvalue){
        leftValue = lvalue;
        rightValue = rvalue;
    }
    
    public int getSumOfValues() {
        return getLeftValue() + getRightValue();
    }
    
    boolean isPair() {
        return getLeftValue() == getRightValue();
    }

    /**
     * @return the leftValue
     */
    public int getLeftValue() {
        return leftValue;
    }

    /**
     * @return the rightValue
     */
    public int getRightValue() {
        return rightValue;
    }

    /**
     * @return the leftPiece
     */
    public Piece getLeftPiece() {
        return leftPiece;
    }

    /**
     * @param leftPiece the leftPiece to set
     */
    public void setLeftPiece(Piece leftPiece) {
        this.leftPiece = leftPiece;
    }

    /**
     * @return the rightPiece
     */
    public Piece getRightPiece() {
        return rightPiece;
    }

    /**
     * @param rightPiece the rightPiece to set
     */
    public void setRightPiece(Piece rightPiece) {
        this.rightPiece = rightPiece;
    }
    
}
