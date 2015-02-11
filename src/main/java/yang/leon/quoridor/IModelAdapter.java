package yang.leon.quoridor;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Specifies the methods that a game view can call to get information from or
 * change the game model indirectly. Any game controller implements this
 * interface.
 * 
 * @author Leon Yang
 * @see AbstractGameController
 */
public interface IModelAdapter {

    /**
     * Gets the update delegate returned by the game model's
     * {@link AbstractGameModel#getUpdateDelegate() getUpdateDelegate} method.
     * If the game model has not be specified, null is returned.
     * 
     * @return update delegate of the game model or null if the game model is
     *         null
     */
    public AbstractGameModel getUpdateDelegate();

    /**
     * Gets a <code>ArrayList</code> of <code>Location</code>s returned by the
     * game model's {@link AbstractGameModel#getCanMoveLocs(Location)
     * getCanMoveLocs} method.
     * 
     * @param loc
     *            the Location of a pawn
     * @Returns the Locations that the given pawn can move to
     */
    public ArrayList<Location> getCanMoveLocs(Location loc);

    /**
     * Gets the value returned by the game model's
     * {@link AbstractGameModel#isCanPutWall(Location, int) isCanPutWall}
     * method.
     * 
     * @param loc
     *            the Location to be check
     * @param direction
     *            the Location to be check
     * 
     * @return the Location to be check
     */
    public boolean isCanPutWall(Location loc, int direction);

    /**
     * Gets the value returned by the game model's
     * {@link AbstractGameModel#movePawn(Location) movePawn} method.
     * 
     * @param newLoc
     *            the <code>Location</code> the pawn is moving to
     * @return true if the current player wins after the movement
     */
    public boolean movePawn(Location newLoc);

    /**
     * Calls the game model's {@link AbstractGameModel#putWall(Location, int)
     * putWall} method.
     * 
     * @param loc
     *            the <code>Location</code> the wall is put to
     * @param direction
     *            the direction of the wall
     */
    public void putWall(Location loc, int direction);

    /**
     * Gets the value of the game model's
     * {@link AbstractGameModel#getPlayer(int) getPlayer} method.
     * 
     * @param playerIndex
     *            the index of the player
     * @return the specific <code>Player</code>
     */
    public Player getPlayer(int playerIndex);

    /**
     * Gets the value of the game model's
     * {@link AbstractGameModel#getNumPlayers() getNumPlayers} method.
     * 
     * @return the number of players in the game
     */
    public int getNumPlayers();

    /**
     * Calls the game model's {@link AbstractGameModel#nextPlayer() nextPlayer}
     * method. If the game view is not null, its view state will be changed
     * accordingly.
     */
    public void nextPlayer();

    /**
     * Gets the value of the game model's
     * {@link AbstractGameModel#getCurrPlayerIndex() getCurrPlayerIndex} method.
     * 
     * @return the index of the current Player
     */
    public int getCurrPlayerIndex();

    /**
     * Calls the game model's
     * {@link AbstractGameModel#update(Graphics, AbstractGameView) update}
     * method.
     * 
     * @param g
     *            the <code>Graphics</code> object to be drawn on
     * @param context
     *            the game view which provides the methods to draw the game
     *            state
     */
    public void update(Graphics g, AbstractGameView context);

    /**
     * Notifies all the views registered on this <code>IModelAdapter</code> to
     * update. This method should only be meaningful in network gaming.
     */
    public void updateAllViews();
}
