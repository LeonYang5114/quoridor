package yang.leon.quoridor.viewstate;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import yang.leon.quoridor.AbstractGameView;

/**
 * Represents the state of the game view, the change of which changes the users
 * interface, but might not change the game state. It is constructed with an
 * {@link AbstractGameView} as its context. It accepts the
 * <code>MouseEvent</code>s from the context, and change the state of it and/or
 * invoke methods of the context's {@link IModelAdapter} to further change the
 * game state.
 * 
 * @author Leon Yang
 *
 */
public abstract class AbstractViewState implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 166356488156092621L;

    /**
     * Game view context of this view state.
     */
    private AbstractGameView context;

    /**
     * Constructs an <code>AbstractViewState</code> with the given context.
     * 
     * @param context
     *            the game view context of this view state
     */
    public AbstractViewState(AbstractGameView context) {
	this.context = context;
    }

    /**
     * Accepts and processes the <code>MouseEvent</code> generated when the
     * mouse is pressed on the context's grid panel.
     * 
     * @param e
     */
    public abstract void mousePressed(MouseEvent e);

    /**
     * Accepts and processes the <code>MouseEvent</code> generated when the
     * mouse moves on the context's grid panel.
     * 
     * @param e
     */
    public abstract void mouseMoved(MouseEvent e);

    /**
     * Gets the context of this view state.
     * 
     * @return the context of this view state
     */
    public AbstractGameView getContext() {
	return context;
    }

    /**
     * Updates a given <code>Graphics</code> object according to the specific
     * state. The <code>Graphics</code> object should come from the grid panel
     * of the context.
     * 
     * @param g
     *            the <code>Graphics</code> object to be drawn on
     */
    public abstract void update(Graphics g);
}
