package Controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.ActulizarUsuarios;
import modelo.Conexion;
import modelo.Persona;
import modelo.PersonaActualizar;
import modelo.Registromodelo;
import modelo.logginmodelo;

public class tabla implements Initializable{

	private Stage escenario;
	private Connection con;

	@FXML
	private Label lbltitulo;

	@FXML
	private TableView<Persona> tabla;

	@FXML
	private TableColumn<Persona, String> col_usuario;

	@FXML
	private TableColumn<Persona, String> col_nombre;

	@FXML
	private TableColumn<Persona, String> col_telefono;

	@FXML
	private TableColumn<Persona, String> col_apellido;

	@FXML
	private TableColumn<Persona, String> col_email;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Conexion a = Conexion.getobj();
		con = a.getConexion();
		MostrarPrecios();
		
	}
	
	void MostrarPrecios() {

		ObservableList<Persona> lista = RellenadoTabla();

		col_usuario.setCellValueFactory(new PropertyValueFactory<Persona, String>("usuario"));
		col_nombre.setCellValueFactory(new PropertyValueFactory<Persona, String>("nombre"));
		col_apellido.setCellValueFactory(new PropertyValueFactory<Persona, String>("apellido"));
		col_telefono.setCellValueFactory(new PropertyValueFactory<Persona, String>("telefono"));
		col_email.setCellValueFactory(new PropertyValueFactory<Persona, String>("email"));
		
		tabla.setItems(lista);

	}
	
	public ObservableList<Persona> RellenadoTabla() {

		ObservableList<Persona> lista = FXCollections.observableArrayList();
		String consulta = "CALL PROC_RellenadoTabla()";
		Statement man;
		try {
			man = con.createStatement();
			ResultSet rs = man.executeQuery(consulta);
			Persona obj;
			while (rs.next()) {
			obj = new Persona(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),null);
			lista.add(obj);
			}

		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		return lista;

	}

	@FXML
	void actualizar(ActionEvent event) {
		
		if (!tabla.getSelectionModel().isEmpty()) {
			Persona pers = (Persona) tabla.getSelectionModel().getSelectedItems().get(0);
			
			PersonaActualizar.usuario = pers.getUsuario();
			PersonaActualizar.nombre = pers.getNombre();
			PersonaActualizar.apellido = pers.getApellido();
			PersonaActualizar.telefono = pers.getTelefono();
			PersonaActualizar.email = pers.getEmail();
			
			new ActulizarUsuarios();
		}
		
		
		MostrarPrecios();
	}

	@FXML
	void cerrarseccion(ActionEvent event) {

		escenario = (Stage) lbltitulo.getScene().getWindow();
		escenario.close();
		new logginmodelo();

	}

	@FXML
	void eliminar(ActionEvent event) {
		
		if (!tabla.getSelectionModel().isEmpty()) {
			Persona pers = (Persona) tabla.getSelectionModel().getSelectedItems().get(0);
			
			String consulta = "CALL PROC_eliminando(?)";
			try {
				PreparedStatement sentencia = con.prepareStatement(consulta);
				sentencia.setString(1, pers.getUsuario());
				sentencia.executeUpdate();

			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}
		
		MostrarPrecios();
	}

	@FXML
	void nuevo(ActionEvent event) {
		new Registromodelo();
		escenario = (Stage) lbltitulo.getScene().getWindow();
		escenario.close();
	}

}
