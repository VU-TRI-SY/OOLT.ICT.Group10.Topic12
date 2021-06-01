package oop.group10.aio.application;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.optimization.pso.ParticleSwarmOptimization;
import oop.group10.aio.optimization.sao.SimulatedAneallingOptimization;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class Controller implements Initializable{
	
	OptimizationForTSP optimization;
	
	TravelingSalesmanProblem problem;
	@FXML
	private Button button;
	
	@FXML
    private Canvas backgroundCanvas;
	
	@FXML
    private ChoiceBox<String> choiceBox;
	
	@FXML
    private Canvas canvas;

	@FXML
    private Button stopButton;
	
	@FXML
    private ProgressBar progressBar;
	
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
		drawCanvasBackground();
		choiceBox.getItems().add("Particle Swarm Optimization");
		choiceBox.getItems().add("Simulated Anealling Optimization");
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if(newValue.intValue()==0) {
					changeToPSOSolver();
					System.out.println("Change to PSO Solver!");
				}
				if(newValue.intValue()==1) {
					changeToSAOSolver();
					System.out.println("Change to SAO Solver!");
				}
			}
			
		});
		
	}
	
	//Action that model can use to control view
	
	public synchronized void setGlobalBest() {
		Platform.runLater(()->{
			textField.setText(""+optimization.getGlobalBestValue());
		});
	}
	public synchronized void changeProgress() {
		int stopCondition=optimization.getMaximumIteration();
		int current=optimization.getCurrentIteration();
		float progress=(float)current/stopCondition;
		progressBar.setProgress(progress);
	}
	public synchronized void startSolving() {
		if(optimization==null) {
			System.out.println("Please choose Optimization!");
			return;
		}
		Thread t= new Thread(optimization, "Optimization");
		t.start();
	}
	public void stopSolving() {
		if(optimization==null) return;
		optimization.stopSolving();
	}
	
	//When click stop will stop progress bar motion
	public void stopProgressBarMotion() {
		float x=(float)progressBar.getProgress();
		float i = x;
		while(i<1.0) {
			i+=0.000001;
			progressBar.setProgress(i);
		}
		progressBar.setProgress(1.0);
		//Wait for the process complete 
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
	public Canvas getCanvas() {
		return canvas;
	}
	public synchronized void drawCanvasBackground() {
		float[][] map=problem.getXoyMap();
		GraphicsContext graphicsContext=backgroundCanvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.RED);
		int i;
		for(i=0;i<problem.getNumberOfCities();i++) {
			graphicsContext.fillOval(map[0][i]-7.07, map[1][i]-7.07, 10, 10);
		}
		graphicsContext.closePath();
	}
	public void changeToPSOSolver() {
		optimization=new ParticleSwarmOptimization(problem, this);
	}
	public void changeToSAOSolver() {
		optimization=new SimulatedAneallingOptimization(problem, this);
	}
}
