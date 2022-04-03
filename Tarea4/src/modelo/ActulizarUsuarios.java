package modelo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ActulizarUsuarios {
	
public ActulizarUsuarios() {
		
		primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/Vista/ActulizarUsuarios.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.setTitle("Actulizar Datos.");
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.showAndWait();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private Stage primaryStage;
}

