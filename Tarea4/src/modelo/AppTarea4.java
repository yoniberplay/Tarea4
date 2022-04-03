package modelo;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;


public class AppTarea4 extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root ;
			root = FXMLLoader.load(getClass().getResource("/Vista/LogginView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
