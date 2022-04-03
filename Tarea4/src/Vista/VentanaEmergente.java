package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaEmergente{

	public static void AvisoEmergente(String mensaje) {
		Stage escenario = new Stage();
		escenario.initModality(Modality.APPLICATION_MODAL);
		escenario.setTitle("Aviso");
		escenario.setMinWidth(250);
		escenario.setMinHeight(150);
		//escenario.setMinHeight(100);
		
		Label etiqueta = new Label();
		etiqueta.setFont(new Font("Arial Black",14));
		etiqueta.setText(mensaje);
		etiqueta.setPadding(new Insets(10));
		etiqueta.setAlignment(Pos.CENTER);
		
		Button btn = new Button("Cerrar");
		btn.setPadding(new Insets(10));
		btn.setFont(new Font("Arial Black", 14));
		//btn.setAlignment(Pos.BOTTOM_RIGHT);
		btn.setOnAction(e->escenario.close());
		
		VBox lamina = new VBox(10,etiqueta,btn);
		lamina.setAlignment(Pos.CENTER);
		//BorderPane lamina = new BorderPane();
		Scene escena = new Scene(lamina);
 		escenario.setScene(escena);
		escenario.showAndWait();
		
	}


}
