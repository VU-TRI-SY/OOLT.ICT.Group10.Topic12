package oop.group10.aio.application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class InputController implements Initializable {
	
	private Controller controller;
	private int numOfCites;
	private ArrayList<Float> xPos;
	private ArrayList<Float> yPos;
	
	@FXML
    private Button finish;
	
	@FXML
    private ImageView imageView;
	
	@FXML
    private Canvas canvas;
	
	@FXML
	private AnchorPane anchorPane;
	 
	@FXML
    private TextField textField2;

    @FXML
    private TextField textField1;
    
    @FXML
    private TextField promptText;

	void putControllerReference(Controller controller){
		this.controller=controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		imageView.setImage(new Image("asean.png"));
		textField1.setText(0+"");
		textField2.setText(0+"");
		xPos=new ArrayList<>();
		yPos=new ArrayList<>();
		numOfCites=0;
		anchorPane.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(event.getTarget()==finish) return;
				if(event.getX()>=600) return;
				float x=(float)event.getX();
				float y=(float)event.getY();
				drawPointOnCanvas(x, y);
				numOfCites++;
				xPos.add(x);
				yPos.add(y);
				textField1.setText(x+"");
				textField2.setText(y+"");
				promptText.setPromptText("("+x+","+y+")");
			}
		});
	}
	
	public void drawPointOnCanvas(float x,float y) {
		GraphicsContext context=canvas.getGraphicsContext2D();
		context.setFill(Color.RED);
		context.fillOval(x, y, 10, 10);
		context.closePath();
	}
	public void sendDataToController() {
		controller.inputDataForProblem(xPos, yPos, numOfCites);
		promptText.setPromptText("Finished!");
	}
	
	
}
