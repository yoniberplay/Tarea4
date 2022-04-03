module Tarea4 {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.jfoenix;
	requires javafx.base;
	requires java.sql;
	
	opens modelo to javafx.graphics, javafx.fxml;
	opens Controlador to javafx.graphics, javafx.fxml;
	opens Vista to javafx.graphics, javafx.fxml;
	
	
	exports Controlador;
	exports modelo;
	exports Vista;
}
