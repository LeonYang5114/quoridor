package yang.leon.quoridor;

import java.awt.Graphics;
import java.util.ArrayList;

class DelegateModel extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = -6704532575539021422L;

    @Override
    public ArrayList<Location> getCanMoveLocs(Location loc) {
	return null;
    }

    @Override
    public boolean isCanPutWall(Location loc, int direction) {
	return false;
    }

    @Override
    public boolean movePawn(Location newLoc) {
	return false;
    }

    @Override
    public void putWall(Location loc, int direction) {
    }

    @Override
    public Player getPlayer(int playerIndex) {
	return null;
    }

    @Override
    public int getNumPlayers() {
	return 0;
    }

    @Override
    public String nextPlayer() {
	return null;
    }

    @Override
    public int getCurrPlayerIndex() {
	return 0;
    }

    @Override
    public void update(Graphics g, AbstractView context) {
    }

    @Override
    public void requestWaitForUpdate() {	
    }

}
