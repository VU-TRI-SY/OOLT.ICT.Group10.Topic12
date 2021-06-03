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
	//Value for optimization
	@FXML
    private Text alphaText;
	
    @FXML
    private Text betaText;

    @FXML
    private Text rhoText;
    
    @FXML
    private Text wText;
    
    @FXML
    private Slider alphaSlider;
    
    @FXML
    private Slider betaSlider;

    @FXML
    private Slider wSlider;
    
    @FXML
    private Slider rhoSlider;
    
    @FXML
    private TextField alphaValue;
    
    @FXML
    private TextField betaValue;

    @FXML
    private TextField wValue;
    
    @FXML
    private TextField rhoValue;
    
    @FXML
    private Text iterationText;
    
    @FXML
    private Slider iterationSlider;
    
    @FXML
    private TextField iterationValue;
    
    @FXML
    private Text individualText;
    
    @FXML
    private Slider individualSlider;
    
    @FXML
    private TextField individualValue;
    
	//Related to Simulated Anealling
	@FXML
	private Text textTemp;
	
	@FXML
    private Text temperature;
	
	@FXML
    private ProgressBar temperatureBar;
	
	@FXML
    private Text probabilityText;

	@FXML
	private TextField probabilityValue;	
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
		//Bind
		iterationValue.textProperty().bind(iterationSlider.valueProperty().asString("%.0f"));
		alphaValue.textProperty().bind(alphaSlider.valueProperty().asString("%.1f"));
		betaValue.textProperty().bind(betaSlider.valueProperty().asString("%.1f"));
		rhoValue.textProperty().bind(rhoSlider.valueProperty().asString("%.1f"));
		wValue.textProperty().bind(wSlider.valueProperty().asString("%.1f"));
		individualValue.textProperty().bind(individualSlider.valueProperty().asString("%.0f"));
		//BindforValueTextField
		iterationValue.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		alphaValue.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		betaValue.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		rhoValue.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Ant Colony Optimization"));
		wValue.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Particle Swarm Optimization"));
		individualValue.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		probabilityValue.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Simulated Anealling Optimization"));
		//BindforSlider
		iterationSlider.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		alphaSlider.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		betaSlider.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		rhoSlider.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Ant Colony Optimization"));
		wSlider.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Particle Swarm Optimization"));
		individualSlider.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		//BindForText
		iterationText.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		alphaText.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		betaText.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		rhoText.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Ant Colony Optimization"));
		wText.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Particle Swarm Optimization"));
		individualText.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		probabilityText.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Simulated Anealling Optimization"));
		//Bind for change optimization value
		iterationSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				int value=newValue.intValue();
				optimization.setNumberOfIteration(value);
			}
		});
		alphaSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				float value=newValue.floatValue();
				if(optimization instanceof ParticleSwarmOptimization) {
					ParticleSwarmOptimization o=(ParticleSwarmOptimization) optimization;
					o.setAlphaMax(value);
				}
				if(optimization instanceof AntColonyOptimization) {
					AntColonyOptimization o=(AntColonyOptimization) optimization;
					o.setDefautAlpha(value);
				}
			}
		});
		betaSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				float value=newValue.floatValue();
				if(optimization instanceof ParticleSwarmOptimization) {
					ParticleSwarmOptimization o=(ParticleSwarmOptimization) optimization;
					o.setBetaMin(value);
				}
				if(optimization instanceof AntColonyOptimization) {
					AntColonyOptimization o=(AntColonyOptimization) optimization;
					o.setDefautBeta(value);
				}
			}
		});
		rhoSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				float value=newValue.floatValue();
				if(optimization instanceof AntColonyOptimization) {
					AntColonyOptimization o=(AntColonyOptimization) optimization;
					o.setDefautRho(value);
				}
			}
		});
		wSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				float value=newValue.floatValue();
				if(optimization instanceof ParticleSwarmOptimization) {
					ParticleSwarmOptimization o=(ParticleSwarmOptimization) optimization;
					o.setMaxW(value);
				}
			}
		});
		individualSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				int value=newValue.intValue();
				if(optimization instanceof ParticleSwarmOptimization) {
					ParticleSwarmOptimization o=(ParticleSwarmOptimization) optimization;
					o.setNumberOfParticles(value);
				}
				if(optimization instanceof AntColonyOptimization) {
					AntColonyOptimization o=(AntColonyOptimization) optimization;
					o.setNumberOfAnts(value);
				}
			}
		});
		
		temperatureBar.setStyle("-fx-accent: red");
		speedField.setText(1.0+"");
		problem=new TravelingSalesmanProblem();
		drawCanvasBackground();
		speed.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				float floatValue=newValue.floatValue();
				if(optimization!=null) optimization.setSpeed(floatValue);
			}			
		});
		//For Simulated Anealling
		temperatureBar.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Simulated Anealling Optimization"));
		temperature.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Simulated Anealling Optimization"));
		textTemp.visibleProperty().bind(choiceBox.valueProperty().isEqualTo("Simulated Anealling Optimization"));
		//For the two others optimization
		progressBar.visibleProperty().bind(choiceBox.valueProperty().isNotEqualTo("Simulated Anealling Optimization"));
		speedField.textProperty().bind(speed.valueProperty().asString("%.1f"));
		
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
		optimization=new ParticleSwarmOptimization(problem, this);
		setDefaultValuePSO();
	}
	public void changeToSAOSolver() {
		optimization=new SimulatedAneallingOptimization(problem, this);
		setDefaultValueSAO();
	}
	public void changeToACOSolver() {
		optimization=new AntColonyOptimization(problem, this);
		setDefaultValueACO();
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
			Thread.currentThread().join(1000);
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
			Thread.currentThread().join(1000);
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
	public void setDefaultValuePSO() {
		alphaSlider.setMin(0.0);
		alphaSlider.setMax(1.0);
		betaSlider.setMin(0.0);
		betaSlider.setMax(1.0);
		ParticleSwarmOptimization o=(ParticleSwarmOptimization) optimization;
		float alpha=o.getMaxW();
		float beta=o.getBetaMin();
		float w=o.getMaxW();
		int iteration=o.getMaximumIteration();
		iterationSlider.setValue(iteration);
		alphaSlider.setValue(alpha);
		betaSlider.setValue(beta);
		wSlider.setValue(w);
		individualSlider.setValue(o.getNumberOfParticles());
	}
	public void setDefaultValueACO() {
		alphaSlider.setMin(0.0);
		alphaSlider.setMax(5.0);
		betaSlider.setMin(0.0);
		betaSlider.setMax(5.0);
		AntColonyOptimization o=(AntColonyOptimization) optimization;
		float alpha=o.getDefautAlpha();
		float beta=o.getDefautBeta();
		float rho=o.getDefautRho();
		int iteration=o.getMaximumIteration();
		iterationSlider.setValue(iteration);
		alphaSlider.setValue(alpha);
		betaSlider.setValue(beta);
		rhoSlider.setValue(rho);
		individualSlider.setValue(o.getNumberOfAnts());
	}
	public void setDefaultValueSAO() {
		
	}
	public void setDisablityOfSlider(boolean disablity) {
		alphaSlider.setDisable(disablity);
		betaSlider.setDisable(disablity);
		wSlider.setDisable(disablity);
		rhoSlider.setDisable(disablity);
		iterationSlider.setDisable(disablity);
		individualSlider.setDisable(disablity);
	}
	public void updateAcceptProbability(float value) {
		Platform.runLater(()->{
			probabilityValue.setText(Math.ceil(value*1000)/1000+"");
		});
	}
}
