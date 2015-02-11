package yang.leon.quoridor;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A model stores the states of the game and also provides methods to change or
 * retrieve the game state.
 * 
 * @author Leon Yang
 *
 */
public abstract class AbstractGameModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5485403923299219883L;

    /**
     * Indicates no wall exists on a <code>Location</code>. Used as direction.
     */
    public static final int NO_WALL = 0;

    /**
     * Direction of a wall.
     */
    public static final int HORIZONTAL_WALL = 1, VERTICAL_WALL = 2;

    /**
     * Gets the {@link IViewAdapter IViewAdapter} of this model, which provides
     * methods to control the game view indirectly.
     * 
     * @return the view adapter
     */
    public abstract IViewAdapter getViewAdapter();

    /**
     * Sets the view adapter of this model to be the given {@link IViewAdapter
     * IViewAdapter}.
     * 
     * @param viewAdpt
     *            the view adapter to be set
     */
    public abstract void setViewAdapter(IViewAdapter viewAdpt);

    /**
     * Gets the current update delegate of this model. An update delegate is an
     * <code>AbstractGameModel</code> with only information for updating the
     * game view.
     * 
     * @return the current update delegate
     */
    public abstract AbstractGameModel getUpdateDelegate();

    /**
     * Sets the current update delegate as the given <code>updateDelegate</code>
     * . An update delegate is an <code>AbstractGameModel</code> with only
     * information for updating the game view. This method is called every time
     * the game state has changed so that the game view needs update.
     * 
     * @param updateDelegate
     *            the update delegate to be set
     */
    public abstract void setUpdateDelegate(AbstractGameModel updateDelegate);

    /**
     * Gets an <code>ArrayList</code> of the {@link Location Location}s that a
     * pawn on the given <code>Location</code> can move to.
     * <p>
     * The <code>Location</code>s that a pawn can move to is the union of all
     * the adjacent <code>Location</code>s not blocked by any wall and
     * <code>Location</code>s that any adjacent pawn can move to.
     * 
     * @param loc
     *            the <code>Location</code> of a pawn
     * @return the <code>Location</code>s that the given pawn can move to
     */
    public abstract ArrayList<Location> getCanMoveLocs(Location loc);

    /**
     * Gets whether a wall with given <code>direction</code> can be put on the
     * given {@link Location Location}.
     * <p>
     * If the given <code>Location</code> already has a wall, <code>false</code>
     * is returned. If any pawn is totally blocked (cannot reach its goal edge)
     * by this wall, <code>false</code> is returned.
     * 
     * @param loc
     *            the <code>Location</code> to be check
     * @param direction
     *            the direction of the wall
     * @return <code>true</code> if a specific wall can be put
     * @see #VERTICAL_WALL
     * @see #HORIZONTAL_WALL
     */
    public abstract boolean isCanPutWall(Location loc, int direction);

    /**
     * Moves the pawn of the current player to the specific {@link Location
     * Location}. Gets true if the current player wins after the movement.
     * 
     * @param newLoc
     *            the <code>Location</code> the pawn is moving to
     * @return true if the current player wins after the movement
     * @see #getCurrPlayerIndex()
     */
    public abstract boolean movePawn(Location newLoc);

    /**
     * Puts a wall with given direction to the given {@link Location Location}.
     * 
     * @param loc
     *            the <code>Location</code> the wall is put to
     * @param direction
     *            the direction of the wall
     * @see #VERTICAL_WALL
     * @see #HORIZONTAL_WALL
     * @see #isCanPutWall(Location, int)
     */
    public abstract void putWall(Location loc, int direction);

    /**
     * Gets the {@link Player Player} with the given index.
     * 
     * @param playerIndex
     *            the index of the player
     * @return the specific <code>Player</code>
     */
    public abstract Player getPlayer(int playerIndex);

    /**
     * Gets the number of players in the game.
     * 
     * @return the number of players in the game
     */
    public abstract int getNumPlayers();

    /**
     * Notifies this model that the last <code>Player</code> has finished his
     * turn and it is next <code>Player</code>'s turn.
     */
    public abstract void nextPlayer();

    /**
     * Gets the index of the current <code>Player</code>.
     * 
     * @return the index of the current <code>Player</code>
     */
    public abstract int getCurrPlayerIndex();

    /**
     * Draws the game state on the given <code>Graphics</code> object using the
     * methods provided by the given context.
     * 
     * @param g
     *            the <code>Graphics</code> object to be drawn on
     * @param context
     *            the game view which provides the methods to draw the game
     *            state
     */
    public abstract void update(Graphics g, AbstractGameView context);

}
