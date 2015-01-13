package yang.leon.quoridor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import yang.leon.quoridor.state.IViewState;

public abstract class AbstractView extends JPanel {
    public static final int GRID_WIDTH = 450, GRID_HEIGHT = 450;

    private IModelAdapter modelAdpt;

    private IViewState viewState;

    public abstract Image getImage(String key);

    public static Location getSqrLocAtPoint(Point point) {
	int squareHeight = GRID_HEIGHT / DefaultModel.HEIGHT;
	int squareWidth = GRID_WIDTH / DefaultModel.WIDTH;
	return new Location(point.y / squareHeight, point.x / squareWidth);
    }

    public static Point getPointFromSqrLoc(Location loc) {
	int squareHeight = GRID_HEIGHT / DefaultModel.HEIGHT;
	int squareWidth = GRID_WIDTH / DefaultModel.WIDTH;
	return new Point(loc.getCol() * squareWidth, loc.getRow()
		* squareHeight);
    }

    public static Location getCrsLocAtPoint(Point point) {
	int squareHeight = GRID_HEIGHT / DefaultModel.HEIGHT;
	int squareWidth = GRID_WIDTH / DefaultModel.WIDTH;
	int row = (point.y - squareHeight / 2) / squareHeight;
	row = Math.max(Math.min(row, 7), 0);
	int col = (point.x - squareWidth / 2) / squareWidth;
	col = Math.max(Math.min(col, 7), 0);
	return new Location(row, col);
    }

    public static Point getPointFromCrsLoc(Location loc) {
	int squareHeight = GRID_HEIGHT / DefaultModel.HEIGHT;
	int squareWidth = GRID_WIDTH / DefaultModel.WIDTH;
	return new Point((loc.getCol() + 1) * squareWidth, (loc.getRow() + 1)
		* squareHeight);
    }

    public IModelAdapter getModelAdapter() {
	return modelAdpt;
    }

    public void setModelAdapter(IModelAdapter modelAdpt) {
	this.modelAdpt = modelAdpt;
	System.out.println("Model adapter set.");
	System.out.println(modelAdpt == null);
	if (modelAdpt != null)
	    SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    resetGUI();
		}
	    });
    }

    public IViewState getViewState() {
	return viewState;
    }

    public void setViewState(IViewState aState) {
	this.viewState = aState;
	update();
    }

    public abstract void resetGUI();

    public abstract void update();

    public abstract JPanel getGridPanel();

    public abstract void drawWall(Graphics g, Point p, int direction, String arg);
    
    public abstract void drawBackground(Graphics g);
    
    public abstract void drawPawn(Graphics g, Point p);

    public abstract void win(int currPlayerIndex);

    public abstract void disableButtons();
}
