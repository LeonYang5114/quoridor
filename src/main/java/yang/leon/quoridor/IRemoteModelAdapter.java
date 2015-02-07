package yang.leon.quoridor;

import java.awt.Graphics;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemoteModelAdapter extends Remote {

    public AbstractGameModel getUpdateDelegate() throws RemoteException;

    public ArrayList<Location> getCanMoveLocs(Location loc)
	    throws RemoteException;

    public boolean isCanPutWall(Location loc, int direction)
	    throws RemoteException;

    public boolean movePawn(Location newLoc) throws RemoteException;

    public void putWall(Location loc, int direction) throws RemoteException;

    public Player getPlayer(int playerIndex) throws RemoteException;

    public int getNumPlayers() throws RemoteException;

    public void nextPlayer() throws RemoteException;

    public int getCurrPlayerIndex() throws RemoteException;

    public void update(Graphics g, AbstractGameView context)
	    throws RemoteException;    
    
    public void updateAllViews() throws RemoteException;

    public void registerRemoteViewAdapter(IRemoteViewAdapter remoteViewAdpt)
	    throws RemoteException;
}
