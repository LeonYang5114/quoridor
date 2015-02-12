package yang.leon.quoridor.viewstate;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultModel;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;

/**
 * Represents the state of the game view when the player is holding a wall.
 * Under this state, the image of the wall will follow the player's mouse; the
 * wall will be put if the player left-clicks the grid panel and the putting
 * location is valid; the wall will change direction if right-click happens; if
 * the mouse hovers over a valid putting location without motion for a period of
 * time, a white wall put on that location would appear to indicate that the
 * location is valid.
 * 
 * @author Leon Yang
 *
 */
public class HoldingWallState extends AbstractViewState {

    /**
     * 
     */
    private static final long serialVersionUID = -3283918408413745061L;

    /**
     * The direction of the wall holding.
     */
    private int direction;

    /**
     * The <code>Point</code> of the mouse when the last <code>MouseEvent</code>
     * occurred.
     */
    private Point mouseLocation;

    /**
     * The delay that a white wall will appear if the hovering location is
     * valid.
     */
    private static final int DELAY = 350;

    /**
     * The timer that keeps track of the delay.
     */
    private Timer timer;

    /**
     * Flag indicates whether the delay has reached and should the white wall be
     * displayed.
     */
    private boolean showPuttingLoc;

    /**
     * Constructs this view state with the given context.
     * 
     * @param context
     *            the game view context of this view state
     */
    public HoldingWallState(AbstractGameView context) {
	super(context);
	direction = DefaultModel.HORIZONTAL_WALL;
	timer = new Timer(DELAY, new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		showPuttingLoc = true;
		getContext().repaint();
	    }
	});
	timer.setRepeats(false);
    }

    /**
     * If the right mouse button is pressed, the wall being held will change
     * direction. If the left mouse button is pressed, and the location of the
     * mouse indicates a valid location for putting a wall, the wall will be put
     * and the current player ends his turn.
     */
    @Override
    public void mousePressed(MouseEvent e) {
	if (SwingUtilities.isRightMouseButton(e)) {
	    direction = (direction == DefaultModel.HORIZONTAL_WALL) ? DefaultModel.VERTICAL_WALL
		    : DefaultModel.HORIZONTAL_WALL;
	    showPuttingLoc = false;
	    timer.restart();
	    getContext().repaint();
	    return;
	}
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = DefaultView.getCrsLocAtPoint(e.getPoint());
	IModelAdapter adpt = getContext().getModelAdapter();
	if (adpt.isCanPutWall(loc, direction)) {
	    adpt.putWall(loc, direction);
	    adpt.nextPlayer();
	}
    }

    /**
     * Updates the mouse location, reset the timer that delay the display of the
     * current putting location, and repaint the context.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
	mouseLocation = e.getPoint();
	showPuttingLoc = false;
	timer.restart();
	getContext().repaint();
    }

    /**
     * If the mouse is on the <code>Graphics</code> object, an image of the wall
     * is drawn following the mouse. The valid putting location is shown if
     * delay has been over.
     */
    @Override
    public void update(Graphics g) {
	if (mouseLocation == null)
	    return;
	Location loc = DefaultView.getCrsLocAtPoint(mouseLocation);
	if (showPuttingLoc
		&& getContext().getModelAdapter().isCanPutWall(loc, direction)) {
	    Point putting = DefaultView.getPointFromCrsLoc(loc);
	    getContext().drawWall(g, putting, direction, "light");
	}
	getContext().drawWall(g, mouseLocation, direction, null);
    }

    public String toString() {
	return "Holding wall state; direction: "
		+ ((direction == DefaultModel.HORIZONTAL_WALL) ? "horizontal."
			: "vertical.");
    }
}
