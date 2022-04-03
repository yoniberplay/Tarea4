package Controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Vista.VentanaEmergente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelo.Conexion;
import modelo.logginmodelo;

public class registro implements Initializable{
	
	private Connection con;

	@FXML
	private Label lblclave;

	@FXML
	private Label lblconfirmacion;

	@FXML
	private JFXButton btnregistrarme;

    @FXML
    private JFXButton btnsalir;

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

    private Stage escenario;
    private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	void Mousearrastrado(MouseEvent event) {
		escenario = (Stage) txtusuario.getScene().getWindow();
		escenario.setX(event.getScreenX() - xOffset);
		escenario.setY(event.getScreenY() - yOffset);
		
	}

	
	@FXML
	void Mousepresionado(MouseEvent event) {
		Scene escene = txtusuario.getScene();
    	escene.setCursor(Cursor.HAND);
		 xOffset = event.getSceneX();
         yOffset = event.getSceneY();
	}
	
	@FXML
    void MouseSuelto(MouseEvent event) {
    	Scene escene = txtusuario.getScene();
    	escene.setCursor(Cursor.DEFAULT);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Conexion a = Conexion.getobj();
		con = a.getConexion();
		
	}
    
    @FXML
	void Enter(KeyEvent event) {
		int enter = event.getCode().getCode();
		
		if (enter==10) {
			AcccionRegistro(null);
		}

	}

    @FXML
    void Cerrar(ActionEvent event) {
    	escenario = (Stage) txtusuario.getScene().getWindow();
    	escenario.close();
    	new logginmodelo();

    }
    
    @FXML
    void CerrarX(MouseEvent event) {
    	escenario = (Stage) txtusuario.getScene().getWindow();
    	escenario.close();

    }
    
	
    @FXML
    void AcccionRegistro(ActionEvent event) {
    	
    	boolean cond = true;
    	String [] datos = {txtusuario.getText(),txtnombre.getText(),txtapellido.getText(),
    			txttelefono.getText(),txtcorreo.getText(),txtpasswd.getText() };
    	
    	//validando que todos los campos esten llenos
    	for (String a : datos) {
			if (a.equals("")) {
				cond=false;
			}
		}
    	boolean condtemp = usuarioexistente();
    	if (cond && condtemp) {
    		//validar que la clave y su confirmacion sean la mismas.
    		if (txtpasswd.getText().equals(txtpasswdconfir.getText())) {
    			
    			InsertarRegistro(datos);
    			escenario = (Stage) txtusuario.getScene().getWindow();
    	    	escenario.close();
    	    	new logginmodelo();
    			
				
			}else {
				lblclave.setTextFill(Color.RED);
				lblconfirmacion.setTextFill(Color.RED);
			}
    		
			
		}else {
			if (condtemp) {
				VentanaEmergente.AvisoEmergente("Debe rellenar todos los campos.");	
			}
		
		}
    	
    	
    }
    
    private void InsertarRegistro(String[] datos) {
    	
    	String consulta = "CALL PROC_insertarusuario(?,?,?,?,?,?)";
    	
    	
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
    
    private boolean usuarioexistente(){
    	boolean cond = true;
    	Statement st;
    	
    	try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("CALL PROC_consultausuario()");
			while(rs.next()) {
				String usuariotemp =  rs.getString(1);
				if (txtusuario.getText().equalsIgnoreCase(usuariotemp)) {
					cond=false;
				}
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
    	if (cond == false) {
			VentanaEmergente.AvisoEmergente("El usuario "+txtusuario.getText()+" ya esta en uso.");
		}
    	return cond;
    }
    

}

