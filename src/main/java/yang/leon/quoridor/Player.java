package yang.leon.quoridor;

import java.awt.Graphics;

public class Player {

    private int numWalls;

    private Location pawnLoc;

    private static final Location[] INITIAL_LOCATIONS = { new Location(8, 4),
	    new Location(0, 4), new Location(4, 0), new Location(4, 8) };
    
    private int targetEdge;

    public Player(int numWalls, int index) {
	this.setNumWalls(numWalls);
	this.setPawnLoc(INITIAL_LOCATIONS[index]);
	if (getPawnLoc().getRow() == 0)
	    targetEdge = QuoridorModel.SOUTH_EDGE;
	else if (getPawnLoc().getRow() == QuoridorModel.HEIGHT - 1)
	    targetEdge = QuoridorModel.NORTH_EDGE;
	else if (getPawnLoc().getCol() == 0)
	    targetEdge = QuoridorModel.EAST_EDGE;
	else if (getPawnLoc().getCol() == QuoridorModel.WIDTH - 1)
	    targetEdge = QuoridorModel.WEST_EDGE;
    }

    public int getNumWalls() {
	return numWalls;
    }

    public void setNumWalls(int numWalls) {
	this.numWalls = numWalls;
    }
    
    public int getTargetEdge() {
	return targetEdge;
    }

    public Location getPawnLoc() {
	return pawnLoc;
    }

    public void setPawnLoc(Location pawnLoc) {
	this.pawnLoc = pawnLoc;
    }
    
    public void paint(Graphics g) {
	
    }

}
