package soliddomino.game.components;

public class Piece {
    private int leftValue, rightValue;
    private Piece leftPiece, rightPiece;
    
    public Piece(int lvalue, int rvalue){
        leftValue = lvalue;
        rightValue = rvalue;
        leftPiece = null;
        rightPiece = null;
    }
    
    public int getSumOfValues() {
        return getLeftValue() + getRightValue();
    }
    
    boolean isPair() {
        return getLeftValue() == getRightValue();
    }

    public int getLeftValue() {
        return leftValue;
    }

    public int getRightValue() {
        return rightValue;
    }

    public Piece getLeftPiece() {
        return leftPiece;
    }

    public void setLeftPiece(Piece leftPiece) {
        this.leftPiece = leftPiece;
    }

    public Piece getRightPiece() {
        return rightPiece;
    }
    
    public void invertValues(){
        int temp = this.leftValue;
        this.leftValue = this.rightValue;
        this.rightValue = temp;
    }
    
    public void setRightPiece(Piece rightPiece) {
        this.rightPiece = rightPiece;
    }

    public int getFreeValue() {
        if (this.getLeftPiece() == null) {
            return this.getLeftValue();
        } else {
            return this.getRightValue();
        }
    }
    
}
