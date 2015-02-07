package yang.leon.quoridor;

import java.awt.Graphics;
import java.util.ArrayList;

public interface IModelAdapter {
    
    public AbstractGameModel getUpdateDelegate();

    public ArrayList<Location> getCanMoveLocs(Location loc);

    public boolean isCanPutWall(Location loc, int direction);

    public boolean movePawn(Location newLoc);

    public void putWall(Location loc, int direction);

    public Player getPlayer(int playerIndex);

    public int getNumPlayers();

    public void nextPlayer();

    public int getCurrPlayerIndex();

    public void update(Graphics g, AbstractGameView context);

    public void updateAllViews();
}
