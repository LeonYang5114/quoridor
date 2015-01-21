package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;
import java.rmi.RemoteException;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;

public class WonState extends WaitingState {

    /**
     * 
     */
    private static final long serialVersionUID = -8988859134229907152L;

    public WonState(AbstractGameView context) {
	super(context);
    }

    public void update(Graphics g) {
	IModelAdapter adpt = getContext().getModelAdapter();
	Location pawnLoc = null;
	pawnLoc = adpt.getPlayer(adpt.getCurrPlayerIndex())
		.getPawnLoc();
	Point pawnPoint = DefaultView.getPointFromSqrLoc(pawnLoc);
	g.drawImage(getContext().getImage("winner"), pawnPoint.x, pawnPoint.y,
		null);
    }

    public String toString() {
	return "Won state.";
    }

}
