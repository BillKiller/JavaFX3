package controller;

import java.net.URL;
import java.util.IllegalFormatCodePointException;
import java.util.ResourceBundle;

import com.sun.javafx.perf.PerformanceTracker.SceneAccessor;
import com.sun.javafx.tk.TKDragGestureListener;

import controller.DrawController;
import controller.ShapeFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import model.BrokenLine;
import model.CurvedRectangle;
import model.Decision;
import model.DoubleBrokenLine;
import model.BrokenLine;
import model.InputRectangle;
import model.MyCircle;
import model.MyLine;
import model.MyRectangle;
import model.MyShape;
import model.RoundRectangle;

public class RootLayoutController implements Initializable {
	@FXML
	private BorderPane rootLayout;
	@FXML
	private AnchorPane drawingArea;
	@FXML
	private AnchorPane leftArea;
	@FXML
	private AnchorPane shapeArea;
	@FXML
	private AnchorPane codeArea;
	@FXML
	private TextArea codeTextArea;
	@FXML
	private MenuItem ModelMenuItem;

	@FXML
	private TextField textfield;
	@FXML
	private Button Button;
	//shape
	@FXML
	private ImageView RoundRectangle;
	@FXML
	private ImageView Rectangle;
	@FXML
	private ImageView Decision;
	@FXML
	private ImageView InputRectangle;
	@FXML
	private ImageView Circular;
	@FXML
	private ImageView CurvedRectangle;
	//line
	@FXML
	private ImageView MyLine;
	@FXML
	private AnchorPane keyBoardPane;
	@FXML
	private TextField textFieldH;
	@FXML
	private TextField textFieldW;
	@FXML
	private TextField textFieldX;
	@FXML
	private TextField textFieldY;

	@FXML
	private TextArea textArea;

	@FXML
	private Button button2;

	@FXML
	private Button shapeORLine;
	@FXML
	private VBox shapeVBox;
	@FXML
	private VBox lineVBox;
	private boolean isShape;


	@FXML
	private Button codeButton;

	private Compiler compiler;
	
	private MenuController menuController;
	public void menuNew() {
		menuController.newDrawingArea(drawingArea);
	}
	public void menuSave() {
		menuController.saveDrawingArea(drawController);
	}
	public void menuOpen() {
		menuController.getDrawingArea(compiler);
	}
	public void menuExport() {
		menuController.exportDrawingArea(drawingArea);
	}
	
	boolean isCodeModel=false;
	public void changeModel() {
		if(isCodeModel==true) {
			isCodeModel=false;
			ModelMenuItem.setText("Change To Code Model");
			shapeArea.setVisible(true);
			codeArea.setVisible(false);
			leftArea.setPrefWidth(200);
			codeButton.setPrefWidth(200);
		}else {
			isCodeModel=true;
			ModelMenuItem.setText("Change To Draw Model");
			shapeArea.setVisible(false);
			codeArea.setVisible(true);
			leftArea.setPrefWidth(400);
			codeButton.setPrefWidth(400);
		}
	}
	
	public void changeShapeORLine() {
		if(isShape) {
			isShape=false;
			shapeORLine.setText("Line");
			shapeVBox.setVisible(false);
			lineVBox.setVisible(true);
		}else {
			isShape=true;
			shapeORLine.setText("Shape");
			shapeVBox.setVisible(true);
			lineVBox.setVisible(false);
		}
	}


	private DrawController drawController;
	private PropertyController propertyController;
	ShapeFactory shapeFactory;
	String selectShape = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		RoundRectangle.setCursor(Cursor.HAND);
		Rectangle.setCursor(Cursor.HAND);
		Decision.setCursor(Cursor.HAND);
		InputRectangle.setCursor(Cursor.HAND);
		Circular.setCursor(Cursor.HAND);
		CurvedRectangle.setCursor(Cursor.HAND);
		MyLine.setCursor(Cursor.HAND);

		drawController=new DrawController(drawingArea);
		shapeFactory=new ShapeFactory(drawingArea,drawController);
		
		shapeArea.setVisible(true);
		codeArea.setVisible(false);
		ModelMenuItem.setText("Change To Code Model");
		isShape=true;
		shapeVBox.setVisible(true);
		lineVBox.setVisible(false);
		
		compiler = new Compiler();
		compiler.setShapeFactory(shapeFactory);
		drawController.setCompiler(compiler);
		compiler.setTextArea(codeTextArea);
		codeButton.setOnMouseClicked(e->{
				compiler.compireProduce(codeTextArea.getText());
		});
		
		menuController=new MenuController();
		
		
//		DoubleBrokenLine myLine = new DoubleBrokenLine(500, 500, 300, 200);
//		myLine.getPane(drawingArea,drawController);

	    propertyController = new PropertyController(textFieldX,textFieldY,textFieldW,textFieldH,textArea);
	    propertyController.setButton(button2);
	    propertyController.edit();
	    propertyController.setDrawController(drawController);
	    drawController.setPropertyController(propertyController);
	    drawController.setKeyBoardManager();
	    
		drawingArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//drawController.clearAllOnEdit();
				keyBoardPane.requestFocus();
				if (event.getClickCount() == 1 && selectShape != null) {
					double x, y;
					x = event.getX();
					y = event.getY();

					shapeFactory.produce(selectShape, x, y);
//					drawController.addDrawArea();
					selectShape = null;
				}
				if(event.getClickCount() ==1 && selectShape == null){
						drawController.getPropertyController().setWorkShape(drawController.workingShape());
						drawController.getPropertyController().update();
				}
			}
		});

		shapeArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					if (event.getTarget().getClass() == ImageView.class) {
						int x,y;
						x=300;
						y=300;
						selectShape=((ImageView)event.getTarget()).getId();
						shapeFactory.produce(selectShape, x, y);
						selectShape = null;
					}
				} else if (event.getClickCount() == 1) {
					if (event.getTarget().getClass() == ImageView.class) {
						ImageView nowImage = (ImageView) event.getTarget();
						selectShape = nowImage.getId();
					}
				}
			}
		});
	}
}
