package yang.leon.quoridor.state;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.IModelAdapter;
import yang.leon.quoridor.Location;

/**
 * Represents the initial state of the game view. If the pawn of the current
 * player is right clicked, it turns into {@link MovingPawnState}.
 * 
 * @author Leon Yang
 *
 */
public class InitialState extends AbstractViewState {

    /**
     * 
     */
    private static final long serialVersionUID = 2143097583796965991L;

    /**
     * Constructs this view state with the given context.
     * 
     * @param context
     *            the game view context of this view state
     */
    public InitialState(AbstractGameView context) {
	super(context);
	getContext().enableButtons();
    }

    /**
     * If the pawn of the current player is right clicked, the context's view
     * state is changed to {@link MovingPawnState}.
     */
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

    /**
     * Does nothing.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Does nothing.
     */
    @Override
    public void update(Graphics g) {
    }

    public String toString() {
	return "Initial state.";
    }
}
