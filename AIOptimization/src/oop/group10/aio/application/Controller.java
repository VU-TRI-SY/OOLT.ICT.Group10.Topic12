package oop.group10.aio.application;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.optimization.ParticleSwarmOptimization;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class Controller implements Initializable{
	
	OptimizationForTSP optimization;
	
	TravelingSalesmanProblem problem;
	@FXML
	private Button button;
	
	@FXML
    private Canvas backgroundCanvas;
	
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
		optimization=new ParticleSwarmOptimization(problem,this);
		drawCanvasBackground();
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
	public void startSolving() {
		Thread t= new Thread(optimization, "Optimization");
		t.start();
	}
	public void stopSolving() {
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
}
