module MiniProject {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens oop.group10.aio.application to javafx.graphics, javafx.fxml;
}
