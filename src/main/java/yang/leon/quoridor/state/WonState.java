package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.Point;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;

/**
 * Represents the state of the game view that the current player wins the game.
 * @author Leon Yang
 *
 */
public class WonState extends WaitingState {

    /**
     * 
     */
    private static final long serialVersionUID = -8988859134229907152L;
    
    /**
     * Constructs this view state with the given context.
     * 
     * @param context
     *            the game view context of this view state
     */
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
