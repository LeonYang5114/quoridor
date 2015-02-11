package yang.leon.quoridor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;

import javax.swing.JPanel;

import yang.leon.quoridor.state.IViewState;

/**
 * A view that displays the game states and interact with the user. Inputs are
 * accepted here and transferred to the controller to be processed.
 * 
 * @author Leon Yang
 *
 */
public abstract class AbstractGameView extends JPanel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6068677166330116230L;

    /**
     * The dimension in pixels of the chess grid.
     */
    public static final int GRID_WIDTH = 450, GRID_HEIGHT = 450;

    /**
     * Gets the {@link Location Location} of a square given a specific
     * <code>Point</code> on the gird. This <code>Location</code> should be
     * occupied by a pawn.
     * 
     * @param point
     *            the <code>Point</code> that indicates the
     *            <code>Location</code>
     * @return the <code>Location</code> indicated by the given
     *         <code>Point</code>
     */
    public static Location getSqrLocAtPoint(Point point) {
	int squareHeight = GRID_HEIGHT / DefaultModel.HEIGHT;
	int squareWidth = GRID_WIDTH / DefaultModel.WIDTH;
	return new Location(point.y / squareHeight, point.x / squareWidth);
    }

    /**
     * Gets the <code>Point</code> on the grid at the top left corner of a
     * square with the given {@link Location Location}.
     * 
     * @param loc
     *            the <code>Location</code> that needs to find the
     *            <code>Point</code>
     * @return the <code>Point</code> on the grid at the top left corner of the
     *         square with the given <code>Location</code>
     */
    public static Point getPointFromSqrLoc(Location loc) {
	int squareHeight = GRID_HEIGHT / DefaultModel.HEIGHT;
	int squareWidth = GRID_WIDTH / DefaultModel.WIDTH;
	return new Point(loc.getCol() * squareWidth, loc.getRow()
		* squareHeight);
    }

    /**
     * Gets the {@link Location Location} of a crossing given a specific
     * <code>Point</code> on the gird. This <code>Location</code> should be
     * occupied by a wall.
     * 
     * @param point
     *            the <code>Point</code> that indicates the
     *            <code>Location</code>
     * @return the <code>Location</code> indicated by the given
     *         <code>Point</code>
     */
    public static Location getCrsLocAtPoint(Point point) {
	int squareHeight = GRID_HEIGHT / DefaultModel.HEIGHT;
	int squareWidth = GRID_WIDTH / DefaultModel.WIDTH;
	int row = (point.y - squareHeight / 2) / squareHeight;
	row = Math.max(Math.min(row, 7), 0);
	int col = (point.x - squareWidth / 2) / squareWidth;
	col = Math.max(Math.min(col, 7), 0);
	return new Location(row, col);
    }

    /**
     * Gets the <code>Point</code> on the grid at the top left corner of a
     * crossing with the given {@link Location Location}.
     * 
     * @param loc
     *            the <code>Location</code> that needs to find the
     *            <code>Point</code>
     * @return the <code>Point</code> on the grid at the top left corner of the
     *         crossing with the given <code>Location</code>
     */
    public static Point getPointFromCrsLoc(Location loc) {
	int squareHeight = GRID_HEIGHT / DefaultModel.HEIGHT;
	int squareWidth = GRID_WIDTH / DefaultModel.WIDTH;
	return new Point((loc.getCol() + 1) * squareWidth, (loc.getRow() + 1)
		* squareHeight);
    }

    /**
     * Gets an <code>Image</code> with the specific key.
     * 
     * @param key
     *            key of the <code>Image</code>
     * @return the specific <code>Image</code>
     */
    public abstract Image getImage(String key);

    /**
     * Gets the {@link IModelAdapter IModelAdapter} of this view, which provides
     * methods to control the game model indirectly.
     * 
     * @return the model adapter
     */
    public abstract IModelAdapter getModelAdapter();

    /**
     * Sets the model adapter of this view to be the given {@link IModelAdapter
     * IModelAdapter}.
     * 
     * @param modelAdpt
     *            the model adapter to be set
     */
    public abstract void setModelAdapter(IModelAdapter modelAdpt);

    /**
     * Gets the current {@link IViewState IViewState} of this game view.
     * 
     * @return the current <code>IViewState</code>
     */
    public abstract IViewState getViewState();

    /**
     * Sets the view state of this game view to be a {@link IViewState
     * IViewState} constructed dynamically with the name given.
     * 
     * @param stateName
     *            the class name of a specific <code>IViewState</code> to be
     *            constructed and set
     */
    public abstract void setViewState(String stateName);

    /**
     * Resets the GUI. Also used for initialization.
     */
    public abstract void resetGUI();

    /**
     * Updates the game display using information from the game model.
     */
    public abstract void update();

    /**
     * Gets the <code>JPanel</code> containing the chess grid.
     * 
     * @return the <code>JPanel</code> containing the chess grid
     */
    public abstract JPanel getGridPanel();

    /**
     * Draws a wall on the specific <code>Graphics</code> object with given
     * direction on a <code>Location</code> indicating by the given
     * <code>Point</code>. The parameter <code>arg</code> specifies the details
     * of the image of the wall.
     * 
     * @param g
     *            the <code>Graphics</code> object to be drawn on
     * @param p
     *            the <code>Point</code> that indicates the
     *            <code>Location</code> of the wall
     * @param direction
     *            the direction of the wall
     * @param arg
     *            the <code>String</code> that specifies the image details
     */
    public abstract void drawWall(Graphics g, Point p, int direction, String arg);

    /**
     * Draws the background on the given <code>Graphics</code> object
     * 
     * @param g
     *            the <code>Graphics</code> object to be drawn on
     */
    public abstract void drawBackground(Graphics g);

    /**
     * Draws a pawn on the specific <code>Graphics</code> object on a
     * <code>Location</code> indicating by the given <code>Point</code>.
     * 
     * @param g
     *            the <code>Graphics</code> object to be drawn on
     * @param p
     *            the <code>Point</code> that indicates the
     *            <code>Location</code> of the pawn
     */
    public abstract void drawPawn(Graphics g, Point p);

    /**
     * Notifies this game view that the player with given index wins the game.
     * Message should be prompted.
     * 
     * @param winnerIndex
     *            the index of the winner
     */
    public abstract void win(int winnerIndex);

    /**
     * Disables the game-related buttons related. This method should be called
     * when the player ends his turn in a network game.
     * 
     * @see yang.leon.quoridor.state.WaitingState WaitingState
     */
    public abstract void disableButtons();

    /**
     * Enables the game-related buttons. This method should be called when the
     * player starts his turn in a network game.
     * 
     * @see yang.leon.quoridor.state.WaitingState WaitingState
     */
    public abstract void enableButtons();
}
