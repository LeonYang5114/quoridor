package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import yang.leon.quoridor.Location;
import yang.leon.quoridor.ModelControlAdapter;
import yang.leon.quoridor.QuoridorModel;
import yang.leon.quoridor.QuoridorView;

public class HoldingWallState extends ViewState {

    private int direction;
    private Point mouseLocation;
    private static final int DELAY = 350;
    private Timer timer;
    private boolean showPuttingLoc;

    public HoldingWallState(QuoridorView context) {
	super(context);
	direction = QuoridorModel.HORIZONTAL_WALL;
	timer = new Timer(DELAY, new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		showPuttingLoc = true;
		getContext().repaint();
	    }
	});
	timer.setRepeats(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	if (SwingUtilities.isRightMouseButton(e)) {
	    direction = (direction == QuoridorModel.HORIZONTAL_WALL) ? QuoridorModel.VERTICAL_WALL
		    : QuoridorModel.HORIZONTAL_WALL;
	    showPuttingLoc = false;
	    timer.restart();
	    getContext().repaint();
	    return;
	}
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = QuoridorView.getCrsLocAtPoint(e.getPoint());
	ModelControlAdapter adpt = getContext().getModelCtrlAdpt();
	if (adpt.isCanPutWall(loc, direction)) {
	    adpt.putWall(loc, direction);
	    adpt.nextPlayer(getContext());
	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	mouseLocation = e.getPoint();
	showPuttingLoc = false;
	timer.restart();
	getContext().update();
    }

    @Override
    public void update(Graphics g) {
	drawCurrPlayer(g);
	if (mouseLocation == null)
	    return;
	Location loc = QuoridorView.getCrsLocAtPoint(mouseLocation);
	if (showPuttingLoc
		&& getContext().getModelCtrlAdpt().isCanPutWall(loc, direction)) {
	    Point putting = QuoridorView.getPointFromCrsLoc(loc);
	    getContext().drawWall(g, putting, direction, "light");
	}
	getContext().drawWall(g, mouseLocation, direction, null);
    }

    public String toString() {
	return "Holding wall state; direction: "
		+ ((direction == QuoridorModel.HORIZONTAL_WALL) ? "horizontal."
			: "vertical.");
    }
}
