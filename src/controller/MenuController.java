package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.sun.javafx.stage.StageHelper;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController {

	public void newDrawingArea(AnchorPane drawingArea) {
		System.out.println("???");
		while (drawingArea.getChildren().size() != 0) {
			drawingArea.getChildren().remove(0);
		}
	}

	public void saveDrawingArea(String fileName) {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File path = fileChooser.showSaveDialog(stage);
		if (path != null && path.isDirectory()) {
			File saveFile = new File(path, fileName);
			try (PrintWriter saver = new PrintWriter(saveFile)) {

				// saver.println();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getDrawingArea() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DAT", "*.dat"));
		File file = fileChooser.showOpenDialog(stage);
		try (Scanner geter = new Scanner(file)) {

			// geter.nextLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exportDrawingArea(AnchorPane drawingArea) {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Save Directory");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		File saveFile = fileChooser.showSaveDialog(stage);
		System.out.println(saveFile);
		WritableImage image = drawingArea.snapshot(new SnapshotParameters(), null);
		System.out.println(image);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// FileChooser fileChooser = new FileChooser();
	// fileChooser.setTitle("Open Resource File");
	// File path = fileChooser.showSaveDialog(stage);
	// System.out.println(path+" "+fileChooser.getInitialDirectory());
	// if (path != null && path.isDirectory()) {
	// File saveFile = new File(path, fileChooser.getInitialFileName());
	// try (PrintWriter saver = new PrintWriter(saveFile)) {
	// WritableImage image = drawingArea.snapshot(new SnapshotParameters(), null);
	// try {
	// ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", saveFile);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
}
