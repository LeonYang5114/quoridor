package yang.leon.quoridor;

import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IModelAdapter extends Remote {

    public void registerView(AbstractView abstractView) throws RemoteException;

    public ArrayList<Location> getCanMoveLocs(Location loc)
	    throws RemoteException;

    public boolean isCanPutWall(Location loc, int direction)
	    throws RemoteException;

    public boolean movePawn(Location newLoc) throws RemoteException;

    public void putWall(Location loc, int direction) throws RemoteException;

    public Player getPlayer(int playerIndex) throws RemoteException;

    public int getNumPlayers() throws RemoteException;

    public void nextPlayer(AbstractView abstractView) throws RemoteException;

    public void update(Graphics g, AbstractView context) throws RemoteException;

    public int getCurrPlayerIndex() throws RemoteException;

}
