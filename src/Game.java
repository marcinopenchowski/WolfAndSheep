import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;

public class Game {
    BoardSquare[][] boardSquares = new BoardSquare[8][8];
    int rowLimit = 8;
    int colLimit = 8;
    int move;
    int sheepWon;
    int wolfWon;


    public Game(Board board, Stage primaryStage) {
        this.boardSquares = board.boardSquares;

        setPawnsOnDefaultPosition(board);
        play(board, primaryStage);


    }

    public void play(Board board, Stage primaryStage){
        moveWolf(board, primaryStage);
        moveSheep(board);


    }


    public void setPawnsOnDefaultPosition(Board board) {

        for (int i = 0, j = 0; i < board.getBoardSquares().length; i++) {
            if (i % 2 != 0) {
                board.getBoardSquares(board.sheep[j].getRowPosition(), i).getChildren().add(board.sheep[j]);
                j++;
            }
        }
        board.getBoardSquares(board.wolf.getRowPosition(), board.wolf.getColPosition()).getChildren().add(board.wolf);
    }


    public void moveSheep(Board board) {
        for (int i = 0; i < board.getSheep().length; i++) {
                int finalI = i;
                board.sheep[finalI].setOnMouseClicked(mouseEvent -> {

                    if(move%2 != 0) {

                            if (moveIsPossible(board.sheep[finalI].moveDown(), board.sheep[finalI].moveLeft())) {
                                enablingSheepUtility(board.sheep[finalI].moveDown(), board.sheep[finalI].moveLeft());
                            }
                            if (moveIsPossible(board.sheep[finalI].moveDown(), board.sheep[finalI].moveRight())) {
                                enablingSheepUtility(board.sheep[finalI].moveDown(), board.sheep[finalI].moveRight());
                            }

                        if (moveIsPossible(board.sheep[finalI].moveDown(), board.sheep[finalI].moveLeft())) {
                            boardSquares[board.sheep[finalI].getRowPosition() + 1][board.sheep[finalI].getColPosition() - 1].setOnMouseClicked(mouseEvent1 -> {
                                movementSheep(board.sheep[finalI].moveDown(), board.sheep[finalI].moveLeft(), board.sheep[finalI].moveRight(), board, finalI);
                            });
                        }
                        if (moveIsPossible(board.sheep[finalI].moveDown(), board.sheep[finalI].moveRight())) {
                            boardSquares[board.sheep[finalI].getRowPosition() + 1][board.sheep[finalI].getColPosition() + 1].setOnMouseClicked(mouseEvent1 -> {
                                movementSheep(board.sheep[finalI].moveDown(), board.sheep[finalI].moveRight(), board.sheep[finalI].moveLeft(), board, finalI);
                            });
                        }
                    }
                });
        }
    }



