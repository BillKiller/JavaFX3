package model;

import controller.DrawController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class DoubleBrokenLine extends MyLine{
	private Line xLine1;
	private Line xLine2;
	private Line yLine;
//	private double xLen=100;//水平线的初始长度
	public DoubleBrokenLine(double startX, double startY, double endX, double endY,int factoryID) {
		this(startX, startY, endX, endY);
		this.factoryID = factoryID;
	}
	public DoubleBrokenLine(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.booleanProperty = new SimpleBooleanProperty(false);
		super.line=new Line();
		xLine1=new Line(startX,startY,startX,startY);
		xLine2=new Line(startX,endY,endX,endY);
		yLine=new Line(startX,startY,startX,endY);
		xLine1.setStrokeWidth(3);
		xLine2.setStrokeWidth(3);
		yLine.setStrokeWidth(3);
		circle = new Circle();
		circle.setCenterX(startX);
		circle.setCenterY(startY);
		circle.setRadius(3);
		triangle = new Polygon();
		setShape();
		super.startListening();
		addLineListening();
	}
	public void delete(){
		drawingArea.getChildren().remove(xLine1);
		drawingArea.getChildren().remove(xLine2);
		drawingArea.getChildren().remove(yLine);
		drawingArea.getChildren().remove(circle);
		drawingArea.getChildren().remove(triangle);
	}
	
	@Override
	public void setShape() {

		double dx = endX - startX;
		double dy = 0;
		double k = 1 / Math.sqrt(dx * dx + dy * dy);
		double u = (double) Math.sqrt(3) * StandardNum.TRIANBLE_LEN / (Math.sqrt(dx * dx + dy * dy));
		double v = (double) StandardNum.TRIANBLE_LEN / Math.sqrt(dx * dx + dy * dy);
		double mX = endX - u * dx;
		double mY = endY - u * dy;

		double aX = v * dy + mX;
		double aY = v * (-1 * dx) + mY;

		double bX = mX - v * dy;
		double bY = mY - v * (-1 * dx);
		double endX = this.endX + 5 * k * dx;
		double endY = this.endY + 5 * k * dy;
		Double[] list = { aX, aY, endX, endY, bX, bY };
		triangle.getPoints().setAll(list);
		circle.setCenterX(startX);
		circle.setCenterY(startY);
		xLine1.setStartX(startX);
		xLine1.setStartY(startY);
		xLine1.setEndX();
		xLine1.setEndY(startY);
		xLine2.setStartX(this.endX+xLen);
		xLine2.setStartY(this.endY);
		xLine2.setEndX(this.endX);
		xLine2.setEndY(this.endY);
		yLine.setStartX(startX+xLen);
		yLine.setStartY(startY);
		yLine.setEndX(this.endX+xLen);
		yLine.setEndY(this.endY);
		isSelected = true;
		booleanProperty.setValue(true);
	}
	@Override
	public void getPane(AnchorPane drawingArea, DrawController drawController) {
		drawingArea.getChildren().add(xLine1);
		drawingArea.getChildren().add(xLine2);
		drawingArea.getChildren().add(yLine);
		drawingArea.getChildren().add(circle);
		drawingArea.getChildren().add(triangle);
		this.drawingArea = drawingArea;
		this.drawController = drawController;
	}
	@Override
	public void setToTop() {
		drawingArea.getChildren().remove(xLine1);
		drawingArea.getChildren().remove(xLine2);
		drawingArea.getChildren().remove(yLine);
		drawingArea.getChildren().remove(circle);
		drawingArea.getChildren().remove(triangle);
		getPane(drawingArea, drawController);
	}
	public void addLineListening() {
		xLine1.setCursor(Cursor.HAND);
		xLine2.setCursor(Cursor.HAND);
		yLine.setCursor(Cursor.HAND);
		xLine1.setOnMouseEntered(e -> {
			if (!isOnTheLine) {
				lastX = e.getX();
				lastY = e.getY();
				isOnTheLine = true;
			}
		});
		xLine1.setOnMouseExited(e -> {
			isOnTheLine = false;
		});
		xLine1.setOnMouseDragged(e -> {
			double dx = e.getX() - lastX;
			double dy = e.getY() - lastY;
			lastX = e.getX();
			lastY = e.getY();
			move(dx, dy);
		});
		xLine1.setOnMouseReleased(e->{
			this.setToTop();
			booleanProperty.setValue(false);
			if(headLinkShape!=null)headLinkShape.delConnectionInfo(this);
			if(tailLinkShape!=null)tailLinkShape.delConnectionInfo(this);
			booleanProperty.setValue(false);
		});
		
		xLine2.setOnMouseEntered(e -> {
			if (!isOnTheLine) {
				lastX = e.getX();
				lastY = e.getY();
				isOnTheLine = true;
			}
		});
		xLine2.setOnMouseExited(e -> {
			isOnTheLine = false;
		});
		xLine2.setOnMouseDragged(e -> {
			double dx = e.getX() - lastX;
			double dy = e.getY() - lastY;
			lastX = e.getX();
			lastY = e.getY();
			move(dx, dy);
		});
		xLine2.setOnMouseReleased(e->{
			this.setToTop();
			booleanProperty.setValue(false);
			if(headLinkShape!=null)headLinkShape.delConnectionInfo(this);
			if(tailLinkShape!=null)tailLinkShape.delConnectionInfo(this);
			booleanProperty.setValue(false);
		});


		yLine.setOnMouseEntered(e -> {
			if (!isOnTheLine) {
				lastX = e.getX();
				lastY = e.getY();
				isOnTheLine = true;
			}
		});
		yLine.setOnMouseExited(e -> {
			isOnTheLine = false;
		});
		yLine.setOnMouseDragged(e -> {
			double dx = e.getX() - lastX;
			double dy = e.getY() - lastY;
			lastX = e.getX();
			lastY = e.getY();
			move(dx, dy);
		});
		yLine.setOnMouseReleased(e->{
			this.setToTop();
			if(headLinkShape!=null)headLinkShape.delConnectionInfo(this);
			if(tailLinkShape!=null)tailLinkShape.delConnectionInfo(this);
			booleanProperty.setValue(false);
		});
	}
	public void changeListener() {
		booleanProperty.addListener(e -> {
			if (booleanProperty.getValue() == false) {
				drawController.getPropertyController().setWorkShape(this);
				drawController.getPropertyController().update();
				drawController.saveChange();
			}
		});
	}
}
