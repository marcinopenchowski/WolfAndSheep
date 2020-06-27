import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        Board board = new Board();
        MenuPanel menuPanel = new MenuPanel(root, board, primaryStage);
        Game game = new Game(board, primaryStage);
        game.play(board, primaryStage);

        menuPanel.getExitButton().setOnAction(actionEvent -> {
            primaryStage.close();
        });
        primaryStage.setTitle("Wolf and Sheep");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
