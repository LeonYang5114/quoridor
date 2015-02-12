package yang.leon.quoridor.viewstate;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;

/**
 * Represents the state of the game view when the player is moving his pawn. All
 * the valid moves are shown and if any of them is left clicked, a move is made
 * and the current player's turn ends.
 * 
 * @author Leon Yang
 *
 */
public class MovingPawnState extends AbstractViewState {

    /**
     * 
     */
    private static final long serialVersionUID = 1277736410447138262L;

    /**
     * A list of <code>Location</code>s that the current player's pawn can move
     * to
     */
    private ArrayList<Location> canMoveLocs;

    /**
     * Constructs this view state with the given context.
     * 
     * @param context
     *            the game view context of this view state
     */
    public MovingPawnState(AbstractGameView context) {
	super(context);
	IModelAdapter adpt = getContext().getModelAdapter();
	canMoveLocs = adpt.getCanMoveLocs(adpt.getPlayer(
		adpt.getCurrPlayerIndex()).getPawnLoc());
	getContext().update();
    }

    /**
     * If the right mouse button is clicked, the state of the context changes to
     * {@link InitialState}. If the left mouse button is clicked and the
     * location of the mouse indicates a valid location to move the pawn, a move
     * is made and the current player ends his turn. If the current player
     * reaches this target edge after this move, he wins.
     */
    @Override
    public void mousePressed(MouseEvent e) {
	if (SwingUtilities.isRightMouseButton(e)) {
	    getContext().setViewState("InitialState");
	    getContext().update();
	    return;
	}
	if (!SwingUtilities.isLeftMouseButton(e))
	    return;
	Location loc = DefaultView.getSqrLocAtPoint(e.getPoint());
	IModelAdapter adpt = getContext().getModelAdapter();
	if (!canMoveLocs.contains(loc))
	    return;
	if (adpt.movePawn(loc)) {
	    getContext().win(adpt.getCurrPlayerIndex());
	    return;
	}
	adpt.nextPlayer();
    }

    /**
     * Does nothing.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Draws the valid locations to move the current player's pawn.
     */
    @Override
    public void update(Graphics g) {
	for (Location loc : canMoveLocs) {
	    Point p = DefaultView.getPointFromSqrLoc(loc);
	    g.drawImage(getContext().getImage("pawn.light"), p.x, p.y, null);
	}
    }

    public String toString() {
	return "Moving pawn state.";
    }
}
