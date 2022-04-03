package Controlador;

import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Conexion;
import modelo.Registromodelo;
import modelo.listamodelo;

public class loggin implements Initializable{
	
	
	private Stage escenario;
    private double xOffset = 0;
	private double yOffset = 0;
	private Connection con;
	private Statement st;
	private String consultausuario = "CALL PROC_consultausuario()";
	
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
    
    @FXML
	void Enter(KeyEvent event) {
		int enter = event.getCode().getCode();
		
		if (enter==10) {
			iniciarsesion(null);
		}

	}
    
    @FXML
    void Cerrar(ActionEvent event) {
    	
    	escenario = (Stage) txtusuario.getScene().getWindow();
    	escenario.close();

    }
    
    @FXML
    void CerrarX(MouseEvent event) {
    	
    	escenario = (Stage) txtusuario.getScene().getWindow();
    	escenario.close();

    }
	
	  @FXML
	    private TextField txtusuario;

	    @FXML
	    private PasswordField txtpasswd;

	    @FXML
	    private JFXButton btninicio2;

	    @FXML
	    private JFXButton btnsalir;

	    @FXML
	    void accionregistro(ActionEvent event) {
	    	
	    	new Registromodelo();
	    	escenario = (Stage) btnsalir.getScene().getWindow();
	    	escenario.close();
	    }

	    @FXML
	    void iniciarsesion(ActionEvent event) {
	    	
	    	String clave = txtpasswd.getText();
			String usuario = txtusuario.getText();
	    	if (!clave.equals("") && !usuario.equals("")) {
	    		
	    		try {
	    			st = con.createStatement();
	    			ResultSet rs = st.executeQuery(consultausuario);
	    			boolean condicion = true;
	    			while(rs.next()) {
	    				String usuariotemp =  rs.getString(1);
	    				String clavetemp =  rs.getString(2);
	    				if (usuario.equalsIgnoreCase(usuariotemp) && clave.equals(clavetemp)) {
	    					new listamodelo();
	    					condicion=false;
	    					escenario = (Stage) btnsalir.getScene().getWindow();
	    			    	escenario.close();
	    				}
	    			}
	    			if (condicion) {
	    				VentanaEmergente.AvisoEmergente("Datos de usuario, invalidos.");
	    				txtpasswd.setText("");
	    				txtusuario.setText("");
	    				txtusuario.requestFocus();
	    			}
	    			
	    		} catch (SQLException e1) {
	    			System.out.println(e1.getMessage());
	    		}
				
			}else {
				VentanaEmergente.AvisoEmergente("Debes rellenar ambos campos.");
			}
	    }

	    @FXML
	    void salir(ActionEvent event) {
	    	System.out.println(txtpasswd.getText()+ " "+ txtusuario.getText());

	    }


		@Override
		public void initialize(URL location, ResourceBundle resources) {
			Conexion a = Conexion.getobj();
			con = a.getConexion();
		}

}
