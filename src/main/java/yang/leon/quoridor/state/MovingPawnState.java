package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.Location;
import yang.leon.quoridor.ModelControlAdapter;
import yang.leon.quoridor.QuoridorView;

public class MovingPawnState extends ViewState {

    public MovingPawnState(QuoridorView context) {
	super(context);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	if (SwingUtilities.isRightMouseButton(e)) {
	    getContext().setViewState(new InitState(getContext()));
	    return;
	}
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = QuoridorView.getSqrLocAtPoint(e.getPoint());
	ModelControlAdapter adpt = getContext().getModelCtrlAdpt();
	ArrayList<Location> canMoveLocs = adpt.getCanMoveLocs(adpt.getPlayer(
		adpt.getCurrPlayerIndex()).getPawnLoc());
	if (!canMoveLocs.contains(loc))
	    return;
	if (adpt.movePawn(loc)) {
	    getContext().win(adpt.getCurrPlayerIndex());
	    return;
	}
	adpt.nextPlayer(getContext());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void update(Graphics g) {
	drawCurrPlayer(g);
	ModelControlAdapter adpt = getContext().getModelCtrlAdpt();
	Location pawnLoc = adpt.getPlayer(adpt.getCurrPlayerIndex())
		.getPawnLoc();
	ArrayList<Location> canMoveLocs = adpt.getCanMoveLocs(pawnLoc);
	for (Location loc : canMoveLocs) {
	    Point p = QuoridorView.getPointFromSqrLoc(loc);
	    g.drawImage(getContext().getImage("pawn.light"), p.x, p.y, null);
	}
    }

    public String toString() {
	return "Moving pawn state.";
    }
}
