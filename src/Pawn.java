import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.EventObject;

public class Pawn extends Circle{

    int colPosition;
    int rowPosition;

    public Pawn(int colPosition, int rowPosition) {
        this.colPosition = colPosition;
        this.rowPosition = rowPosition;

    }


    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int moveDown(){
     return getRowPosition() + 1;
    }

    public int moveUp(){
        return getRowPosition() - 1;
    }

    public int moveLeft(){
        return getColPosition() - 1;
    }

    public int moveRight(){
        return getColPosition() + 1;
    }


}
