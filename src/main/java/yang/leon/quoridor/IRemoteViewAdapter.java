package yang.leon.quoridor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteViewAdapter extends Remote {

    public void update() throws RemoteException;

    public void registerRemoteModelAdapter(IRemoteModelAdapter remoteModelAdpt)
	    throws RemoteException;

    public void launch() throws RemoteException;

}
