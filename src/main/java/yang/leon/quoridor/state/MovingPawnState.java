package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;

public class MovingPawnState extends IViewState {

    /**
     * 
     */
    private static final long serialVersionUID = 1277736410447138262L;

    public MovingPawnState(AbstractGameView context) {
	super(context);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	if (SwingUtilities.isRightMouseButton(e)) {
	    getContext().setViewState(new InitialState(getContext()));
	    return;
	}
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = DefaultView.getSqrLocAtPoint(e.getPoint());
	IModelAdapter adpt = getContext().getModelAdapter();
	ArrayList<Location> canMoveLocs = null;
	canMoveLocs = adpt.getCanMoveLocs(adpt.getPlayer(
		adpt.getCurrPlayerIndex()).getPawnLoc());
	if (canMoveLocs == null || !canMoveLocs.contains(loc))
	    return;
	if (adpt.movePawn(loc)) {
	    getContext().win(adpt.getCurrPlayerIndex());
	    return;
	}
	try {
	    String name = "yang.leon.quoridor.state." + adpt.nextPlayer();
	    IViewState newState = (IViewState) Class.forName(name)
		    .getConstructor(AbstractGameView.class)
		    .newInstance(getContext());
	    getContext().setViewState(newState);
	} catch (Exception e1) {
	    e1.printStackTrace();
	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void update(Graphics g) {
	IModelAdapter adpt = getContext().getModelAdapter();
	Location pawnLoc = null;
	pawnLoc = adpt.getPlayer(adpt.getCurrPlayerIndex()).getPawnLoc();
	ArrayList<Location> canMoveLocs = null;
	canMoveLocs = adpt.getCanMoveLocs(pawnLoc);
	for (Location loc : canMoveLocs) {
	    Point p = DefaultView.getPointFromSqrLoc(loc);
	    g.drawImage(getContext().getImage("pawn.light"), p.x, p.y, null);
	}
    }

    public String toString() {
	return "Moving pawn state.";
    }
}