    public void moveWolf(Board board, Stage primaryStage) {
            board.getWolf().setOnMouseClicked(mouseEvent -> {

                if(move%2 == 0) {
                    sheepWon = 4;

                    enablingWolfUtility(board.wolf.moveDown(), board.wolf.moveLeft(), sheepWon);
                    enablingWolfUtility(board.wolf.moveDown(), board.wolf.moveRight(), sheepWon);
                    enablingWolfUtility(board.wolf.moveUp(), board.wolf.moveLeft(), sheepWon);
                    enablingWolfUtility(board.wolf.moveUp(), board.wolf.moveRight(), sheepWon);

                    if(sheepWon == 4){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sheeps are winner!!!");
                        alert.setHeaderText(null);
                        alert.setContentText("Sheeps are winner!!!");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.get() == ButtonType.OK){
                            primaryStage.close();
                        }


                    }

                    if (moveIsPossible(board.wolf.moveDown(), board.wolf.moveLeft())) {
                        boardSquares[board.wolf.moveDown()][board.wolf.moveLeft()].setOnMouseClicked(mouseEvent1 -> {
                            movementWolf(board.wolf.moveDown(), board.wolf.moveLeft(), board, primaryStage);
                        });
                    }
                    if (moveIsPossible(board.wolf.moveDown(), board.wolf.moveRight())) {
                        boardSquares[board.wolf.moveDown()][board.wolf.moveRight()].setOnMouseClicked(mouseEvent1 -> {
                            movementWolf(board.wolf.moveDown(), board.wolf.moveRight(), board, primaryStage);
                        });
                    }
                    if (moveIsPossible(board.wolf.moveUp(), board.wolf.moveLeft())) {
                        boardSquares[board.wolf.moveUp()][board.wolf.moveLeft()].setOnMouseClicked(mouseEvent1 -> {
                            movementWolf(board.wolf.moveUp(), board.wolf.moveLeft(), board, primaryStage);
                        });
                    }
                    if (moveIsPossible(board.wolf.moveUp(), board.wolf.moveRight())) {
                        boardSquares[board.wolf.moveUp()][board.wolf.moveRight()].setOnMouseClicked(mouseEvent1 -> {
                            movementWolf(board.wolf.moveUp(), board.wolf.moveRight(), board, primaryStage);
                        });

                    }
                }
            });



    }

    public void movementWolf(int vertical, int horizontal, Board board, Stage primaryStage){
        if (boardSquares[vertical][horizontal].ishighlight()) {
            boardSquares[vertical][horizontal].getChildren().add(board.getWolf());
            blackenWolfSquares(board);
            boardSquares[board.wolf.getRowPosition()][board.wolf.getColPosition()].setBusy(false);
            board.wolf.setColPosition(horizontal);
            board.wolf.setRowPosition(vertical);
            wolfWon(board.getWolf(), primaryStage);
            boardSquares[board.wolf.getRowPosition()][board.wolf.getColPosition()].setOnMouseClicked(null);
            boardSquares[board.wolf.getRowPosition()][board.wolf.getColPosition()].setBusy(true);
            move++;
        }
    }

    public void movementSheep(int vertical, int horizontalST, int horizontalSC, Board board, int finalI){
        if(boardSquares[vertical][horizontalST].isHighLight()) {
            boardSquares[vertical][horizontalST].setOnMouseClicked(null);
            boardSquares[vertical][horizontalST].getChildren().add(board.sheep[finalI]);
            if (moveIsPossible(vertical, horizontalST)) {
                boardSquares[vertical][horizontalST].blacken();
            }
            if (moveIsPossible(vertical, horizontalSC)) {
                boardSquares[vertical][horizontalSC].blacken();
                boardSquares[vertical][horizontalSC].setOnMouseClicked(null);
            }
            boardSquares[board.sheep[finalI].getRowPosition()][board.sheep[finalI].getColPosition()].setBusy(false);
            board.sheep[finalI].setRowPosition(vertical);
            board.sheep[finalI].setColPosition(horizontalST);
            boardSquares[board.sheep[finalI].getRowPosition()][board.sheep[finalI].getColPosition()].setBusy(true);
            move++;
        }

    }

    public void blackenWolfSquares(Board board){
        if(moveIsPossible(board.wolf.moveDown(), board.wolf.moveLeft())){
            disablingWolfUtility(board.wolf.moveDown(), board.wolf.moveLeft());

        }
        if(moveIsPossible(board.wolf.moveDown(), board.wolf.moveRight())){
            disablingWolfUtility(board.wolf.moveDown(), board.wolf.moveRight());

        }
        if(moveIsPossible(board.wolf.moveUp(), board.wolf.moveLeft())) {
            disablingWolfUtility(board.wolf.moveUp(), board.wolf.moveLeft());

        }
        if(moveIsPossible(board.wolf.moveUp(), board.wolf.moveRight())){
            disablingWolfUtility(board.wolf.moveUp(), board.wolf.moveRight());

        }

    }

    public boolean moveIsPossible(int vertical, int horizontal){
        return (vertical < rowLimit  && vertical >= 0 && horizontal < colLimit && horizontal >= 0);
    }


    public void disablingWolfUtility(int vertical, int horizontal){
        boardSquares[vertical][horizontal].blacken();
        boardSquares[vertical][horizontal].setishighlight(false);
        boardSquares[vertical][horizontal].setOnMouseClicked(null);
    }

    public void enablingWolfUtility(int vertical, int horizontal, int sheepWon){
        if (moveIsPossible(vertical, horizontal)) {
            if(!boardSquares[vertical][horizontal].isBusy()) {
                boardSquares[vertical][horizontal].highlight(Color.YELLOWGREEN);
                boardSquares[vertical][horizontal].setishighlight(true);
                this.sheepWon = sheepWon - 1;
            }
        }
    }

    public void enablingSheepUtility(int vertical, int horizontal){
        if (moveIsPossible(vertical, horizontal)) {
            if(!boardSquares[vertical][horizontal].isBusy()) {
                boardSquares[vertical][horizontal].highlight(Color.YELLOWGREEN);
                boardSquares[vertical][horizontal].setishighlight(true);
            }
        }
    }


    public void wolfWon(Pawn wolf, Stage primaryStage){
        if(wolf.getRowPosition() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wolf are winner!!!");
            alert.setHeaderText(null);
            alert.setContentText("Wolf are winner!!!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                primaryStage.close();
            }
        }
    }


}
