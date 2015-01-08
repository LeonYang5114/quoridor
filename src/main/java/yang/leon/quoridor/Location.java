package yang.leon.quoridor;

public class Location {

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
