package yang.leon.quoridor;

import java.io.Serializable;

public class Location implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 55019413903442916L;
    private int row, col;

    public Location(int row, int col) {
	this.row = row;
	this.col = col;
    }

    public int getRow() {
	return row;
    }

    public void setRow(int row) {
	this.row = row;
    }

    public int getCol() {
	return col;
    }

    public void setCol(int col) {
	this.col = col;
    }

    public Location toRotatedLoc(int playerIndex) {
	int index = playerIndex % 4;
	switch (index) {
	case 0:
	    return new Location(getRow(), getCol());
	case 1:
	    return new Location(8 - getCol(), getRow());
	case 2:
	    return new Location(8 - getRow(), getCol());
	case 3:
	    return new Location(getCol(), 8 - getRow());
	default:
	    return null;
	}
    }

    public String toString() {
	return "Row: " + getRow() + ", Col: " + getCol();
    }

    public boolean equals(Object o) {
	if (o == null)
	    return false;
	if (o instanceof Location) {
	    Location loc = (Location) o;
	    return loc.getRow() == getRow() && loc.getCol() == getCol();
	}
	return false;
    }
}
