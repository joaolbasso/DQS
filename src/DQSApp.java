

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 *
 * @author VIDEO
 */
public class DQSApp extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MenuPrincipal.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("DQS - Sistema de informação");
        Image icon = new Image("/View/Imagens/icon.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        
        /*
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Aperte q");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        */
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    
    
}
