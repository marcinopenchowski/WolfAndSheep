import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Board {
    GridPane board = new GridPane();
    BoardSquare[][] boardSquares = new BoardSquare[8][8];
    StackPane stackPane;
    Pawn[] sheep = new Pawn[4];
    Pawn wolf = new Pawn(0, 7);

    public Board() {

        for(int row = 0, i = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                if((i % 2) == 0) {
                    BoardSquare boardSquare = new BoardSquare(Color.WHITE);
                    StackPane square = getBoardSquare(boardSquare);
                    board.add(square, col, row);
                    boardSquares[row][col] = boardSquare;


                }else {
                    BoardSquare boardSquare = new BoardSquare(Color.BLACK);
                    StackPane square = getBoardSquare(boardSquare);
                    board.add(square, col, row);
                    boardSquares[row][col] = boardSquare;

                }

                i++;
            }
            i++;
        }
        for(int row = 0; row < 8; row++){
            RowConstraints constrains = new RowConstraints();
            constrains.setPercentHeight(20);
            board.getRowConstraints().add(constrains);
        }
        for(int col = 0; col < 8; col++){
            ColumnConstraints constrains = new ColumnConstraints();
            constrains.setPercentWidth(20);
            board.getColumnConstraints().add(constrains);
        }
        createPawns();
    }

    public GridPane getBoard() {
        return board;
    }

    public BoardSquare getBoardSquares(int row, int col){

        return boardSquares[row][col];
    }
    public BoardSquare[][] getBoardSquares(){
        return boardSquares;
    }

    public StackPane getBoardSquare(BoardSquare boardSquare){
        StackPane stackPane = new StackPane();




        stackPane.getChildren().addAll(boardSquare);

        this.stackPane = stackPane;
        return stackPane;
    }

    public void createPawns(){
        NumberBinding radiusProperty = Bindings.when(stackPane.widthProperty().greaterThan(stackPane.heightProperty())).then(stackPane.heightProperty().
                subtract(12).divide(2)).otherwise(stackPane.widthProperty().subtract(12).divide(2));

        for(int i = 0; i < sheep.length; i++){
            this.sheep[i] = new Pawn(i*2+1, 0);
            boardSquares[sheep[i].getRowPosition()][sheep[i].getColPosition()].setBusy(true);
            sheep[i].setFill(Color.YELLOW);
            sheep[i].radiusProperty().bind(radiusProperty);
        }

        wolf.radiusProperty().bind(radiusProperty);
        wolf.setFill(Color.RED);
        boardSquares[wolf.getRowPosition()][wolf.getColPosition()].setBusy(true);
    }

    public Pawn[] getSheep() {
        return sheep;
    }
    public Pawn getSheep(int i) {
        return sheep[i];
    }

    public void setSheep(Pawn[] sheep) {
        this.sheep = sheep;
    }

    public Pawn getWolf() {
        return wolf;
    }

    public void setWolf(Pawn wolf) {
        this.wolf = wolf;
    }
}
