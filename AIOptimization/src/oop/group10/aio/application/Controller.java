package oop.group10.aio.application;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.optimization.ParticleSwarmOptimization;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class Controller implements Initializable{
	
	OptimizationForTSP optimization;
	
	TravelingSalesmanProblem problem;
	
	@FXML
    private Canvas canvas;

    @FXML
    private ImageView imageView;

    @FXML
    private Label label;

    @FXML
    private TextField textField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		problem=new TravelingSalesmanProblem();
		optimization=new ParticleSwarmOptimization(problem);
		textField.setText("0");
	}
	
	
	

}
