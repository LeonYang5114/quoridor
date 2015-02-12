package yang.leon.quoridor;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * An empty implementation of <code>AbstractGameModel</code>. It is used when
 * only one or some of the methods are needed, so that calling unnecessary
 * methods will not incur unexpected results. One example is the update delegate
 * which only stores the information necessary for updating the game view.
 * 
 * @author Leon Yang
 * @see AbstractGameModel#getUpdateDelegate() getUpdateDelegate
 * @see AbstractGameModel#setUpdateDelegate() setUpdateDelegate
 */
class DelegateModel extends AbstractGameModel {

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
    public void nextPlayer() {
    }

    @Override
    public int getCurrPlayerIndex() {
	return 0;
    }

    @Override
    public void update(Graphics g, AbstractGameView context) {
    }

    @Override
    public IViewAdapter getViewAdapter() {
	return null;
    }

    @Override
    public void setViewAdapter(IViewAdapter viewAdpt) {
    }

    @Override
    public AbstractGameModel getUpdateDelegate() {
	return null;
    }

    @Override
    public void setUpdateDelegate(AbstractGameModel updateDelegate) {
    }

}
