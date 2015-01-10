package yang.leon.quoridor;

import java.util.ArrayList;

public interface ModelControlAdapter {
    
    public ArrayList<Location> getCanMoveLocs(Location loc);
    
    public boolean isCanPutWall(Location loc, int direction);
    
    public boolean movePawn(Location newLoc);
    
    public void putWall(Location loc, int direction);
    
    public Player getPlayer(int playerIndex);
    
    public int getNumPlayers();
    
    public int getCurrPlayerIndex();
    
    public void nextPlayer(QuoridorView context);
}
