package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.AbstractView;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;

public class InitialState extends IViewState {

    /**
     * 
     */
    private static final long serialVersionUID = 2143097583796965991L;

    public InitialState(AbstractView context) {
	super(context);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = AbstractView.getSqrLocAtPoint(e.getPoint());
	IModelAdapter adpt = getContext().getModelAdapter();
	try {
	    if (adpt.getPlayer(adpt.getCurrPlayerIndex()).getPawnLoc().equals(loc)) {
	        getContext().setViewState(new MovingPawnState(getContext()));
	    }
	} catch (RemoteException e1) {
	    e1.printStackTrace();
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
