package yang.leon.quoridor;

import java.util.ArrayList;

public interface ModelControlAdapter {
    
    public ArrayList<Location> getCanMoveLocs(Location loc);
    
    public boolean isCanPutWall(Location loc, int direction);
    
    public void movePawn(Player p, Location newLoc);
    
    public void putWall(Player p, Location loc, int direction);
    
    public Player getPlayer(int playerIndex);
    
    public int getNumPlayers();
}
