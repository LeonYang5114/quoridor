package yang.leon.quoridor;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5485403923299219883L;

    public static final int WIDTH = 9, HEIGHT = 9;

    public static final int NOT_ON_EDGE = -1, NORTH_EDGE = 0, EAST_EDGE = 1,
	    SOUTH_EDGE = 2, WEST_EDGE = 3;

    public static final int NO_WALL = 0, HORIZONTAL_WALL = 1,
	    VERTICAL_WALL = 2;

    public static final int TOTAL_WALLS = 20;

    private transient IViewAdapter viewAdpt;

    private AbstractModel updateDelegate;

    private boolean hasDoneWithUpdate;
    public static final int UPDATE_TIME_OUT = 5000;

    public IViewAdapter getViewAdapter() {
	return viewAdpt;
    }

    public void setViewAdapter(IViewAdapter viewAdpt) {
	this.viewAdpt = viewAdpt;
    }

    public AbstractModel getUpdateDelegate() {
	return updateDelegate;
    }

    public void setUpdateDelegate(AbstractModel updateDelegate) {
	this.updateDelegate = updateDelegate;
    }

    protected boolean hasDoneWithUpdate() {
	return hasDoneWithUpdate;
    }

    public void doneWithUpdateNotify() {
	hasDoneWithUpdate = true;
    }

    public void resetDoneWithUpdate() {
	hasDoneWithUpdate = false;
    }

    public abstract ArrayList<Location> getCanMoveLocs(Location loc);

    public abstract boolean isCanPutWall(Location loc, int direction);

    public abstract boolean movePawn(Location newLoc);

    public abstract void putWall(Location loc, int direction);

    public abstract Player getPlayer(int playerIndex);

    public abstract int getNumPlayers();

    public abstract String nextPlayer();

    public abstract void requestWaitForUpdate();

    public abstract int getCurrPlayerIndex();

    public abstract void update(Graphics g, AbstractView context);

}
