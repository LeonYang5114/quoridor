package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.rmi.RemoteException;

import yang.leon.quoridor.AbstractView;
import yang.leon.quoridor.DefaultModel;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;
import yang.leon.quoridor.Player;

public abstract class IViewState implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 166356488156092621L;
    private AbstractView context;

    public IViewState(AbstractView context) {
	this.context = context;
    }

    public abstract void mousePressed(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);

    public AbstractView getContext() {
	return context;
    }

    public abstract void update(Graphics g);

    public void drawCurrPlayer(Graphics g) {
	IModelAdapter adpt = getContext().getModelAdapter();
	Player player = null;
	try {
	    player = adpt.getPlayer(adpt.getCurrPlayerIndex());
	} catch (RemoteException e) {
	    e.printStackTrace();
	}
	if (player == null)
	    return;
	switch (player.getTargetEdge()) {
	case DefaultModel.NORTH_EDGE:
	    g.drawImage(getContext().getImage("target.edge.horizontal"), 0, 0,
		    null);
	    break;
	case DefaultModel.WEST_EDGE:
	    g.drawImage(getContext().getImage("target.edge.vertical"), 0, 0,
		    null);
	    break;
	case DefaultModel.SOUTH_EDGE:
	    g.drawImage(getContext().getImage("target.edge.horizontal"), 0,
		    404, null);
	    break;
	case DefaultModel.EAST_EDGE:
	    g.drawImage(getContext().getImage("target.edge.vertical"), 404, 0,
		    null);
	    break;
	default:
	    break;
	}
	Location pawnLoc = player.getPawnLoc();
	Point pawnPoint = DefaultView.getPointFromSqrLoc(pawnLoc);
	g.drawImage(getContext().getImage("current.pawn"), pawnPoint.x,
		pawnPoint.y, null);
    }
    
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	return this.getClass().equals(obj.getClass());
    }
}
