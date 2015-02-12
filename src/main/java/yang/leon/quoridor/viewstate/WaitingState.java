package yang.leon.quoridor.viewstate;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import yang.leon.quoridor.AbstractGameView;

/**
 * Represents the state of the game view when the player is waiting, usually for
 * another player in a network game. Under this state, no game behavior can be
 * done.
 * 
 * @author Leon Yang
 *
 */
public class WaitingState extends AbstractViewState {

    /**
     * 
     */
    private static final long serialVersionUID = -8034022749645426325L;

    /**
     * Constructs this view state with the given context.
     * 
     * @param context
     *            the game view context of this view state
     */
    public WaitingState(AbstractGameView context) {
	super(context);
	getContext().disableButtons();
    }

    /**
     * Does nothing.
     */
    @Override
    public void mousePressed(MouseEvent e) {
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
	return "Waiting state.";
    }

}
