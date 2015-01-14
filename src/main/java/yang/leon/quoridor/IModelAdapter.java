package yang.leon.quoridor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IModelAdapter extends Remote {

    public void registerView(AbstractView abstractView) throws RemoteException;

    public void registerView(ICallbackClientView callbackView)
	    throws RemoteException;

    public ArrayList<Location> getCanMoveLocs(Location loc)
	    throws RemoteException;

    public boolean isCanPutWall(Location loc, int direction)
	    throws RemoteException;

    public boolean movePawn(Location newLoc) throws RemoteException;

    public void putWall(Location loc, int direction) throws RemoteException;

    public Player getPlayer(int playerIndex) throws RemoteException;

    public int getNumPlayers() throws RemoteException;

    public String nextPlayer() throws RemoteException;

    public AbstractView getUpdatingView() throws RemoteException;

    public AbstractModel getUpdateDelegate() throws RemoteException;

    public void requestWaitForUpdate() throws RemoteException;

    public int getCurrPlayerIndex() throws RemoteException;

    public void doneWithUpdateNotify(AbstractView context)
	    throws RemoteException;

    public void requestUpdate() throws RemoteException;

}
