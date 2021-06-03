package oop.group10.aio.application;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oop.group10.aio.optimization.OptimizationForTSP;
import oop.group10.aio.optimization.aco.AntColonyOptimization;
import oop.group10.aio.optimization.pso.ParticleSwarmOptimization;
import oop.group10.aio.optimization.sao.SimulatedAneallingOptimization;
import oop.group10.aio.tsp.TravelingSalesmanProblem;

public class Controller implements Initializable{
	
	OptimizationForTSP optimization;
	
	TravelingSalesmanProblem problem;
	//Related to Simulated Anealling
	@FXML
	private Text textTemp;
	
	@FXML
    private Text temperature;
	
	@FXML
    private ProgressBar temperatureBar;
	
	//Related to others
	
	@FXML
    private Canvas canvas;
	
	@FXML
	private Button button;
	
	@FXML
    private Button inputButton;
	
	@FXML
    private Canvas backgroundCanvas;
	
	@FXML
    private ChoiceBox<String> choiceBox;

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
    
    @FXML
    private Slider speed;
    
    @FXML
    private TextField speedField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		temperatureBar.setStyle("-fx-accent: red");
		speedField.setText(1.0+"");
		problem=new TravelingSalesmanProblem();
		drawCanvasBackground();
		speed.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				float floatValue=newValue.floatValue();
				speedField.setText(Math.ceil(floatValue*10)/10+"");
				if(optimization!=null) optimization.setSpeed(floatValue);
			}			
		});
		choiceBox.getItems().add("Particle Swarm Optimization");
		choiceBox.getItems().add("Simulated Anealling Optimization");
		choiceBox.getItems().add("Ant Colony Optimization");
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
				if(newValue.intValue()==2) {
					changeToACOSolver();
					System.out.println("Change to ACO Solver!");
				}
			}
			
		});
		
	}
	//Action that model can use to control view
	public synchronized void setCurrentSolution(float currentSolution) {
		Platform.runLater(()->{
			textField.setText(""+currentSolution);
		});
	}
	public synchronized void setGlobalBest() {
		Platform.runLater(()->{
			textField.setText(""+optimization.getGlobalBestValue());
		});
	}
	//Change to correspond solver
	public void changeToPSOSolver() {
		progressBar.setVisible(true);
		temperatureBar.setVisible(false);
		temperature.setVisible(false);
		textTemp.setVisible(false);
		optimization=new ParticleSwarmOptimization(problem, this);
	}
	public void changeToSAOSolver() {
		progressBar.setVisible(false);
		temperatureBar.setVisible(true);
		temperature.setVisible(true);
		textTemp.setVisible(true);
		optimization=new SimulatedAneallingOptimization(problem, this);
	}
	public void changeToACOSolver() {
		progressBar.setVisible(true);
		temperatureBar.setVisible(false);
		temperature.setVisible(false);
		textTemp.setVisible(false);
		optimization=new AntColonyOptimization(problem, this);
	}
	//Start solving
	public synchronized void startSolving() {
		if(optimization==null) {
			System.out.println("Please choose Optimization!");
			return;
		}
		drawCanvasBackground();
		Thread t= new Thread(optimization, "Optimization");
		optimization.setSpeed((int)speed.getValue());
		t.start();
	}
	//Stop solving
	public void stopSolving() {
		if(optimization==null) return;
		optimization.stopSolving();
	}
	//Change the progress bar while running
	public synchronized void changeProgress() {
		int stopCondition=optimization.getMaximumIteration();
		int current=optimization.getCurrentIteration();
		float progress=(float)current/stopCondition;
		progressBar.setProgress(progress);
	}
	public synchronized void changeTemperatureProgress() {
		SimulatedAneallingOptimization o=(SimulatedAneallingOptimization) optimization;
		float maxTemperature=o.getStartTemperature();
		float currentTemperature=o.getCurrentTemperature();
		float progress=(float)currentTemperature/maxTemperature;
		temperature.setText(Math.ceil(currentTemperature*100)/100 +"");
		temperatureBar.setProgress(progress);
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
	public void stopTemperatureBarMotion() {
		float x=(float)temperatureBar.getProgress();
		float i = x;
		while(i<1.0) {
			i+=0.000001;
			temperatureBar.setProgress(i);
		}
		temperatureBar.setProgress(1.0);
		//Wait for the process complete 
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
	//Get canvas for drawing on it
	public Canvas getCanvas() {
		return canvas;
	}
	//Draw the cities in background
	public synchronized void drawCanvasBackground() {
		float[][] map=problem.getXoyMap();
		GraphicsContext graphicsContext=backgroundCanvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.RED);
		graphicsContext.clearRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
		int i;
		for(i=0;i<problem.getNumberOfCities();i++) {
			graphicsContext.fillOval(map[0][i]-7.07, map[1][i]-7.07, 10, 10);
		}
		graphicsContext.closePath();
	}
	
	//Input data for problem
	public void inputDataForProblem(ArrayList<Float> xPos,ArrayList<Float> yPos,int numOfCites) {
		problem.init(xPos, yPos, numOfCites);
	}
	//Change to input view
	public void switchToInputView() {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("InputView.fxml"));
		Parent root=null;
		try {
			root=loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		InputController inputController=loader.getController();
		inputController.putControllerReference(this);
		
		Stage newStage=new Stage();
		newStage.setScene(new Scene(root));
		newStage.setTitle("Input cities");
		newStage.show();
		
	}
	
}
