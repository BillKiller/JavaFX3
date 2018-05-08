package controller;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CurvedRectangle;
import model.Decision;
import model.InputRectangle;
import model.MyLine;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane DrawingArea;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Flow Graph Editor");
        initRootLayout();
    }

    /**
     * 鍔犺浇RootLayout.fxml
     */
    public void initRootLayout() {
        try {
	//灏咰SS鏂囦欢鍔犺浇鍒癴xml鏂囦欢涓�
	Application.setUserAgentStylesheet(getClass().getResource("/css/MyCSS.css")
                    .toExternalForm());
        	//灏哛ootLayout.fxml鍔犺浇鍒皉ootLayout鎴愬憳鍙橀噺涓�
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/controller/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            //debug
//            MyLine myLine = new MyLine(500, 500,600,700);
//            myLine.getPane(rootLayout);
            //鐢╮ootLayout鍒濆鍖栦竴涓猻cene锛屾斁鍒皊tage涓婂睍绀�
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initAttributeBox(){
    	try {
        	//灏哛ootLayout.fxml鍔犺浇鍒皉ootLayout鎴愬憳鍙橀噺涓�
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/controller/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            //鐢╮ootLayout鍒濆鍖栦竴涓猻cene锛屾斁鍒皊tage涓婂睍绀�
            Scene scene = new Scene(rootLayout);
            ((ScrollPane)rootLayout.getCenter()).setOnKeyPressed(e->{
         	   System.out.println("as123");
            });
            primaryStage.setScene(scene);
            primaryStage.show();

            ((ScrollPane)rootLayout.getCenter()).requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
