package yang.leon.quoridor;

import java.io.Serializable;

/**
 * Representation of the game player, which records the pawn location, the
 * number of walls left, and the goal edge.
 * 
 * @author Leon Yang
 *
 */
public class Player implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1156188417186445415L;

    /**
     * Total number of walls a player can use.
     */
    private static final int TOTAL_WALLS = 20;

    /**
     * Current number of walls.
     */
    private int numWalls;

    /**
     * The <code>Location</code> of the player's pawn.
     */
    private Location pawnLoc;

    /**
     * The initial <code>Location</code> on the South edge.
     */
    private static final Location SOUTH = new Location(8, 4);

    /**
     * The initial <code>Location</code> on the North edge.
     */
    private static final Location NORTH = new Location(0, 4);

    /**
     * The initial <code>Location</code> on the West edge.
     */
    private static final Location WEST = new Location(4, 0);

    /**
     * The initial <code>Location</code> on the East edge.
     */
    private static final Location EAST = new Location(4, 8);

    /**
     * The initial <code>Location</code>s when different number of players are
     * in the game.
     */
    private static final Location[][] INITIAL_LOCATIONS = { { SOUTH },
	    { SOUTH, NORTH }, { SOUTH, EAST, NORTH },
	    { SOUTH, EAST, NORTH, WEST } };

    /**
     * The edge which the player will win as soon as he reaches.
     */
    private int targetEdge;

    /**
     * Construct a player given the number of players in the game and the index
     * of this player.
     * 
     * @param numPlayers
     *            number of players in the game
     * @param index
     *            index of this player
     */
    public Player(int numPlayers, int index) {
	this.setNumWalls(TOTAL_WALLS / numPlayers);
	this.setPawnLoc(INITIAL_LOCATIONS[numPlayers - 1][index]);
	if (getPawnLoc().getRow() == 0)
	    targetEdge = AbstractGameModel.SOUTH_EDGE;
	else if (getPawnLoc().getRow() == DefaultModel.HEIGHT - 1)
	    targetEdge = AbstractGameModel.NORTH_EDGE;
	else if (getPawnLoc().getCol() == 0)
	    targetEdge = AbstractGameModel.EAST_EDGE;
	else if (getPawnLoc().getCol() == DefaultModel.WIDTH - 1)
	    targetEdge = AbstractGameModel.WEST_EDGE;
    }

    /**
     * Gets the number of walls the user has left.
     * 
     * @return the number of walls the user has left
     */
    public int getNumWalls() {
	return numWalls;
    }

    /**
     * Sets the number of walls to be the given number.
     * 
     * @param numWalls
     *            the number of walls to be set
     */
    public void setNumWalls(int numWalls) {
	this.numWalls = numWalls;
    }

    /**
     * Gets the target edge of this player.
     * 
     * @return the target edge of this player
     * @see AbstractGameModel#EAST_EDGE EAST_EDGE
     * @see AbstractGameModel#NORTH_EDGE NORTH_EDGE
     * @see AbstractGameModel#SOUTH_EDGE SOUTH_EDGE
     * @see AbstractGameModel#WEST_EDGE WEST_EDGE
     */
    public int getTargetEdge() {
	return targetEdge;
    }

    /**
     * Gets the {@link Location} of this player's pawn.
     * 
     * @return the <code>Location</code> of this player's pawn
     */
    public Location getPawnLoc() {
	return pawnLoc;
    }

    /**
     * Sets the {@link Location} of this player's pawn to be the given
     * <code>Location</code>.
     * 
     * @param pawnLoc
     *            the new <code>Location</code> to be set
     */
    public void setPawnLoc(Location pawnLoc) {
	this.pawnLoc = pawnLoc;
    }

}
