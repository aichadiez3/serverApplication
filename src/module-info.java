module serverApplication {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	
	opens server to javafx.graphics, javafx.fxml;
}
