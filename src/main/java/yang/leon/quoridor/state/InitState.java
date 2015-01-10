package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.Location;
import yang.leon.quoridor.ModelControlAdapter;
import yang.leon.quoridor.QuoridorView;

public class InitState extends ViewState {

    public InitState(QuoridorView context) {
	super(context);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = QuoridorView.getSqrLocAtPoint(e.getPoint());
	ModelControlAdapter adpt = getContext().getModelCtrlAdpt();
	if (adpt.getPlayer(adpt.getCurrPlayerIndex()).getPawnLoc().equals(loc))
	    getContext().setViewState(new MovingPawnState(getContext()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void update(Graphics g) {
	drawCurrPlayer(g);
    }

    public String toString() {
	return "Initial state.";
    }
}
