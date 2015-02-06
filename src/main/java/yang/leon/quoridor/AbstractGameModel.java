package yang.leon.quoridor;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractGameModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5485403923299219883L;

    public abstract IViewAdapter getViewAdapter();

    public abstract void setViewAdapter(IViewAdapter viewAdpt);

    public abstract AbstractGameModel getUpdateDelegate();

    public abstract void setUpdateDelegate(AbstractGameModel updateDelegate);

    public abstract ArrayList<Location> getCanMoveLocs(Location loc);

    public abstract boolean isCanPutWall(Location loc, int direction);

    public abstract boolean movePawn(Location newLoc);

    public abstract void putWall(Location loc, int direction);

    public abstract Player getPlayer(int playerIndex);

    public abstract int getNumPlayers();

    public abstract void nextPlayer();

    public abstract int getCurrPlayerIndex();

    public abstract void update(Graphics g, AbstractGameView context);

}
