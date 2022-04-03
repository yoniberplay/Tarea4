package Controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Vista.VentanaEmergente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelo.Conexion;
import modelo.PersonaActualizar;

public class actulizador implements Initializable{
	
	private Stage escenario;
	private Connection con;
	
    @FXML
    private TextField txtusuario;

    @FXML
    private TextField txtnombre;

    @FXML
    private TextField txtapellido;

    @FXML
    private TextField txttelefono;

    @FXML
    private TextField txtcorreo;

    @FXML
    private PasswordField txtpasswd;

    @FXML
    private PasswordField txtpasswdconfir;

    @FXML
    private Label lblclave;

    @FXML
    private JFXButton btnregistrarme;

    @FXML
    private Label lblconfirmacion;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		Conexion a = Conexion.getobj();
		con = a.getConexion();
		
		txtusuario.setText(PersonaActualizar.usuario); 
		txtnombre.setText(PersonaActualizar.nombre); 
		txtapellido.setText(PersonaActualizar.apellido); 
		txttelefono.setText(PersonaActualizar.telefono); 
		txtcorreo.setText(PersonaActualizar.email);
		
	}

    @FXML
    void Acccionactualizar(ActionEvent event) {
    	
    	boolean cond = true;
    	String [] datos = {txtnombre.getText(),txtapellido.getText(),
    			txttelefono.getText(),txtcorreo.getText(),txtpasswd.getText(),txtusuario.getText()};
    	
    	//validando que todos los campos esten llenos
    	for (String a : datos) {
			if (a.equals("")) {
				cond=false;
			}
		}
    	if (cond) {
    		//validar que la clave y su confirmacion sean la mismas.
    		if (txtpasswd.getText().equals(txtpasswdconfir.getText())) {
    			ActulizarDatos(datos);
    			
    			escenario = (Stage) txtusuario.getScene().getWindow();
    	    	escenario.close();
    	    	
			}else {
				lblclave.setTextFill(Color.RED);
				lblconfirmacion.setTextFill(Color.RED);
			}
    		
			
		}else {
				VentanaEmergente.AvisoEmergente("Debe rellenar todos los campos.");	
		}
    	

    }
    
    private void ActulizarDatos(String[] datos) {
    	
		String consulta = "CALL PROC_ActualizarUsuario(?,?,?,?,?,?)";

		try {
			PreparedStatement sentencia = con.prepareStatement(consulta);
			sentencia.setString(1, datos[0]);
			sentencia.setString(2, datos[1]);
			sentencia.setString(3, datos[2]);
			sentencia.setString(4, datos[3]);
			sentencia.setString(5, datos[4]);
			sentencia.setString(6, datos[5]);
			sentencia.executeUpdate();

			sentencia.close();

		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

	}

}

