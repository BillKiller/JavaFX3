package model;

import java.text.DecimalFormat;
import java.util.ArrayList;

import controller.DrawController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class DoubleBrokenLine extends MyLine {
	protected Line xLine1;
	protected Line xLine2;
	protected Line yLine;
	protected double aX;
	protected double aY;
	protected double bX;
	protected double bY;

	public DoubleBrokenLine(double startX, double startY, double endX, double endY, double aX, int factoryID) {
		this(startX, startY, endX, endY, aX);
		this.factoryID = factoryID;
	}

	public DoubleBrokenLine(double startX, double startY, double endX, double endY, double aX) {
		middlePoints=new ArrayList<>();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.aX = aX;
		this.aY = startY;
		this.bX = aX;
		this.bY = endY;
		this.booleanProperty = new SimpleBooleanProperty(false);
		super.line = new Line();
		xLine1 = new Line(startX, startY, aX, aY);
		xLine2 = new Line(bX, bY, endX, endY);
		yLine = new Line(aX, aY, bX, bY);
		xLine1.setStrokeWidth(3);
		xLine2.setStrokeWidth(3);
		yLine.setStrokeWidth(3);
		circle = new Circle();
		circle.setCenterX(startX);
		circle.setCenterY(startY);
		circle.setRadius(5);
		triangle = new Polygon();
		setShape();
		this.isSelected = false;
		super.startListening();
		addLineListening();
	}

	public void delete() {
		drawingArea.getChildren().remove(xLine1);
		drawingArea.getChildren().remove(xLine2);
		drawingArea.getChildren().remove(yLine);
		drawingArea.getChildren().remove(circle);
		drawingArea.getChildren().remove(triangle);
	}

	@Override
	public void setShape() {
		System.out.println(startX+" "+startY+" "+aX+" "+aY+" "+bX+" "+bY+" "+endX+" "+endY);
		double dx = endX - bX;
		double dy = endY - bY;
		double k = 1 / Math.sqrt(dx * dx + dy * dy);
		double u = (double) Math.sqrt(3) * StandardNum.TRIANBLE_LEN / (Math.sqrt(dx * dx + dy * dy));
		double v = (double) StandardNum.TRIANBLE_LEN / Math.sqrt(dx * dx + dy * dy);
		double mX = endX - u * dx;
		double mY = endY - u * dy;

		double aaX = v * dy + mX;
		double aaY = v * (-1 * dx) + mY;

		double bbX = mX - v * dy;
		double bbY = mY - v * (-1 * dx);
		double endX = this.endX + 5 * k * dx;
		double endY = this.endY + 5 * k * dy;
		Double[] list = { aaX, aaY, endX, endY, bbX, bbY };
		triangle.getPoints().setAll(list);
		circle.setCenterX(startX);
		circle.setCenterY(startY);

		xLine1.setStartX(startX);
		xLine1.setStartY(startY);
		xLine1.setEndX(aX);
		xLine1.setEndY(aY);
		xLine2.setStartX(bX);
		xLine2.setStartY(bY);
		xLine2.setEndX(this.endX);
		xLine2.setEndY(this.endY);
		yLine.setStartX(aX);
		yLine.setStartY(aY);
		yLine.setEndX(bX);
		yLine.setEndY(bY);
		middlePoints.clear();
		middlePoints.add(new Circle((startX+aX)/2,startY,StandardNum.DRAW_POINTS_RADIUS));
		middlePoints.add(new Circle((aX+endX)/2,endY,StandardNum.DRAW_POINTS_RADIUS));
		middlePoints.add(new Circle(aX,(startY+endY)/2,StandardNum.DRAW_POINTS_RADIUS));
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
	@Override
	public void endMove(double x, double y) {
		endX = x - triangle.getParent().getLayoutX();
		endY = y - triangle.getParent().getLayoutY();
		bY=endY;
		setShape();
	}
	@Override
	public void move(double dx, double dy) {
		startX = startX + dx;
		startY = startY + dy;
		endX = endX + dx;
		endY = endY + dy;
		aX+=dx;
		aY+=dy;
		bX+=dx;
		bY+=dy;
		setShape();
	}
	@Override
	public void startMove(double x, double y) {
		startX = x - triangle.getParent().getLayoutX();
		startY = y - triangle.getParent().getLayoutY();
		aY=startY;
		setShape();
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
		xLine1.setOnMouseReleased(e -> {
			this.setToTop();
			booleanProperty.setValue(false);
			if (headLinkShape != null)
				headLinkShape.delConnectionInfo(this);
			if (tailLinkShape != null)
				tailLinkShape.delConnectionInfo(this);
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
		xLine2.setOnMouseReleased(e -> {
			this.setToTop();
			booleanProperty.setValue(false);
			if (headLinkShape != null)
				headLinkShape.delConnectionInfo(this);
			if (tailLinkShape != null)
				tailLinkShape.delConnectionInfo(this);
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
		yLine.setOnMouseReleased(e -> {
			this.setToTop();
			if (headLinkShape != null)
				headLinkShape.delConnectionInfo(this);
			if (tailLinkShape != null)
				tailLinkShape.delConnectionInfo(this);
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
	@Override
	public String toString(int factoryID) {
		DecimalFormat df = new DecimalFormat("#.00");

		String tostring = getClass().getSimpleName() + "< " + factoryID + " >" + "(" + df.format(this.startX) + ","
				+ df.format(startY) +df.format(aX)+","+df.format(aY)+","+df.format(bX)+","+df.format(bY)+ "," + df.format(endX) + "," + df.format(endY) + ")" + "[ " + " " + " ]" + " ;\n";
		System.out.println(tostring);
		return tostring;
	}
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.00");

		String tostring = getClass().getSimpleName() + "< " + factoryID + " >" + "(" + df.format(this.startX) + ","
				+ df.format(startY) + "," + df.format(endX) + "," + df.format(endY) +","+df.format(aX)+ ")" + "[ " + " " + " ]" + " ;\n";
		System.out.println(tostring);
		return tostring;
	}
}
