package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;

public class InitialState extends IViewState {

    /**
     * 
     */
    private static final long serialVersionUID = 2143097583796965991L;

    public InitialState(AbstractGameView context) {
	super(context);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = AbstractGameView.getSqrLocAtPoint(e.getPoint());
	IModelAdapter adpt = getContext().getModelAdapter();
	if (adpt.getPlayer(adpt.getCurrPlayerIndex()).getPawnLoc().equals(loc)) {
	    getContext().setViewState("MovingPawnState");
	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void update(Graphics g) {
    }

    public String toString() {
	return "Initial state.";
    }
}
